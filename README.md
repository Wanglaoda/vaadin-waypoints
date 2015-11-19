# Waypoints Add-on for Vaadin 7

This is an add-on for Vaadin 7 providing a wrapper for the JavaScript library 'Waypoints'. You can check out the original library on  
http://imakewebthings.com/waypoints/
With this add-on you can receive events when you scroll to a Vaadin component.

## Online demo

Try the add-on demo at http://env-9019740.jelastic.servint.net/waypoints-demo/ (may take a while to load)

## Download release

Official releases of this add-on are available at Vaadin Directory. For Maven instructions, download and reviews, go to http://vaadin.com/addon/waypoints

## Building and running demo

git clone https://github.com/christiandusse/vaadin-waypoints
mvn clean install
cd demo
mvn jetty:run

To see the demo, navigate to http://localhost:8080/waypoints-demo/

## Development with Eclipse IDE

For further development of this add-on, the following tool-chain is recommended:
- Eclipse IDE
- m2e wtp plug-in (install it from Eclipse Marketplace)
- Vaadin Eclipse plug-in (install it from Eclipse Marketplace)
- JRebel Eclipse plug-in (install it from Eclipse Marketplace)
- Chrome browser

### Importing project

Choose File > Import... > Existing Maven Projects

Note that Eclipse may give "Plugin execution not covered by lifecycle configuration" errors for pom.xml. Use "Permanently mark goal resources in pom.xml as ignored in Eclipse build" quick-fix to mark these errors as permanently ignored in your project. Do not worry, the project still works fine. 

### Debugging server-side

If you have not already compiled the widgetset, do it now by running vaadin:install Maven target for waypoints-root project.

If you have a JRebel license, it makes on the fly code changes faster. Just add JRebel nature to your waypoints-demo project by clicking project with right mouse button and choosing JRebel > Add JRebel Nature

To debug project and make code modifications on the fly in the server-side, right-click the waypoints-demo project and choose Debug As > Debug on Server. Navigate to http://localhost:8080/waypoints-demo/ to see the application.

### Debugging client-side

The most common way of debugging and making changes to the client-side code is dev-mode. To create debug configuration for it, open waypoints-demo project properties and click "Create Development Mode Launch" button on the Vaadin tab. Right-click newly added "GWT development mode for waypoints-demo.launch" and choose Debug As > Debug Configurations... Open up Classpath tab for the development mode configuration and choose User Entries. Click Advanced... and select Add Folders. Choose Java and Resources under waypoints/src/main and click ok. Now you are ready to start debugging the client-side code by clicking debug. Click Launch Default Browser button in the GWT Development Mode in the launched application. Now you can modify and breakpoints to client-side classes and see changes by reloading the web page. 

Another way of debugging client-side is superdev mode. To enable it, uncomment devModeRedirectEnabled line from the end of DemoWidgetSet.gwt.xml located under waypoints-demo resources folder and compile the widgetset once by running vaadin:compile Maven target for waypoints-demo. Refresh waypoints-demo project resources by right clicking the project and choosing Refresh. Click "Create SuperDevMode Launch" button on the Vaadin tab of the waypoints-demo project properties panel to create superder mode code server launch configuration and modify the class path as instructed above. After starting the code server by running SuperDevMode launch as Java application, you can navigate to http://localhost:8080/waypoints-demo/?superdevmode. Now all code changes you do to your client side will get compiled as soon as you reload the web page. You can also access Java-sources and set breakpoints inside Chrome if you enable source maps from inspector settings. 

 
## Release notes

### Version 0.0.1-SNAPSHOT
- Providing basic functionality for Waypoints.
- WaypointExtension for creating a Waypoint on a Vaadin component
- InviewExtension to create a Waypoint shortcut on a Vaadin component
- Infinite Scroll has no extension. To provide the same functionality You can use an InviewExtension and add more elements when the InviewEvent is fired.
- Sticky Elements (http://imakewebthings.com/waypoints/shortcuts/sticky-elements/) not supported
- Can use the following vaadin components as context: UI, Window, Panel, Table, HorizontalSplitPanel, VerticalSplitPanel
- Option to use custom scrollable Vaadin components as context

- Known issues:
- May lead to infinite loop when scrolling fast while having set a lot of waypoints
- May not receive events on browser size change
- May receive wrong events when having multiple scrollbars in the hierarchy of the extended component
- May receive wrong events for components inside a table
- No support for cells inside a Vaadin grid

## Roadmap

This component is developed as a hobby with no public roadmap or any guarantees of upcoming releases. That said, the following features are planned for upcoming releases:
- Sticky Elements: http://imakewebthings.com/waypoints/shortcuts/sticky-elements/
- Support for cells inside a Vaadin grid
- Events on scrolling because of browser size change
- Settings options lazily
- Provide more options from original library: 'group', 'continuous' http://imakewebthings.com/waypoints/api/waypoint/
- Performance optimization (f.e. option to only receive the last event when scrolling over multiple waypoints at once)
- Better documentation
- Integration tests

## Issue tracking

The issues for this add-on are tracked on its github.com page. All bug reports and feature requests are appreciated. 

## Contributions

Contributions are welcome, but there are no guarantees that they are accepted as such. Process for contributing is the following:
- Fork this project
- Create an issue to this project about the contribution (bug or feature) if there is no such issue about it already. Try to keep the scope minimal.
- Develop and test the fix or functionality carefully. Only include minimum amount of code needed to fix the issue.
- Refer to the fixed issue in commit
- Send a pull request for the original project
- Comment on the original issue that you have implemented a fix for it

## License & Author

Add-on is distributed under MIT License. For license terms, see LICENSE.

This addon is written by Christian Duße

# Developer Guide

## Getting started

Here is a simple example on how to try out the add-on component:

```
    AbstractComponent yourComponent = ... // the component you want to receive events for
    AbstractComponent context = ... // Your scrollable Vaadin component that directly on indirectly contains 'yourComponent' (there may be some layout hierarchies between the context and the component). Context can be one of UI, Panel, Window, Table, HorizontalSplitPanel, VerticalSplitPanel
    boolean horizontal = false; // is vertical scrollbar
    InviewExtension extension = new InviewExtensionImpl(yourComponent, context, horizontal);
    ext.addEnterListener(new EnterListener()
    {
        @Override
        public void onEnter(EnterEvent event)
        {
            // is fired when You scroll into 'yourComponent'
        }
    });
```

For a more comprehensive example, see
- waypoints-demo\src\main\java\eu\dusse\vaadin\waypoints\demo\DemoWaypoint.java
- waypoints-demo\src\main\java\eu\dusse\vaadin\waypoints\demo\DemoInview.java
- waypoints-demo\src\main\java\eu\dusse\vaadin\waypoints\demo\DemoInfiniteScroll.java

## Features

### Feature A

Receiving events when element is scrolled into viewport. 
This can be used to load content lazily or to track user behavior.

## API

Waypoints JavaDoc is available online at https://github.com/christiandusse/vaadin-waypoints