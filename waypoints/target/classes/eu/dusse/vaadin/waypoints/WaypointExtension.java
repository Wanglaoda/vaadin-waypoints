package eu.dusse.vaadin.waypoints;

import java.lang.reflect.Method;

import com.vaadin.ui.AbstractComponent;
import com.vaadin.util.ReflectTools;

import eu.dusse.vaadin.waypoints.common.Direction;
import eu.dusse.vaadin.waypoints.common.WaypointEvent;

public interface WaypointExtension
{

    public boolean isEnabled();

    public void setEnabled(boolean enabled);

    public void destroy();

    public void addCrossedListener(CrossedListener listener);

    public void removeCrossedListener(CrossedListener listener);

    public interface CrossedListener
    {
        static Method METHOD = ReflectTools.findMethod(CrossedListener.class, "onCrossed",
                CrossedEvent.class);

        public void onCrossed(CrossedEvent event);
    }

    public class CrossedEvent extends WaypointEvent
    {
        protected CrossedEvent(AbstractComponent source, Direction direction)
        {
            super(source, direction);
        }
    }

}
