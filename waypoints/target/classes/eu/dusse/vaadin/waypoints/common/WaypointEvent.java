package eu.dusse.vaadin.waypoints.common;

import java.util.EventObject;

import com.vaadin.ui.AbstractComponent;

/**
 * Abstract super-class for the events fired by this addon
 * 
 * @author Christian
 * 
 */
public abstract class WaypointEvent extends EventObject
{

    private final Direction direction;

    protected WaypointEvent(AbstractComponent source, Direction direction)
    {
        super(source);
        this.direction = direction;
    }

    @Override
    public AbstractComponent getSource()
    {
        return (AbstractComponent) super.getSource();
    }

    /**
     * Direction that describes on which direction an inview event occurred.
     * 
     * {@link Direction#DOWN} means that the event occurred while scrolling down and so on.
     * 
     * @return the direction on which direction an inview event occurred.
     */
    public Direction getDirection()
    {
        return direction;
    }

}