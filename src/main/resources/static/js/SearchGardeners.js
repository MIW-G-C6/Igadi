$(document).ready(function () {

    $("#search-form").submit(function (event) {
        event.preventDefault();
        fire_ajax_submit();
    });
});

function fire_ajax_submit() {

    var searchData = {};
    var searchPatch = "/overview/details/" + document.getElementById("garden").innerHTML + "/gardeners";
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

    data.forEach(userDTO => {
        let text = document.createTextNode(userDTO.userName);
        let listItem = document.createElement("li");
        let userContainerDiv = document.createElement("div");
        userContainerDiv.classList.add("igadi-resultList-appear");
        let userText = document.createElement("p");
        userText.innerHTML = userDTO.userName;
        let addButton = document.createElement("button");
        let buttonImg = document.createElement("img");
        buttonImg.src = "/images/icons/plus.png";
        buttonImg.classList.add("igadi-add-gardener-img", "float-right");
        let addForm = document.createElement("form");
        let addMapping = "/overview/details/" + document.getElementById("garden").innerHTML +
            "/gardeners/" + userDTO.userId;
        listItem.classList.add("list-unstyled");
        addButton.classList.add("btn", "btn-outline-success", "w-100", "igadi-add-person-btn");
        addForm.classList.add("w-100");
        addButton.setAttribute("type", "submit");
        addForm.setAttribute("action", addMapping);
        addForm.setAttribute("method", "post");
        addForm.setAttribute("object", "${user}");
        addButton.innerHTML = userDTO.userName;
        addButton.appendChild(buttonImg);
        addForm.appendChild(addButton);
        userContainerDiv.appendChild(addForm);
        listItem.appendChild(userContainerDiv);
        newList.appendChild(listItem);
    });

    let oldList = document.getElementById("result").firstChild;
    document.getElementById("result").replaceChild(newList, oldList);
}