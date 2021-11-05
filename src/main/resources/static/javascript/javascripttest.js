document.getElementById("clickkie").addEventListener("click", function() {
    clickkie();
})

function clickkie() {
    const yourSound = new Audio("/javascript/sound/untitled.mp3");
    console.log(yourSound.src);
    yourSound.play().then(r => {

    }).catch(error => {
        console.log(error);
    });
    console.log("tes");
}