/* This file is part of the requestForm.html view. It returns  a list of gardens of which the user has not joined yet.

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
    var searchPatch = "/gardens/requests";
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

    data.forEach(gardenDTO => {
        let text = document.createTextNode(gardenDTO.gardenName);
        let listItem = document.createElement("li");
        let gardenContainerDiv = document.createElement("div");
        gardenContainerDiv.classList.add("igadi-resultList-appear");
        let addButton = document.createElement("button");
        let buttonImg = document.createElement("img");
        buttonImg.src = "/images/icons/plus.png";
        buttonImg.classList.add("igadi-add-gardener-img", "float-right");
        let addForm = document.createElement("form");
        let addMapping = "/gardens/requests/" + gardenDTO.gardenId;
        listItem.classList.add("list-unstyled");
        addButton.classList.add("btn", "btn-outline-success", "w-100", "igadi-add-person-btn");
        addForm.classList.add("w-100");
        addButton.setAttribute("type", "submit");
        addForm.setAttribute("action", addMapping);
        addForm.setAttribute("method", "post");
        addForm.setAttribute("object", "${gardenDTO}");
        addButton.innerHTML = gardenDTO.gardenName;
        addButton.appendChild(buttonImg);
        addForm.appendChild(addButton);
        gardenContainerDiv.appendChild(addForm);
        listItem.appendChild(gardenContainerDiv);
        newList.appendChild(listItem);
    });

    let oldList = document.getElementById("result").firstChild;
    document.getElementById("result").replaceChild(newList, oldList);
}