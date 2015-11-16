// deprecated. that's why we override this function in the next function
window.findScrollableOfContext = function(context) {
	// if the dom of the specified context component is not scrollable,
	// find the scrollable dom (needed for panel or window)
	if (!($(context).hasClass("v-scrollable"))) {
		context = $(context).find(".v-scrollable:first");
	}
	return context;
}

// upper implementation does not work (f.e. when ui has no scrollbars yet,
// it won't have the class name 'v-scrollable'
// That is why we override that function
// If You have a custom component that contains a scrollable div,
// You might want to override this function, too
window.findScrollableOfContext = function(context) {
	var classList = context.classList;
	if (classList.contains("v-ui")) {
		// the v-ui element itself is the scrollable
		return context;
	} else if (classList.contains("v-panel")) {
		// v-panel v-scrollable
		// second child of panel is the scrollable
		return context.children[1];
	} else if (classList.contains("v-window")) {
		// v-window popupContent v-window-wrap v-window-contents
		// v-scrollable
		return context.children[0].children[0].children[2].children[0];
	} else {
		console
				.warn("no handler for getting scrollable of context by classlist: "
						+ classList);
		// use the dom element itself if no handler was found
		return context;
	}
}
