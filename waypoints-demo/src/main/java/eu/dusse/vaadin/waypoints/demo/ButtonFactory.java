package eu.dusse.vaadin.waypoints.demo;

import java.util.concurrent.atomic.AtomicInteger;

import com.vaadin.server.FontAwesome;
import com.vaadin.server.Resource;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Button;

public class ButtonFactory
{

    private static Resource[] images =
    { FontAwesome.YOUTUBE, FontAwesome.ANDROID, FontAwesome.BACKWARD, FontAwesome.WARNING,
            FontAwesome.PAUSE };

    private static final AtomicInteger counter = new AtomicInteger(0);

    public static Button createButton()
    {
        int idx = counter.incrementAndGet();

        final Button button = new Button();
        button.setCaption(String.valueOf(idx));
        button.setIcon(images[idx % images.length]);
        button.setWidth(100, Unit.PIXELS);
        button.setHeight(100, Unit.PIXELS);
        return button;
    }

}
