package eu.dusse.vaadin.waypoints;

import java.io.Serializable;

import com.vaadin.annotations.JavaScript;
import com.vaadin.server.AbstractJavaScriptExtension;
import com.vaadin.shared.Connector;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.JavaScriptFunction;

import elemental.json.JsonArray;
import eu.dusse.vaadin.waypoints.client.WaypointExtensionState;
import eu.dusse.vaadin.waypoints.common.Direction;

@JavaScript(
{ "public/waypoints/jquery-2.1.4.min.js", "public/waypoints/jquery.waypoints.min.js",
        "public/waypoints/common.js", "public/waypoints/waypointExtensionConnector.js" })
public class WaypointExtensionImpl extends AbstractJavaScriptExtension implements WaypointExtension
{

    private final Connector context;

    public WaypointExtensionImpl(AbstractComponent toExtend, AbstractComponent context,
            boolean horizontal, Serializable offset)
    {
        addCallbacks();
        extend(toExtend);
        this.context = context;
        getState().horizontal = horizontal;
        getState().offset = offset;
    }

    private void addCallbacks()
    {
        addFunction("crossed", new JavaScriptFunction()
        {
            @Override
            public void call(JsonArray arguments)
            {
                String direction = arguments.getString(0);
                fireEvent(new CrossedEvent((AbstractComponent) getParent(),
                        Direction.get(direction)));
            }
        });
    }

    @Override
    protected WaypointExtensionState getState()
    {
        return (WaypointExtensionState) super.getState();
    }

    @Override
    public boolean isEnabled()
    {
        return getState().enabled;
    }

    @Override
    public void setEnabled(boolean enabled)
    {
        getState().enabled = enabled;
    }

    @Override
    public void destroy()
    {
        callFunction("destroy");
    }

    @Override
    public void addCrossedListener(CrossedListener listener)
    {
        super.addListener(CrossedEvent.class, listener, CrossedListener.METHOD);
    }

    @Override
    public void removeCrossedListener(CrossedListener listener)
    {
        super.removeListener(CrossedEvent.class, listener, CrossedListener.METHOD);
    }

    @Override
    public void beforeClientResponse(boolean initial)
    {
        ensureContextSet();
        ensureListenersSet();

        super.beforeClientResponse(initial);
    }

    private void ensureContextSet()
    {
        // if no context specified -> use 'null'
        if (context == null)
        {
            getState().contextConnectorId = null;
        }
        else
        {
            // beforeClientResponse the context should be attached so that is has a
            // connector id. If not, throw an exception
            if (context.getConnectorId() == null)
            {
                throw new IllegalStateException(
                        "Context for extension does not have a connector id yet! Maybe You did not attach the context to Your UI yet!");
            }
            else
            {
                getState().contextConnectorId = context.getConnectorId();
            }
        }
    }

    private void ensureListenersSet()
    {
        // let the client side know if there are listeners on server side
        // (do not call rpc from client to server if there is no listener)
        boolean hasListeners = !getListeners(CrossedEvent.class).isEmpty();
        getState().hasListeners = hasListeners;
    }

}
