/* This file is part of the users.html view. It returns  a list of users for the admin to see.

- Lukas de Ruiter
 */

$(document).ready(function () {

    $("#search-form").submit(function (event) {
        event.preventDefault();
        fire_ajax_submit();
    });
});

function fire_ajax_submit() {

    var searchData = {};
    var searchPatch = "/users";
    searchData["keywords"] = $("#keywordsBox").val();

    $("#btn-search").prop("disabled", true);

    $.ajax(
        {
            type: "POST",
            contentType: "application/json",
            url: searchPatch,
            data: JSON.stringify(searchData),
            dataType: 'json',
            cache: false,
            timeout: 6000,

            success: function (resultData) {
                fillTable(resultData)
                console.log("POST: ", searchData);
                console.log("SUCCESS : ", resultData);
                $("#btn-search").prop("disabled", false);
            },
            error: function (e) {
                console.log("ERROR : ", e);
                $("#btn-search").prop("disabled", false);
            }
        });
}

function fillTable(data){
    let newList = document.createElement("ul");
    newList.classList.add("list-group-item", "p-0", "m-0", "w-100");
    //Array.from(document.getElementsByClassName("modalfade")).forEach(el=>el.remove());


    data.forEach(userDTO => {
        let text = document.createTextNode(userDTO.userName);
        let listItem = document.createElement("li");
        let userContainerDiv = document.createElement("div");
        userContainerDiv.classList.add("igadi-resultList-appear");
        let userText = document.createElement("p");
        userText.innerHTML = userDTO.userName;
        userText.classList.add("text-center", "h-100", "m-0", "w-75");


        let deleteImg = document.createElement("img");
        let buttonImg2 = document.createElement("img");
        deleteImg.src = "/images/icons/bin.png";
        deleteImg.classList.add("igadi-delete-user-img", "float-right");
        buttonImg2.src = "/images/icons/pen.png";
        buttonImg2.classList.add("igadi-delete-user-img", "float-right");


        let addForm = document.createElement("form");
        let deleteButton = document.createElement("button");
        let addMapping = "/users/delete/" + userDTO.userId;

        let addForm2 = document.createElement("form");
        let addButton2 = document.createElement("button");
        let addMapping2 = "/users/edit/" + userDTO.userId;
//data-toggle="modal" data-target="#igadiGardenDeleteModal">
        listItem.classList.add("list-unstyled");

        deleteButton.classList.add("btn", "w-100", "igadi-delete-user-btn");
        deleteButton.setAttribute("data-toggle", "modal");
        deleteButton.setAttribute("data-target", `#userNum${userDTO.userId}`);

        addForm.setAttribute("action", addMapping);
        addForm.setAttribute("method", "post");
        addForm.setAttribute("object", "${user}");
        addForm.classList.add("w-25");
        deleteButton.appendChild(deleteImg);

        addButton2.classList.add("btn", "w-100", "igadi-change-user-btn");
        addButton2.setAttribute("type", "submit");
        addForm2.setAttribute("action", addMapping);
        addForm2.setAttribute("method", "post");
        addForm2.setAttribute("object", "${user}");
        addButton2.appendChild(buttonImg2);
        addForm2.appendChild(addButton2);

        userContainerDiv.appendChild(userText);
        userContainerDiv.appendChild(deleteButton);
        userContainerDiv.appendChild(addForm2);
        listItem.appendChild(userContainerDiv);
        newList.appendChild(listItem);

        let modalDiv1 = document.createElement("div");
        modalDiv1.classList.add("modal", "modalfade");
        modalDiv1.setAttribute("id", `userNum${userDTO.userId}`);
        modalDiv1.setAttribute("tabindex", "-1");
        modalDiv1.setAttribute("role", "dialog");
        modalDiv1.setAttribute("aria-labelledby", "myModalLabel");

        let modalDiv2 = document.createElement("div");
        modalDiv2.classList.add("modal-dialog");
        modalDiv2.setAttribute("role", "document");

        let modalDiv3 = document.createElement("div");
        modalDiv3.classList.add("modal-content");

        let modalDiv4 = document.createElement("div");
        modalDiv4.classList.add("modal-header", "w-100", "d-flex",
            "justify-content-center", "text-center");

        let modalTitle = document.createElement("h5");
        modalTitle.classList.add("modal-title");
        modalTitle.setAttribute("id", "myModalLabel");
        modalTitle.innerHTML = "Deleting user " + userDTO.userName;

        let modalCancelBtn = document.createElement("button");
        modalCancelBtn.classList.add("close");
        modalCancelBtn.setAttribute("type", "button");
        modalCancelBtn.setAttribute("data-dismiss", "modal");
        modalCancelBtn.setAttribute("aria-label", "Close");

        let modalCancelSpan = document.createElement("span");
        modalCancelSpan.setAttribute("aria-hidden", "true");
        modalCancelSpan.innerHTML = "&times;";

        let modalDiv5 = document.createElement("div");
        modalDiv5.classList.add("modal-body", "w-100", "d-flex", "justify-content-center", "text-center",
            "d-flex", "flex-column");

        let modalTextTitle = document.createElement("h4");
        modalTextTitle.innerHTML = "Are you sure?";

        let modalText = document.createElement("p");
        modalText.innerHTML = "* This user will be permanently removed";

        let modalDiv6 = document.createElement("div");
        modalDiv6.classList.add("modal-footer", "w-100", "d-flex", "justify-content-center");

        let submitBtn = document.createElement("button");
        submitBtn.classList.add("w-100", "btn", "btn-danger", "taskDeleteBtn", "w-25");
        submitBtn.innerHTML = "Yes";

        let modalDismissBtn = document.createElement("button");
        modalDismissBtn.classList.add("btn", "btn-success", "w-25");
        modalDismissBtn.setAttribute("data-dismiss", "modal");
        modalDismissBtn.innerHTML = "No";


        addForm.appendChild(submitBtn);
        modalDiv6.appendChild(addForm);
        modalDiv6.appendChild(modalDismissBtn);
        modalDiv5.appendChild(modalTextTitle);
        modalDiv5.appendChild(modalText);
        modalCancelBtn.appendChild(modalCancelSpan);
        modalDiv4.appendChild(modalTitle);
        modalDiv4.appendChild(modalCancelBtn);
        modalDiv3.appendChild(modalDiv4);
        modalDiv3.appendChild(modalDiv5);
        modalDiv3.appendChild(modalDiv6);
        modalDiv2.appendChild(modalDiv3);
        modalDiv1.appendChild(modalDiv2);
        document.getElementById("body").appendChild(modalDiv1);
    });

    let oldList = document.getElementById("result").firstChild;
    document.getElementById("result").replaceChild(newList, oldList);
}