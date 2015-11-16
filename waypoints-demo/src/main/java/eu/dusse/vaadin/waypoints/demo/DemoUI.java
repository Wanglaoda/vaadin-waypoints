package eu.dusse.vaadin.waypoints.demo;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.Property.ValueChangeNotifier;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Resource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import eu.dusse.vaadin.waypoints.InviewExtension;
import eu.dusse.vaadin.waypoints.InviewExtension.EnterEvent;
import eu.dusse.vaadin.waypoints.InviewExtension.EnterListener;
import eu.dusse.vaadin.waypoints.InviewExtension.EnteredEvent;
import eu.dusse.vaadin.waypoints.InviewExtension.EnteredListener;
import eu.dusse.vaadin.waypoints.InviewExtension.ExitEvent;
import eu.dusse.vaadin.waypoints.InviewExtension.ExitListener;
import eu.dusse.vaadin.waypoints.InviewExtension.ExitedEvent;
import eu.dusse.vaadin.waypoints.InviewExtension.ExitedListener;
import eu.dusse.vaadin.waypoints.InviewExtensionImpl;
import eu.dusse.vaadin.waypoints.WaypointExtension;
import eu.dusse.vaadin.waypoints.WaypointExtension.CrossedEvent;
import eu.dusse.vaadin.waypoints.WaypointExtension.CrossedListener;
import eu.dusse.vaadin.waypoints.WaypointExtensionImpl;

@Theme("demo")
@Title("Waypoints Add-on Demo")
@SuppressWarnings("serial")
public class DemoUI extends UI
{

    private static Resource[] images =
    { FontAwesome.YOUTUBE, FontAwesome.ANDROID, FontAwesome.BACKWARD, FontAwesome.WARNING,
            FontAwesome.PAUSE };

    @WebServlet(value = "/*", asyncSupported = true)
    @VaadinServletConfiguration(productionMode = false, ui = DemoUI.class, widgetset = "eu.dusse.vaadin.waypoints.demo.DemoWidgetSet")
    public static class Servlet extends VaadinServlet
    {
    }

    @Override
    protected void init(VaadinRequest request)
    {
        final CheckBox enabledBox = new CheckBox();
        enabledBox.setCaption("Enabled");
        enabledBox.setValue(true);

        final TextArea area = new TextArea();
        area.setWidth(800, Unit.PIXELS);
        area.setHeight(400, Unit.PIXELS);
        area.setEnabled(false);

        // --- //

        VerticalLayout imageWallUIVertical = new VerticalLayout();
        imageWallUIVertical.setSpacing(true);

        for (int idx = 0; idx < 20; idx++)
        {
            Button button = createButton(idx, area, DemoUI.this, false, enabledBox, false);
            imageWallUIVertical.addComponent(button);
        }

        // --- //

        HorizontalLayout imageWallPanelHorizontal = new HorizontalLayout();
        imageWallPanelHorizontal.setMargin(true);
        imageWallPanelHorizontal.setSpacing(true);

        Panel imageWallPanel = new Panel();
        imageWallPanel.setWidth(800, Unit.PIXELS);
        imageWallPanel.setContent(imageWallPanelHorizontal);

        for (int idx = 20; idx < 40; idx++)
        {
            Button button = createButton(idx, area, imageWallPanel, true, enabledBox, false);
            imageWallPanelHorizontal.addComponent(button);
        }

        // --- //

        VerticalLayout imageWallWindowVertical = new VerticalLayout();
        imageWallWindowVertical.setSpacing(true);
        imageWallWindowVertical.setMargin(true);

        Window imageWallWindow = new Window();
        imageWallWindow.setHeight(400, Unit.PIXELS);
        imageWallWindow.setContent(imageWallWindowVertical);
        imageWallWindow.setPosition(1300, 200);
        imageWallWindow.setResizable(true);
        UI.getCurrent().addWindow(imageWallWindow);

        for (int idx = 40; idx < 60; idx++)
        {
            Button button = createButton(idx, area, imageWallWindow, false, enabledBox, false);
            imageWallWindowVertical.addComponent(button);
        }

        // --- //

        VerticalLayout imageWallVerticalRight = new VerticalLayout();
        imageWallVerticalRight.setSpacing(true);
        imageWallVerticalRight.setMargin(true);

        for (int idx = 60; idx < 80; idx++)
        {
            Button button = createButton(idx, area, DemoUI.this, false, enabledBox, true);
            imageWallVerticalRight.addComponent(button);
        }

        // --- //

        HorizontalLayout main = new HorizontalLayout(imageWallUIVertical, imageWallPanel,
                imageWallVerticalRight);
        main.setSpacing(true);
        main.setMargin(true);

        setContent(main);

        Window window1 = new Window();
        window1.setCaption("Settings");
        window1.setPosition(200, 200);
        window1.setContent(enabledBox);
        window1.setResizable(false);
        addWindow(window1);

        Window window2 = new Window();
        // window2.setCaption("Console");
        window2.setPosition(200, 300);
        window2.setContent(area);
        window2.setResizable(false);
        addWindow(window2);
    }

