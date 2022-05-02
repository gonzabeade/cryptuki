function changeBorderColor(label) {
    console.log(label)
    if(label.matches(".checked")){
        label.classList.remove("checked");
        label.classList.remove("border-frostdr");
        label.classList.add("border-[#FAFCFF]")
    }else {
        label.classList.remove("border-[#FAFCFF]")
        label.classList.add("border-frostdr");
        label.classList.add("checked");
    }

}
function updateVars(value) {
    document.getElementById("minCoin").innerText = value;
    document.getElementById("maxCoin").innerText = value;
}