window.de_elrassel_vaadin_appear_StickyExtensionImpl = function() {

	// extension connector
	var connector = this;

	// id of extended connector
	var parentConnectorId = connector.getParentId();

	// element of extended connector
	var element = connector.getElement(parentConnectorId);

	var sticky = new Waypoint.Sticky({
		element : element,
		direction : connector.getState().direction,
		stuckClass : connector.getState().stuckClass,
		wrapper : connector.getState().wrapper
	});

	this.destroy = function() {
		sticky.destroy();
	}

}