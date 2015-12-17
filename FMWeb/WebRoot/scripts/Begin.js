
$(function () {
    var bars = '.jspHorizontalBar, .jspVerticalBar';

    $('.scroll-pane').bind('jsp-initialised', function (event, isScrollable)
    {

        //hide the scroll bar on first load hide or show
        $(this).find(bars).show();

    }).jScrollPane().hover(

    //hide show scrollbar
				function () {
				    $(this).find(bars).stop().fadeTo('fast', 1); // 0.9
				},
				function () {
				    $(this).find(bars).stop().fadeTo('fast', 1); // 0
				}

			);

});
