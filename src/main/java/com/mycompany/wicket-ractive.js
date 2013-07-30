(function() {
	"use strict";

	if (typeof(Wicket) === "undefined") {
		window.Wicket = {};
	}

	if (typeof(Wicket.Ractive) !== "undefined") {
		return;
	}

	Wicket.Ractive = {
		register: function(markupId, data) {
			if (Wicket.Ractive[markupId]) {
				Wicket.Ractive[markupId].set(data);
			}
			else {
				Wicket.Ractive[markupId] = new Ractive({
					debug: true,

					el: markupId,
					template: '#' + markupId,
					data: data
				});
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
