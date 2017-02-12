$('#login').click(function() {
    $('#signup').fadeOut();
    $('.subtitle-signup').fadeOut();
    $('#login').fadeOut();
    $('.subtitle-login').fadeOut();
    $('#trynow').fadeOut();
    $(".subtitle-trynow").fadeOut();

    $('#backbutton').fadeIn();
    $('.subtitle-login-message').fadeIn();
});

$('#signup').click(function() {
    $('#signup').fadeOut();
    $('.subtitle-signup').fadeOut();
    $('#login').fadeOut();
    $('.subtitle-login').fadeOut();
    $('#trynow').fadeOut();
    $(".subtitle-trynow").fadeOut();

    $('#backbutton').fadeIn();
    $('.subtitle-login-message').fadeIn();
});


$('#backbutton').click(function() {
    $('#signup').fadeIn();
    $('.subtitle-signup').fadeIn();
    $('#login').fadeIn();
    $('.subtitle-login').fadeIn();
    $('#trynow').fadeIn();
    $(".subtitle-trynow").fadeIn();

    $('#backbutton').fadeOut();
    $('.subtitle-login-message').fadeOut();

});
