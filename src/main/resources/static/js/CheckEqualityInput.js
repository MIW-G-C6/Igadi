/* This file is part of the profileSettings.html view.

- Annemarleen Bosma
 */

$(document).ready(function() {

    $("#email").click(function(event) {
        event.preventDefault();

        checkInput();
    });

    function checkInput() {

        let originalEmail = document.forms["changeProfileSettingsForm"]["originalEmail"].value;
        let newEmail = document.forms["changeProfileSettingsForm"]["newEmail"].value;

        console.log(originalEmail, newEmail);
        validationElement.style.display = 'none';
        validationElement.className = 'validation-image';

        if (newEmail) {
            validationElement.style.display = 'inline-block';
            if (originalEmail === newEmail) {
                validationElement.className += ' validation-success';
            } else {
                validationElement.className += ' validation-error';
            }
        }
    };

});