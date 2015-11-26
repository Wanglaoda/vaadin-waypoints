package eu.dusse.vaadin.waypoints.demo;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.Accordion;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.VerticalSplitPanel;

import eu.dusse.vaadin.waypoints.InviewExtension;
import eu.dusse.vaadin.waypoints.InviewExtension.EnterEvent;
import eu.dusse.vaadin.waypoints.InviewExtension.EnterListener;
import eu.dusse.vaadin.waypoints.InviewExtension.ExitedEvent;
import eu.dusse.vaadin.waypoints.InviewExtension.ExitedListener;
import eu.dusse.vaadin.waypoints.InviewExtensionImpl;

@SuppressWarnings("serial")
public class DemoAllSupportedContexts extends FormLayout implements View
{

    private final EnterListener defaultEnterListener = new EnterListener()
    {
        @Override
        public void onEnter(EnterEvent event)
        {
            AbstractComponent source = event.getSource(); // the extended component
            String text = "enter " + source.getCaption() + " " + event.getDirection();
            Notification.show(text, Type.WARNING_MESSAGE);
        }
    };
    private final ExitedListener defaultExitedListener = new ExitedListener()
    {
        @Override
        public void onExited(ExitedEvent event)
        {
            AbstractComponent source = event.getSource(); // the extended component
            String text = "exited " + source.getCaption() + " " + event.getDirection();
            Notification.show(text, Type.WARNING_MESSAGE);
        }
    };

    public DemoAllSupportedContexts()
    {
        setSpacing(true);
    }

    @Override
    public void enter(ViewChangeEvent event)
    {
        createExampleUI();
        createExamplePanel();
        createExampleWindow();
        createExampleTable();
        createExampleGrid();
        createExampleTabsheet();
        createExampleAccordion();
        createExampleVerticalSplitPanel();
        createExampleHorizontalSplitPanel();
    }

    private void createExampleUI()
    {
        final Button button = ButtonFactory.createButton(); // to listen for

        InviewExtension extension = new InviewExtensionImpl(button, UI.getCurrent(), false);
        extension.addEnterListener(defaultEnterListener);
        extension.addExitedListener(defaultExitedListener);

        // wrap button into layout so that we can designate an other caption
        VerticalLayout wrapper = new VerticalLayout(button);
        wrapper.setCaption("Vaadin UI");

        addComponent(wrapper);
    }

    private void createExamplePanel()
    {
        int heightInPx = 300;

        Panel panel = new Panel();
        panel.setHeight(heightInPx, Unit.PIXELS);

        // create space so that button is not visible at start
        CssLayout spacer = new CssLayout();
        spacer.setHeight(heightInPx * 1.5f, Unit.PIXELS);

        final Button button = ButtonFactory.createButton(); // to listen for

        InviewExtension extension = new InviewExtensionImpl(button, panel, false);
        extension.addEnterListener(defaultEnterListener);
        extension.addExitedListener(defaultExitedListener);

        VerticalLayout layout = new VerticalLayout();
        layout.addComponents(spacer, button);

        panel.setContent(layout);

        // wrap panel into layout so the caption will be shown at the left side of the form and not
        // in the panel's title bar
        VerticalLayout wrapper = new VerticalLayout(panel);
        wrapper.setCaption("Vaadin Panel");

        addComponent(wrapper);
    }

    private void createExampleWindow()
    {
        Component label = new Label("TODO. See examples on the 'Waypoint' view.");
        label.setCaption("Vaadin Window");
        addComponent(label);
    }

    private void createExampleTable()
    {
        Table table = new Table();
        table.setWidth(100, Unit.PERCENTAGE);
        table.addContainerProperty(1, Integer.class, 0);
        table.addContainerProperty(2, Button.class, null);
        table.setColumnHeaders("key", "value");
        table.setPageLength(5); // table should be as high as 5 rows

        for (int idx = 1; idx <= 10; idx++)
        {
            // add spacer
            table.addItem(new Object[]
            { idx, null }, idx);
        }

        final Button button = ButtonFactory.createButton(); // to listen for

        InviewExtension extension = new InviewExtensionImpl(button, table, false);
        extension.addEnterListener(defaultEnterListener);
        extension.addExitedListener(defaultExitedListener);

        table.addItem(new Object[]
        { 11, button }, 11);

        table.setCaption("Vaadin Table (might fire events early because of vaadin rendering)");
        addComponent(table);
    }

    private void createExampleGrid()
    {
        Component label = new Label(
                "Not supported yet. Idea for supporting Grid is to add the extension to a renderer");
        label.setCaption("Vaadin Grid");
        addComponent(label);
    }

