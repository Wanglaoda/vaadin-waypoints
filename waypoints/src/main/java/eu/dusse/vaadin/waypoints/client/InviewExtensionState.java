package eu.dusse.vaadin.waypoints.client;

import java.util.HashSet;

import com.vaadin.shared.JavaScriptExtensionState;

public class InviewExtensionState extends JavaScriptExtensionState
{

    public String contextConnectorId = null;
    public boolean horizontal = false;

    public InviewExtensionState()
    {
        registeredEventListeners = new HashSet<String>();
    }

    public static String EVENT_ENTER = "event_enter";
    public static String EVENT_ENTERED = "event_entered";
    public static String EVENT_EXIT = "event_exit";
    public static String EVENT_EXITED = "event_exited";

}