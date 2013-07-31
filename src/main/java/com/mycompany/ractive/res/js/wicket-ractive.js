(function() {
	"use strict";

	if (typeof(Wicket) === "undefined") {
		window.Wicket = {};
	}

	if (typeof(Wicket.Ractive) !== "undefined") {
		return;
	}

	Wicket.Ractive = {
		register: function(markupId, config) {
			if (Wicket.Ractive[markupId]) {
				Wicket.Ractive[markupId].set(config.data);
			}
			else {
				Wicket.Ractive[markupId] = new Ractive(config);
			}
		}
	};

	$(document).on("unload", function() {
		var ractive;

		for (ractive in Wicket.Ractive) {
			if (ractive.hasOwnProperty('teardown')) {
				ractive.teardown();
			}
		}
	});
})();
