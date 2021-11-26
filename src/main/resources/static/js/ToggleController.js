/* This file is part of the userCreateAccountPage.html view.

- Annemarleen Bosma
 */

$(document).ready(function() {

    $("#icon-click").click(function(event) {
        event.preventDefault();

        togglePasswordEye();
});

function togglePasswordEye() {
    let inputfieldPassword1 = $("#inputfieldPassword1");
    let inputfieldRepeatPassword1 = $("#inputfieldRepeatPassword1");

    if (inputfieldPassword1.attr("type") === "password" || inputfieldRepeatPassword1 === "password"){
        inputfieldPassword1.attr("type", "text");
        inputfieldRepeatPassword1.attr("type", "text");
        document.getElementById("iconPasswordEyeVisible").src="/images/icons/icons8-hide-24.png";
    } else {
        inputfieldPassword1.attr("type", "password");
        inputfieldRepeatPassword1.attr("type", "password");
        document.getElementById("iconPasswordEyeVisible").src="/images/icons/icons8-eye-24.png";
    }
    };
});