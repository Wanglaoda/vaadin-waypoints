package eu.dusse.vaadin.waypoints.client;

import java.io.Serializable;

import com.vaadin.shared.JavaScriptExtensionState;

public class WaypointExtensionState extends JavaScriptExtensionState
{

    public String contextConnectorId = null;
    public boolean horizontal = false;
    public Serializable offset = 0;
    public boolean hasListeners = false;

}
