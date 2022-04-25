function changeBorderColor(label) {
    if(label.matches(".checked")){
        label.classList.remove("checked");
        label.classList.remove("border-frostdr");
    }else {
        label.classList.add("border-frostdr");
        label.classList.add("checked");
    }

}
function updateVars(value) {
    document.getElementById("minCoin").innerText = value;
    document.getElementById("maxCoin").innerText = value;
}