package eu.dusse.vaadin.waypoints.demo;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import eu.dusse.vaadin.waypoints.InviewExtension;
import eu.dusse.vaadin.waypoints.InviewExtension.EnterEvent;
import eu.dusse.vaadin.waypoints.InviewExtension.EnterListener;
import eu.dusse.vaadin.waypoints.InviewExtensionImpl;

@SuppressWarnings("serial")
public class DemoInfiniteScroll extends VerticalLayout implements View
{

    private static final int NOF_ENTRIES = 10;

    public DemoInfiniteScroll()
    {
        setSpacing(true);
    }

    @Override
    public void enter(ViewChangeEvent event)
    {
        createExample1();
    }

    private void createExample1()
    {
        // initial content
        loadNextPage();

        final Button loadNextPageButton = new Button();
        loadNextPageButton.setStyleName("primary");
        loadNextPageButton.setCaption("Load more entries");
        loadNextPageButton.addClickListener(new ClickListener()
        {
            @Override
            public void buttonClick(ClickEvent event)
            {
                // remove button
                removeComponent(loadNextPageButton);

                // add more content on click
                loadNextPage();

                // add button at end
                addComponent(loadNextPageButton);
            }
        });
        addComponent(loadNextPageButton);

        final InviewExtension extension = new InviewExtensionImpl(loadNextPageButton,
                UI.getCurrent(), false);
        extension.addEnterListener(new EnterListener()
        {
            @Override
            public void onEnter(EnterEvent event)
            {
                // remove button
                removeComponent(loadNextPageButton);

                // add more content on cbecoming visible
                loadNextPage();

                // add button at end
                addComponent(loadNextPageButton);
            }
        });

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

    }

    private void loadNextPage()
    {
        Notification.show("Load next page", Type.WARNING_MESSAGE);

        for (int idx = 0; idx < NOF_ENTRIES; idx++)
        {
            final Button button = ButtonFactory.createButton();
            addComponent(button);
        }
    }

}
