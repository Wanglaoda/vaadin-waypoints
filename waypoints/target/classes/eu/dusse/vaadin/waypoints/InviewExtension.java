package eu.dusse.vaadin.waypoints;

import java.lang.reflect.Method;

import com.vaadin.ui.AbstractComponent;
import com.vaadin.util.ReflectTools;

import eu.dusse.vaadin.waypoints.common.Direction;
import eu.dusse.vaadin.waypoints.common.WaypointEvent;

public interface InviewExtension
{

    /**
     * Returns <b>true</b> if this extension is marked as enabled.
     * 
     * When this extension is enabled, it will listen to inview-events
     * 
     * @return is this extension enabled
     */
    public boolean isEnabled();

    /**
     * Sets the enabled state of this extension
     * 
     * When this extension is enabled, it will listen to inview-events
     * 
     * @param enabled
     *            the state to set
     */
    public void setEnabled(boolean enabled);

    /**
     * Destroys the inview-listeners for this components
     */
    public void destroy();

    public void addEnterListener(EnterListener listener);

    public void removeEnterListener(EnterListener listener);

    public void addEnteredListener(EnteredListener listener);

    public void removeEnteredListener(EnteredListener listener);

    public void addExitListener(ExitListener listener);

    public void removeExitListener(ExitListener listener);

    public void addExitedListener(ExitedListener listener);

    public void removeExitedListener(ExitedListener listener);

    public interface EnterListener
    {
        static Method METHOD = ReflectTools.findMethod(EnterListener.class, "onEnter",
                EnterEvent.class);

        /**
         * The enter callback is triggered when any piece of the element starts entering the
         * viewport. It triggers with a {@link Direction#DOWN} direction when the top of the element
         * hits the bottom of the viewport while scrolling down, and triggers with an
         * {@link Direction#UP} direction when the bottom of the element hits the top of the
         * viewport while scrolling up.
         * 
         * @see <a href="http://imakewebthings.com/waypoints/shortcuts/inview/#enter-option">
         *      Original API</a>
         * 
         * @param event
         *            the {@link EnterEvent} containing the source and the {@link Direction} for the
         *            occurred event
         */
        public void onEnter(EnterEvent event);
    }

    public interface EnteredListener
    {
        static Method METHOD = ReflectTools.findMethod(EnteredListener.class, "onEntered",
                EnteredEvent.class);

        /**
         * The entered callback is triggered when the entirety of the element has finished entering
         * the viewport. It triggers with a {@link Direction#DOWN} direction when the bottom of the
         * element hits the bottom of the viewport while scrolling down, and triggers with an
         * {@link Direction#UP} direction when the top of the element hits the top of the viewport
         * while scrolling up.
         * 
         * @see <a href="http://imakewebthings.com/waypoints/shortcuts/inview/#entered-option">
         *      Original API</a>
         * 
         * @param event
         *            the {@link EnteredEvent} containing the source and the {@link Direction} for
         *            the occurred event
         */
        public void onEntered(EnteredEvent event);
    }

    public interface ExitListener
    {
        static Method METHOD = ReflectTools.findMethod(ExitListener.class, "onExit",
                ExitEvent.class);

        /**
         * The exit callback is triggered when any piece of the element starts leaving the viewport.
         * It triggers with a {@link Direction#DOWN} direction when the top of the element hits the
         * top of the viewport while scrolling down, and triggers with an {@link Direction#UP}
         * direction when the bottom of the element hits the bottom of the viewport while scrolling
         * up.
         * 
         * @see <a href="http://imakewebthings.com/waypoints/shortcuts/inview/#exit-option">
         *      Original API</a>
         * 
         * @param event
         *            the {@link ExitEvent} containing the source and the {@link Direction} for the
         *            occurred event
         */
        public void onExit(ExitEvent event);
    }

    public interface ExitedListener
    {
        static Method METHOD = ReflectTools.findMethod(ExitedListener.class, "onExited",
                ExitedEvent.class);

        /**
         * The exited callback is triggered when the entirety of the element finishes leaving the
         * viewport. It triggers with a {@link Direction#DOWN} direction when the bottom of the
         * element hits the top of the viewport while scrolling down, and triggers with an
         * {@link Direction#UP} direction when the top of the element hits the bottom of the
         * viewport while scrolling up.
         * 
         * @see <a href="http://imakewebthings.com/waypoints/shortcuts/inview/#exited-option">
         *      Original API</a>
         * 
         * @param event
         *            the {@link ExitedEvent} containing the source and the {@link Direction} for
         *            the occurred event
         */
        public void onExited(ExitedEvent event);
    }

    public class EnterEvent extends WaypointEvent
    {
        protected EnterEvent(AbstractComponent source, Direction direction)
        {
            super(source, direction);
        }
    }

    public class EnteredEvent extends WaypointEvent
    {
        protected EnteredEvent(AbstractComponent source, Direction direction)
        {
            super(source, direction);
        }
    }

    public class ExitEvent extends WaypointEvent
    {
        protected ExitEvent(AbstractComponent source, Direction direction)
        {
            super(source, direction);
        }
    }

    public class ExitedEvent extends WaypointEvent
    {
        protected ExitedEvent(AbstractComponent source, Direction direction)
        {
            super(source, direction);
        }
    }

}
