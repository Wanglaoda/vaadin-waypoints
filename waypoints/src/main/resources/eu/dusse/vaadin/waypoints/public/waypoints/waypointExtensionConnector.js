/*
 * Copyright 2015 Christian Duße
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

window.eu_dusse_vaadin_waypoints_WaypointExtensionImpl = function() {

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

	var waypoint = new Waypoint({
		element : element,
		handler : function(direction) {
			if (connector.getState().hasListeners) {
				connector.crossed(direction);
			}
		},
		context : context,
		horizontal : connector.getState().horizontal,
		offset : connector.getState().offset
	});

	this.onStateChange = function() {
		if (connector.getState().enabled) {
			waypoint.enable();
		} else {
			waypoint.disable();
		}
	}

	this.destroy = function() {
		waypoint.destroy();
	}

}