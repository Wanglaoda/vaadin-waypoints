package eu.dusse.vaadin.waypoints;

import com.vaadin.annotations.JavaScript;
import com.vaadin.server.AbstractJavaScriptExtension;
import com.vaadin.shared.Connector;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.Component;
import com.vaadin.ui.JavaScriptFunction;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;

import elemental.json.JsonArray;
import eu.dusse.vaadin.waypoints.client.InviewExtensionState;
import eu.dusse.vaadin.waypoints.common.Direction;

@JavaScript(
{ "public/waypoints/jquery-2.1.4.min.js", "public/waypoints/jquery.waypoints.min.js",
        "public/waypoints/inview.min.js", "public/waypoints/common.js",
        "public/waypoints/inviewExtensionConnector.js" })
public class InviewExtensionImpl extends AbstractJavaScriptExtension implements InviewExtension
{

    private final Connector context;

    /**
     * Creates an {@link InviewExtension} that will listen to inview-events
     * 
     * @param toExtend
     *            The {@link Component} to listen to inview-events to. The component will be
     *            extended by this {@link InviewExtension}
     * @param context
     *            Used for specifying a scrollable element other than the window that this Inview
     *            instance should act within. You can set the scrollable {@link UI} or {@link Panel}
     *            here. For listening to browser size change events You should add an additional
     *            {@link InviewExtension} with <b>null</b> as context.
     * @param horizontal
     *            When true, callbacks will work in the same fashion as a standard horizontal
     *            waypoint, dealing with left/right scrolls and passing {@link Direction#RIGHT} or
     *            {@link Direction#LEFT} in place of {@link Direction#DOWN} and {@link Direction#UP}
     *            .
     * 
     * @see <a href="http://imakewebthings.com/waypoints/shortcuts/inview/#context-option">Original
     *      API</a>
     * @see <a href="http://imakewebthings.com/waypoints/shortcuts/inview/#horizontal-option">
     *      Original API</a>
     */
    public InviewExtensionImpl(AbstractComponent toExtend, AbstractComponent context,
            boolean horizontal)
    {
        addCallbacks();
        extend(toExtend);
        this.context = context;
        getState().horizontal = horizontal;
    }

    private void addCallbacks()
    {
        addFunction("onEnter", new JavaScriptFunction()
        {
            @Override
            public void call(JsonArray arguments)
            {
                String direction = arguments.getString(0);
                fireEvent(
                        new EnterEvent((AbstractComponent) getParent(), Direction.get(direction)));
            }
        });
        addFunction("onEntered", new JavaScriptFunction()
        {
            @Override
            public void call(JsonArray arguments)
            {
                String direction = arguments.getString(0);
                fireEvent(new EnteredEvent((AbstractComponent) getParent(),
                        Direction.get(direction)));
            }
        });
        addFunction("onExit", new JavaScriptFunction()
        {
            @Override
            public void call(JsonArray arguments)
            {
                String direction = arguments.getString(0);
                fireEvent(new ExitEvent((AbstractComponent) getParent(), Direction.get(direction)));
            }
        });
        addFunction("onExited", new JavaScriptFunction()
        {
            @Override
            public void call(JsonArray arguments)
            {
                String direction = arguments.getString(0);
                fireEvent(
                        new ExitedEvent((AbstractComponent) getParent(), Direction.get(direction)));
            }
        });
    }

    @Override
    protected InviewExtensionState getState()
    {
        return (InviewExtensionState) super.getState();
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
    public void addEnterListener(EnterListener listener)
    {
        super.addListener(EnterEvent.class, listener, EnterListener.METHOD);
    }

    @Override
    public void removeEnterListener(EnterListener listener)
    {
        super.removeListener(EnterEvent.class, listener, EnterListener.METHOD);
    }

    @Override
    public void addEnteredListener(EnteredListener listener)
    {
        super.addListener(EnteredEvent.class, listener, EnteredListener.METHOD);
    }

    @Override
    public void removeEnteredListener(EnteredListener listener)
    {
        super.removeListener(EnteredEvent.class, listener, EnteredListener.METHOD);
    }

    @Override
    public void addExitListener(ExitListener listener)
    {
        super.addListener(ExitEvent.class, listener, ExitListener.METHOD);
    }

    @Override
    public void removeExitListener(ExitListener listener)
    {
        super.removeListener(ExitEvent.class, listener, ExitListener.METHOD);
    }

    @Override
    public void addExitedListener(ExitedListener listener)
    {
        super.addListener(ExitedEvent.class, listener, ExitedListener.METHOD);
    }

    @Override
    public void removeExitedListener(ExitedListener listener)
    {
        super.removeListener(ExitedEvent.class, listener, ExitedListener.METHOD);
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
        if (getListeners(EnterEvent.class).isEmpty())
        {
            getState().registeredEventListeners.remove(InviewExtensionState.EVENT_ENTER);
        }
        else
        {
            getState().registeredEventListeners.add(InviewExtensionState.EVENT_ENTER);
        }

        if (getListeners(EnteredEvent.class).isEmpty())
        {
            getState().registeredEventListeners.remove(InviewExtensionState.EVENT_ENTERED);
        }
        else
        {
            getState().registeredEventListeners.add(InviewExtensionState.EVENT_ENTERED);
        }

        if (getListeners(ExitEvent.class).isEmpty())
        {
            getState().registeredEventListeners.remove(InviewExtensionState.EVENT_EXIT);
        }
        else
        {
            getState().registeredEventListeners.add(InviewExtensionState.EVENT_EXIT);
        }

        if (getListeners(ExitedEvent.class).isEmpty())
        {
            getState().registeredEventListeners.remove(InviewExtensionState.EVENT_EXITED);
        }
        else
        {
            getState().registeredEventListeners.add(InviewExtensionState.EVENT_EXITED);
        }
    }

}
