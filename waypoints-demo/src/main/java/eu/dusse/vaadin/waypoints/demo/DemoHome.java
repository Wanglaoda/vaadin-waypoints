package eu.dusse.vaadin.waypoints.demo;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class DemoHome extends VerticalLayout implements View
{

    public DemoHome()
    {
        Label label = new Label();
        label.setValue("Info not yet written :(");

        addComponents(label);
        setSpacing(true);
    }

    @Override
    public void enter(ViewChangeEvent event)
    {
        // empty. everything is initialized in constructor
    }

}
