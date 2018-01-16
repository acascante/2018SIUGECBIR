/*
 * SimpleModal OSX Style Modal Dialog
 * http://www.ericmmartin.com/projects/simplemodal/
 * http://code.google.com/p/simplemodal/
 *
 * Copyright (c) 2010 Eric Martin - http://ericmmartin.com
 * Modify 2011 Jonnathan Sanchez
 * Licensed under the MIT license:
 *   http://www.opensource.org/licenses/mit-license.php
 *
 * Revision: $Id: osx.js 238 2010-03-11 05:56:57Z emartin24 $
 */
function click_boton(){
   var OSX = {
		container: null,
		init: function () {

				jQuery("#osx-modal-content").modal({
					overlayId: 'osx-overlay',
					containerId: 'osx-container',
					closeHTML: null,
					minHeight: 80,
					opacity: 65,
					position: ['0',],
					overlayClose: true,
					onOpen: OSX.open,
					onClose: OSX.close
				});

		},
		open: function (d) {
			var self = this;
			self.container = d.container[0];
			d.overlay.fadeIn('slow', function () {
				jQuery("#osx-modal-content", self.container).show();
				var title = jQuery("#osx-modal-title", self.container);
				title.show();
				d.container.slideDown('slow', function () {
					setTimeout(function () {
						var h = jQuery("#osx-modal-data", self.container).height()
							+ title.height()
							+ 20; // padding
						d.container.animate(
							{height: h},
							200,
							function () {
								jQuery("div.close", self.container).show();
								jQuery("#osx-modal-data", self.container).show();
							}
						);
					}, 300);
				});
			})
		},
		close: function (d) {
			var self = this; // this = SimpleModal object
			d.container.animate(
				{top:"-" + (d.container.height() + 20)},
				500,
				function () {
					self.close(); // or $.modal.close();
				}
			);
		}
	};
OSX.init();
}