window.de_elrassel_vaadin_appear_InviewExtensionImpl = function() {

	// extension connector
	var connector = this;

	// id of extended connector
	var parentConnectorId = connector.getParentId();

	// element of extended connector
	var element = connector.getElement(parentConnectorId);

	// connector id of context (scrollable)
	var contextConnectorId = connector.getState().contextConnectorId;

	var context; // the scrollable element
	if (contextConnectorId == null) {
		// when no context was specified -> use window
		context = window;
	} else {
		// otherwise use specified context
		context = connector.getElement(contextConnectorId);

		if (context == null) {
			throw "No element found for specified contextConnectorId: "
					+ contextConnectorId;
		}

		// find scrollable dom for vaadin component dom
		context = window.findScrollableOfContext(context);
	}

	var inview = new Waypoint.Inview({
		element : element,
		enter : function(direction) {
			if (connector.getState().registeredEventListeners
					.indexOf("event_enter") != -1) {
				connector.onEnter(direction);
			}
		},
		entered : function(direction) {
			if (connector.getState().registeredEventListeners
					.indexOf("event_entered") != -1) {
				connector.onEntered(direction);
			}
		},
		exit : function(direction) {
			if (connector.getState().registeredEventListeners
					.indexOf("event_exit") != -1) {
				connector.onExit(direction);
			}
		},
		exited : function(direction) {
			if (connector.getState().registeredEventListeners
					.indexOf("event_exited") != -1) {
				connector.onExited(direction);
			}
		},
		context : context,
		horizontal : connector.getState().horizontal
	});

	this.onStateChange = function() {
		if (connector.getState().enabled) {
			inview.enable();
		} else {
			inview.disable();
		}
	}

	this.destroy = function() {
		inview.destroy();
	}

}