    private Button createButton(final int idx, final TextArea area, AbstractComponent context,
            boolean horizontal, ValueChangeNotifier settingChangeNotifier, boolean waypoint)
    {
        final Button image = new Button();
        image.setCaption(String.valueOf(idx));
        image.setIcon(images[idx % images.length]);
        image.setWidth(100, Unit.PIXELS);
        image.setHeight(100, Unit.PIXELS);

        if (waypoint)
        {
            final WaypointExtension ext = new WaypointExtensionImpl(image, context, horizontal, 0);
            ext.addCrossedListener(new CrossedListener()
            {
                @Override
                public void onCrossed(CrossedEvent event)
                {
                    String text = "crossed " + image.getCaption() + " " + event.getDirection();
                    appendLine(text, area);
                }
            });

            settingChangeNotifier.addValueChangeListener(new ValueChangeListener()
            {
                @Override
                public void valueChange(ValueChangeEvent event)
                {
                    ext.setEnabled((Boolean) event.getProperty().getValue());
                }
            });
        }
        else
        {
            final InviewExtension ext = new InviewExtensionImpl(image, context, horizontal);
            ext.addEnterListener(new EnterListener()
            {
                @Override
                public void onEnter(EnterEvent event)
                {
                    String text = "enter " + image.getCaption() + " " + event.getDirection();
                    appendLine(text, area);
                }
            });
            ext.addEnteredListener(new EnteredListener()
            {
                @Override
                public void onEntered(EnteredEvent event)
                {
                    String text = "entered " + image.getCaption() + " " + event.getDirection();
                    appendLine(text, area);
                }
            });
            ext.addExitListener(new ExitListener()
            {
                @Override
                public void onExit(ExitEvent event)
                {
                    String text = "exit " + image.getCaption() + " " + event.getDirection();
                    appendLine(text, area);
                }
            });
            ext.addExitedListener(new ExitedListener()
            {
                @Override
                public void onExited(ExitedEvent event)
                {
                    String text = "exited " + image.getCaption() + " " + event.getDirection();
                    appendLine(text, area);
                }
            });

            settingChangeNotifier.addValueChangeListener(new ValueChangeListener()
            {
                @Override
                public void valueChange(ValueChangeEvent event)
                {
                    ext.setEnabled((Boolean) event.getProperty().getValue());
                }
            });

            if (context instanceof UI)
            {
                // workaround: extension for window size change
                // add an additional extension with 'null' as context and without
                // listeners
                new InviewExtensionImpl(image, null, horizontal);
            }
        }

        return image;
    }

    private void appendLine(String text, TextArea area)
    {
        area.setValue(area.getValue() + "\n" + text);
        if (area.getValue().length() > 500)
        {
            area.setValue(area.getValue().substring(250)); // delete old lines
        }
        area.setCursorPosition(area.getValue().length());
    }

}
