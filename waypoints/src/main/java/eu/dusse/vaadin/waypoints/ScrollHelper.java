package eu.dusse.vaadin.waypoints;

import com.vaadin.annotations.JavaScript;
import com.vaadin.server.AbstractJavaScriptExtension;
import com.vaadin.server.Extension;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;

@JavaScript(
{ "public/waypoints/jquery-2.1.4.min.js", "public/waypoints/jquery.smooth-scroll.js",
        "public/waypoints/common.js", "public/waypoints/scrollHelperExtensionConnector.js" })
public class ScrollHelper extends AbstractJavaScriptExtension
{

    protected ScrollHelper()
    {
    }

    /**
     * Scrolls into the specified vaadin component using the default settings and the current
     * {@link UI} as context.
     * 
     * Note that the first scroll will take some time due to the browser loading the necessary
     * JavaScript files. You can prevent this by calling {@link ScrollHelper#getCurrent()} before
     * You would call this method actually (for example when loading the {@link UI} instance).
     * 
     * @param target
     *            the component to scroll into
     * @param horizontal
     *            the direction of the scroll to trigger
     */
    public void scrollTo(AbstractComponent target, boolean horizontal)
    {
        scrollTo(target, UI.getCurrent(), horizontal);
    }

    /**
     * Scrolls into the specified vaadin component using the default settings.
     * 
     * Note that the first scroll will take some time due to the browser loading the necessary
     * JavaScript files. You can prevent this by calling {@link ScrollHelper#getCurrent()} before
     * You would call this method actually (for example when loading the {@link UI} instance).
     * 
     * @param target
     *            the component to scroll into
     * @param context
     *            the scrollable context of the target. This is the component that has the
     *            scrollbars in the browser. This can be {@link UI}, {@link Panel}, {@link Window}
     *            or other components that generate scrollbars
     * @param horizontal
     *            the direction of the scroll to trigger
     */
    public void scrollTo(AbstractComponent target, AbstractComponent context, boolean horizontal)
    {
        scrollTo(target, context, horizontal, 0, 400);
    }

    /**
     * Scrolls into the specified vaadin component.
     * 
     * Note that the first scroll will take some time due to the browser loading the necessary
     * JavaScript files. You can prevent this by calling {@link ScrollHelper#getCurrent()} before
     * You would call this method actually (for example when loading the {@link UI} instance).
     * 
     * @param target
     *            the component to scroll into
     * @param context
     *            the scrollable context of the target. This is the component that has the
     *            scrollbars in the browser. This can be {@link UI}, {@link Panel}, {@link Window}
     *            or other components that generate scrollbars
     * @param horizontal
     *            the direction of the scroll to trigger
     * @param offset
     *            the amount of pixels that should be left between the target and context after the
     *            scroll. Setting this to <b>0</b> means that after the scroll the top of the target
     *            is at the top of the scrollable. Setting it to a negative value will leave some
     *            space above the target. Setting it to a positive value means that the browser will
     *            scroll over the target.
     * @param speed
     *            the amount of time that the scroll animation should take in ms. Setting this value
     *            to <b>0</b> means that the scroll will have no animation and will happen
     *            immediately.
     */
    public void scrollTo(AbstractComponent target, AbstractComponent context, boolean horizontal,
            int offset, int speed)
    {
        callFunction("scrollTo", target, context, horizontal, offset, speed);
    }

    public static ScrollHelper getCurrent()
    {
        ScrollHelper optioner = null;

        // Search singleton instance
        for (Extension extension : UI.getCurrent().getExtensions())
        {
            if (extension instanceof ScrollHelper)
            {
                optioner = (ScrollHelper) extension;
                break;
            }
        }

        // Create new instance if not found
        if (optioner == null)
        {
            optioner = new ScrollHelper();
            optioner.extend(UI.getCurrent());
        }

        return optioner;
    }

}
