package eu.dusse.vaadin.waypoints.demo;

import java.io.Serializable;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import eu.dusse.vaadin.waypoints.WaypointExtension;
import eu.dusse.vaadin.waypoints.WaypointExtension.CrossedEvent;
import eu.dusse.vaadin.waypoints.WaypointExtension.CrossedListener;
import eu.dusse.vaadin.waypoints.WaypointExtensionImpl;

@SuppressWarnings("serial")
public class DemoWaypoint extends HorizontalLayout implements View
{

    private static final int NOF_ENTRIES = 20;

    public DemoWaypoint()
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
        VerticalLayout vertical = new VerticalLayout();
        vertical.setCaption("Fire event when element crosses the middle of screen");
        vertical.setSpacing(true);

        final AbstractComponent context = UI.getCurrent(); // the button is inside the ui
        final boolean horizontal = false; // is aligned vertically
        final Serializable offset = "50%"; // fire events when button crosses center of screen

        for (int idx = 0; idx < NOF_ENTRIES; idx++)
        {
            final Button button = ButtonFactory.createButton();
            final WaypointExtension extension = addExtension(button, context, horizontal, offset);

            // is this demo, the checkbox decides if the extension is enabled
            extension.setEnabled(DemoUI.getCurrent().getEnabledBox().getValue());

            DemoUI.getCurrent().getEnabledBox().addValueChangeListener(new ValueChangeListener()
            {
                @Override
                public void valueChange(ValueChangeEvent event)
                {
                    // disable extension when checkbox unchecked
                    extension.setEnabled((Boolean) event.getProperty().getValue());
                }
            });

            vertical.addComponent(button);
        }

        addComponent(vertical);
    }

    private void createExample2()
    {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setMargin(true);
        horizontalLayout.setSpacing(true);

        Panel panel = new Panel();
        panel.setCaption("Fire event when element crosses the left border of the panel");
        panel.setWidth(800, Unit.PIXELS);
        panel.setContent(horizontalLayout); // horizontal layout inside a constraint panel

        final AbstractComponent context = panel; // the scrollable context is the panel
        final boolean horizontal = true; // is aligned horizontal
        final Serializable offset = 0; // fire events when button crosses the left side of the panel

        for (int idx = 0; idx < NOF_ENTRIES; idx++)
        {
            Button button = ButtonFactory.createButton();
            final WaypointExtension extension = addExtension(button, context, horizontal, offset);

            // is this demo, the checkbox decides if the extension is enabled
            extension.setEnabled(DemoUI.getCurrent().getEnabledBox().getValue());

            DemoUI.getCurrent().getEnabledBox().addValueChangeListener(new ValueChangeListener()
            {
                @Override
                public void valueChange(ValueChangeEvent event)
                {
                    // disable extension when checkbox unchecked
                    extension.setEnabled((Boolean) event.getProperty().getValue());
                }
            });

            horizontalLayout.addComponent(button);
        }

        addComponent(panel);
    }

    private void createExample3()
    {
        VerticalLayout vertical = new VerticalLayout();
        vertical.setMargin(true);
        vertical.setSpacing(true);

        Window window = new Window();
        window.setCaption("Fire event when element crosses the bottom border of the window");
        window.setHeight(400, Unit.PIXELS);
        window.setWidth(800, Unit.PIXELS);
        window.setPosition(300, 400);
        window.setContent(vertical);

        final AbstractComponent context = window; // the scrollable context is the window
        final boolean horizontal = false; // is aligned vertically
        final Serializable offset = "bottom-in-view"; // fire events when button crosses the bottom
                                                      // border of the window

        for (int idx = 0; idx < NOF_ENTRIES; idx++)
        {
            Button button = ButtonFactory.createButton();
            final WaypointExtension extension = addExtension(button, context, horizontal, offset);

            // is this demo, the checkbox decides if the extension is enabled
            extension.setEnabled(DemoUI.getCurrent().getEnabledBox().getValue());

            DemoUI.getCurrent().getEnabledBox().addValueChangeListener(new ValueChangeListener()
            {
                @Override
                public void valueChange(ValueChangeEvent event)
                {
                    // disable extension when checkbox unchecked
                    extension.setEnabled((Boolean) event.getProperty().getValue());
                }
            });

            vertical.addComponent(button);
        }

        UI.getCurrent().addWindow(window);
    }

    private void createExample4()
    {
        Table table = new Table();
        table.setCaption("Fire event when row is 100px below the table's top border");

        table.addContainerProperty(1, Integer.class, 0);
        table.addContainerProperty(2, Button.class, null);
        table.setColumnHeaders("key", "value");
        table.setPageLength(5); // table should be as high as 5 rows

        final AbstractComponent context = table; // the scrollable context is the table
        final boolean horizontal = false; // is aligned vertically
        final Serializable offset = 100; // fire events when row is 100px below table's top corner

        for (int idx = 0; idx < NOF_ENTRIES; idx++)
        {
            Button button = ButtonFactory.createButton();
            final WaypointExtension extension = addExtension(button, context, horizontal, offset);

            // is this demo, the checkbox decides if the extension is enabled
            extension.setEnabled(DemoUI.getCurrent().getEnabledBox().getValue());

            DemoUI.getCurrent().getEnabledBox().addValueChangeListener(new ValueChangeListener()
            {
                @Override
                public void valueChange(ValueChangeEvent event)
                {
                    // disable extension when checkbox unchecked
                    extension.setEnabled((Boolean) event.getProperty().getValue());
                }
            });

            table.addItem(new Object[]
            { idx, button }, null);
        }

        addComponent(table);
    }

    private static WaypointExtension addExtension(final Button button,
            final AbstractComponent context, final boolean horizontal, final Serializable offset)
    {
        final WaypointExtension ext = new WaypointExtensionImpl(button, context, horizontal,
                offset);

        ext.addCrossedListener(new CrossedListener()
        {
            @Override
            public void onCrossed(CrossedEvent event)
            {
                String text = "crossed " + button.getCaption() + " " + event.getDirection() + " at "
                        + offset;
                Notification.show(text);
            }
        });

        return ext;
    }

}
