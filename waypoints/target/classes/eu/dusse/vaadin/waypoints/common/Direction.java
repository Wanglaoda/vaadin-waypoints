package eu.dusse.vaadin.waypoints.common;

public enum Direction
{

    UP("up"), //
    DOWN("down"), //
    LEFT("left"), //
    RIGHT("right"); //

    private final String direction;

    private Direction(String direction)
    {
        this.direction = direction;
    }

    public String getDirection()
    {
        return direction;
    }

    public static Direction get(String direction)
    {
        for (Direction value : values())
        {
            if (value.direction.equals(direction))
            {
                return value;
            }
        }
        return null;
    }

}
