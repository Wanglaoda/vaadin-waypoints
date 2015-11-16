package eu.dusse.vaadin.waypoints;

import com.vaadin.annotations.JavaScript;
import com.vaadin.server.AbstractJavaScriptExtension;
import com.vaadin.ui.AbstractComponent;

import eu.dusse.vaadin.waypoints.client.StickyExtensionState;

/**
 * 
 * 
 * @author Christian Duße
 *
 * @deprecated EXPERIMENTAL!
 */
@JavaScript(
{ "public/waypoints/jquery-2.1.4.min.js", "public/waypoints/jquery.waypoints.min.js",
        "public/waypoints/sticky.min.js", "public/waypoints/common.js",
        "public/waypoints/stickyExtensionConnector.js" })
public class StickyExtensionImpl extends AbstractJavaScriptExtension implements StickyExtension
{

    public StickyExtensionImpl(AbstractComponent toExtend)
    {
        extend(toExtend);
    }

    @Override
    protected StickyExtensionState getState()
    {
        return (StickyExtensionState) super.getState();
    }

    @Override
    public void destroy()
    {
        callFunction("destroy");
    }

}