    private void createExampleTabsheet()
    {
        int heightInPx = 300;

        TabSheet tabsheet = new TabSheet();
        tabsheet.setHeight(heightInPx, Unit.PIXELS);

        // create space so that button is not visible at start
        CssLayout spacer = new CssLayout();
        spacer.setHeight(heightInPx * 1.5f, Unit.PIXELS);

        final Button button = ButtonFactory.createButton(); // to listen for

        InviewExtension extension = new InviewExtensionImpl(button, tabsheet, false);
        extension.addEnterListener(defaultEnterListener);
        extension.addExitedListener(defaultExitedListener);

        VerticalLayout layout = new VerticalLayout();
        layout.addComponents(spacer, button);

        tabsheet.addTab(layout, "Tab");
        tabsheet.addTab(new CssLayout(), "Other tab");
        tabsheet.setCaption("Vaadin Tabsheet");
        addComponent(tabsheet);
    }

    private void createExampleAccordion()
    {
        int heightInPx = 300;

        Accordion tabsheet = new Accordion();
        tabsheet.setHeight(heightInPx, Unit.PIXELS);

        // create space so that button is not visible at start
        CssLayout spacer = new CssLayout();
        spacer.setHeight(heightInPx * 1.5f, Unit.PIXELS);

        final Button button = ButtonFactory.createButton(); // to listen for

        InviewExtension extension = new InviewExtensionImpl(button, tabsheet, false);
        extension.addEnterListener(defaultEnterListener);
        extension.addExitedListener(defaultExitedListener);

        VerticalLayout layout = new VerticalLayout();
        layout.addComponents(spacer, button);

        tabsheet.addTab(layout, "Tab");
        tabsheet.addTab(new CssLayout(), "Other tab");
        tabsheet.setCaption("Vaadin Accordion (not working as aspected)");
        addComponent(tabsheet);
    }

    private void createExampleVerticalSplitPanel()
    {
        int heightInPx = 300;

        VerticalSplitPanel panel = new VerticalSplitPanel();
        panel.setHeight(heightInPx * 2, Unit.PIXELS);

        // --- upper --- //

        // create space so that button is not visible at start
        CssLayout spacerUpper = new CssLayout();
        spacerUpper.setHeight(heightInPx * 1.5f, Unit.PIXELS);

        final Button buttonUpper = ButtonFactory.createButton(); // to listen for
        InviewExtension extensionUpper = new InviewExtensionImpl(buttonUpper, panel, false);
        extensionUpper.addEnterListener(defaultEnterListener);
        extensionUpper.addExitedListener(defaultExitedListener);

        VerticalLayout layoutUpper = new VerticalLayout();
        layoutUpper.addComponents(spacerUpper, buttonUpper);

        // --- lower --- //

        // create space so that button is not visible at start
        CssLayout spacerLower = new CssLayout();
        spacerLower.setHeight(heightInPx * 1.5f, Unit.PIXELS);

        final Button buttonLower = ButtonFactory.createButton(); // to listen for
        InviewExtension extensionLower = new InviewExtensionImpl(buttonLower, panel, false);
        extensionLower.addEnterListener(defaultEnterListener);
        extensionLower.addExitedListener(defaultExitedListener);

        VerticalLayout layoutLower = new VerticalLayout();
        layoutLower.addComponents(spacerLower, buttonLower);

        // --- combine --- //

        panel.setFirstComponent(layoutUpper);
        panel.setSecondComponent(layoutLower);
        panel.setCaption("Vaadin VerticalSplitPanel (does not fire when split position changed)");
        addComponent(panel);
    }

    private void createExampleHorizontalSplitPanel()
    {
        int widthInPx = 300;

        HorizontalSplitPanel panel = new HorizontalSplitPanel();
        panel.setWidth(widthInPx * 2, Unit.PIXELS);

        // --- upper --- //

        // create space so that button is not visible at start
        CssLayout spacerLeft = new CssLayout();
        spacerLeft.setWidth(widthInPx * 1.5f, Unit.PIXELS);

        final Button buttonLeft = ButtonFactory.createButton(); // to listen for
        InviewExtension extensionUpper = new InviewExtensionImpl(buttonLeft, panel, true);
        extensionUpper.addEnterListener(defaultEnterListener);
        extensionUpper.addExitedListener(defaultExitedListener);

        HorizontalLayout layoutLeft = new HorizontalLayout();
        layoutLeft.addComponents(spacerLeft, buttonLeft);

        // --- lower --- //

        // create space so that button is not visible at start
        CssLayout spacerRight = new CssLayout();
        spacerRight.setWidth(widthInPx * 1.5f, Unit.PIXELS);

        final Button buttonRight = ButtonFactory.createButton(); // to listen for
        InviewExtension extensionLower = new InviewExtensionImpl(buttonRight, panel, true);
        extensionLower.addEnterListener(defaultEnterListener);
        extensionLower.addExitedListener(defaultExitedListener);

        HorizontalLayout layoutRight = new HorizontalLayout();
        layoutRight.addComponents(spacerRight, buttonRight);

        // --- combine --- //

        panel.setFirstComponent(layoutLeft);
        panel.setSecondComponent(layoutRight);
        panel.setCaption("Vaadin HorizontalSplitPanel (does not fire when split position changed)");
        addComponent(panel);
    }

}
