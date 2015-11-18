package eu.dusse.vaadin.waypoints.demo;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

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

@SuppressWarnings("serial")
public class DemoInview extends VerticalLayout implements View
{

    private static final int NOF_ENTRIES = 20;

    public DemoInview()
    {
        setSpacing(true);
    }

    @Override
    public void enter(ViewChangeEvent event)
    {
        createExample1();
        createExample2();
        createExample3();
        createExample4();
    }

    private void createExample1()
    {
        HorizontalLayout layout = new HorizontalLayout();
        layout.setMargin(true);
        layout.setSpacing(true);

        Panel panel = new Panel();
        panel.setCaption("Example 1: Fire event when element begins to enter viewport");
        panel.setWidth(800, Unit.PIXELS);
        panel.setContent(layout);

        AbstractComponent context = panel; // panel is the scrollable
        boolean horizontal = true; // is horizontal layout

        for (int idx = 0; idx < NOF_ENTRIES; idx++)
        {
            final Button button = ButtonFactory.createButton();

            final InviewExtension ext = new InviewExtensionImpl(button, context, horizontal);
            ext.addEnterListener(new EnterListener()
            {
                @Override
                public void onEnter(EnterEvent event)
                {
                    String text = "enter " + button.getCaption() + " " + event.getDirection();
                    Notification.show(text);
                }
            });

            // is this demo, the checkbox decides if the extension is enabled
            ext.setEnabled(DemoUI.getCurrent().getEnabledBox().getValue());

            DemoUI.getCurrent().getEnabledBox().addValueChangeListener(new ValueChangeListener()
            {
                @Override
                public void valueChange(ValueChangeEvent event)
                {
                    ext.setEnabled((Boolean) event.getProperty().getValue());
                }
            });

            layout.addComponent(button);
        }
        addComponent(panel);
    }

    private void createExample2()
    {
        HorizontalLayout layout = new HorizontalLayout();
        layout.setMargin(true);
        layout.setSpacing(true);

        Panel panel = new Panel();
        panel.setCaption(
                "Example 2: Fire event when element finished entering viewport (entered completely)");
        panel.setWidth(800, Unit.PIXELS);
        panel.setContent(layout);

        AbstractComponent context = panel; // panel is the scrollable
        boolean horizontal = true; // is horizontal layout

        for (int idx = 0; idx < NOF_ENTRIES; idx++)
        {
            final Button button = ButtonFactory.createButton();

            final InviewExtension ext = new InviewExtensionImpl(button, context, horizontal);
            ext.addEnteredListener(new EnteredListener()
            {
                @Override
                public void onEntered(EnteredEvent event)
                {
                    String text = "entered " + button.getCaption() + " " + event.getDirection();
                    Notification.show(text);
                }
            });

            // is this demo, the checkbox decides if the extension is enabled
            ext.setEnabled(DemoUI.getCurrent().getEnabledBox().getValue());

            DemoUI.getCurrent().getEnabledBox().addValueChangeListener(new ValueChangeListener()
            {
                @Override
                public void valueChange(ValueChangeEvent event)
                {
                    ext.setEnabled((Boolean) event.getProperty().getValue());
                }
            });

            layout.addComponent(button);
        }
        addComponent(panel);
    }

    private void createExample3()
    {
        HorizontalLayout layout = new HorizontalLayout();
        layout.setMargin(true);
        layout.setSpacing(true);

        Panel panel = new Panel();
        panel.setCaption("Example 3: Fire event when element begins to leave viewport");
        panel.setWidth(800, Unit.PIXELS);
        panel.setContent(layout);

        AbstractComponent context = panel; // panel is the scrollable
        boolean horizontal = true; // is horizontal layout

        for (int idx = 0; idx < NOF_ENTRIES; idx++)
        {
            final Button button = ButtonFactory.createButton();

            final InviewExtension ext = new InviewExtensionImpl(button, context, horizontal);
            ext.addExitListener(new ExitListener()
            {
                @Override
                public void onExit(ExitEvent event)
                {
                    String text = "exit " + button.getCaption() + " " + event.getDirection();
                    Notification.show(text);
                }
            });

            // is this demo, the checkbox decides if the extension is enabled
            ext.setEnabled(DemoUI.getCurrent().getEnabledBox().getValue());

            DemoUI.getCurrent().getEnabledBox().addValueChangeListener(new ValueChangeListener()
            {
                @Override
                public void valueChange(ValueChangeEvent event)
                {
                    ext.setEnabled((Boolean) event.getProperty().getValue());
                }
            });

            layout.addComponent(button);
        }
        addComponent(panel);
    }

    private void createExample4()
    {
        HorizontalLayout layout = new HorizontalLayout();
        layout.setMargin(true);
        layout.setSpacing(true);

        Panel panel = new Panel();
        panel.setCaption(
                "Example 4: Fire event when element finished leaving viewport (left completely)");
        panel.setWidth(800, Unit.PIXELS);
        panel.setContent(layout);

        AbstractComponent context = panel; // panel is the scrollable
        boolean horizontal = true; // is horizontal layout

        for (int idx = 0; idx < NOF_ENTRIES; idx++)
        {
            final Button button = ButtonFactory.createButton();

            final InviewExtension ext = new InviewExtensionImpl(button, context, horizontal);
            ext.addExitedListener(new ExitedListener()
            {
                @Override
                public void onExited(ExitedEvent event)
                {
                    String text = "exited " + button.getCaption() + " " + event.getDirection();
                    Notification.show(text);
                }
            });

            // is this demo, the checkbox decides if the extension is enabled
            ext.setEnabled(DemoUI.getCurrent().getEnabledBox().getValue());

            DemoUI.getCurrent().getEnabledBox().addValueChangeListener(new ValueChangeListener()
            {
                @Override
                public void valueChange(ValueChangeEvent event)
                {
                    ext.setEnabled((Boolean) event.getProperty().getValue());
                }
            });

            layout.addComponent(button);
        }
        addComponent(panel);
    }

}
