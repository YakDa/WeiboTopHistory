$(".content").click(function() {
    if ($(this).hasClass("content")) {
        $(this).removeClass("content");
    }
    else {
         $(this).addClass("content");
    }
});