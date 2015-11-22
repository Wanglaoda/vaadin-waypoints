package eu.dusse.vaadin.waypoints.demo;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.annotation.WebServlet;

import org.javatuples.Pair;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.Navigator.ClassBasedViewProvider;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.navigator.ViewDisplay;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

@Theme("demo")
@Title("Waypoints Add-on Demo")
@SuppressWarnings("serial")
public class DemoUI extends UI
{

    @WebServlet(value = "/*", asyncSupported = true)
    @VaadinServletConfiguration(productionMode = false, ui = DemoUI.class, widgetset = "eu.dusse.vaadin.waypoints.demo.DemoWidgetSet")
    public static class Servlet extends VaadinServlet
    {
    }

    private static final List<Pair<String, Class<? extends View>>> VIEWS = new ArrayList<Pair<String, Class<? extends View>>>();

    static
    {
        VIEWS.add(new Pair<String, Class<? extends View>>("Home", DemoHome.class));
        VIEWS.add(new Pair<String, Class<? extends View>>("Waypoint", DemoWaypoint.class));
        VIEWS.add(new Pair<String, Class<? extends View>>("Inview", DemoInview.class));
        VIEWS.add(new Pair<String, Class<? extends View>>("Infinite scroll",
                DemoInfiniteScroll.class));
        VIEWS.add(new Pair<String, Class<? extends View>>("All supported contexts",
                DemoAllSupportedContexts.class));
    }

    private final CheckBox enabledBox;
    private final VerticalLayout mainLayout;

    public DemoUI()
    {
        enabledBox = new CheckBox();
        enabledBox.setCaption("Waypoints enabled");
        enabledBox.setValue(true);

        HorizontalLayout settingsLayout = new HorizontalLayout();
        settingsLayout.addComponents(enabledBox);
        settingsLayout.setSpacing(true);

        CssLayout navigationLayout = new CssLayout();
        navigationLayout.setStyleName("v-component-group");
        for (Pair<String, Class<? extends View>> pair : VIEWS)
        {
            final String viewName = pair.getValue0();

            Button navigationButton = new Button();
            navigationButton.setCaption(viewName);
            navigationButton.addClickListener(new ClickListener()
            {
                @Override
                public void buttonClick(ClickEvent event)
                {
                    UI.getCurrent().getNavigator().navigateTo(viewName);
                }
            });

            navigationLayout.addComponent(navigationButton);
        }

        mainLayout = new VerticalLayout();
        mainLayout.addComponents(settingsLayout, navigationLayout);
        mainLayout.setSpacing(true);
        mainLayout.setMargin(true);

        setContent(mainLayout);
    }

    @Override
    protected void init(VaadinRequest request)
    {
        ViewDisplay display = new ViewDisplay()
        {
            @Override
            public void showView(View view)
            {
                // replace component at position 1 of the mainlayout
                Component content = (Component) view;
                if (mainLayout.getComponentCount() > 2)
                {
                    // remove old content
                    mainLayout.removeComponent(mainLayout.getComponent(2));
                }
                // add new content
                mainLayout.addComponent(content);
            }
        };

        Navigator navigator = new Navigator(this, display);
        navigator.setErrorView(new DemoHome()); // default view
        for (Pair<String, Class<? extends View>> pair : VIEWS)
        {
            navigator.addProvider(new ClassBasedViewProvider(pair.getValue0(), pair.getValue1()));
        }
        navigator.addViewChangeListener(new ViewChangeListener()
        {
            @Override
            public boolean beforeViewChange(ViewChangeEvent event)
            {
                // close all windows when changing views
                for (Window window : UI.getCurrent().getWindows())
                {
                    window.close();
                }
                return true;
            }

            @Override
            public void afterViewChange(ViewChangeEvent event)
            {
            }
        });
    }

    public CheckBox getEnabledBox()
    {
        return enabledBox;
    }

    public static DemoUI getCurrent()
    {
        return (DemoUI) UI.getCurrent();
    }

}
