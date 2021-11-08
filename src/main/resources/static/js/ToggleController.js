$(document).ready(function() {

    $("#icon-click").click(function() {
        console.log("This is the togglefunction");

        var inputfieldPassword1 = $("#inputfieldPassword1");

        if (inputfieldPassword1.attr("type") === "password") {
            inputfieldPassword1.attr("type", "text");
            document.getElementById("iconPasswordEyeVisible").src="/images/icons/icons8-hide-24.png";
        }
        else {
        inputfieldPassword1.attr("type", "password");
            document.getElementById("iconPasswordEyeVisible").src="/images/icons/icons8-eye-24.png";
    }
    })});