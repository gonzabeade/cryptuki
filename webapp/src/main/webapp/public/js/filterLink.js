
window.onload = async function onStart() {
    await setCryptoPrice();
    getFilters();

}

function getFilters() {
    if (window.location.search.length > 0) {
        var searchParams = new URLSearchParams(window.location.search);
        let elementToSelect;
        for (const [key, value] of searchParams) {
            elementToSelect = document.getElementById(key);
            if (elementToSelect != null) {
                elementToSelect.value = value;
            }
        }

        // let reset = document.getElementById("reset");
        // if (reset !== null) {
        //     reset.classList.remove("hidden");
        // }
        // if(searchParams.get("coin")!= null || searchParams.get("location")!= null) {
        //     document.getElementById("reset").classList.remove("hidden")
        // }

    }

    if (searchParams.has("orderingCriterion")) {
        document.getElementById("orderingCriterion").options[searchParams.get("orderingCriterion")].selected = true
    }
    if(searchParams.has("location")){
        document.getElementById("location-selected").classList.remove("hidden");
        document.getElementById("hood-selected").innerText= searchParams.get("location");
    }

}
function resetAllFilters(){
    document.getElementById("reset").classList.add("hidden")
    document.getElementById("coin").options[0].selected = true
    document.getElementById("location").options[0].selected = true
    deleteParams();
}
function addPageValue(value){
    var searchParams = new URLSearchParams(window.location.search)
    searchParams.set("page",value);
    var newRelativePathQuery = window.location.pathname + '?' + searchParams.toString();
    history.pushState(null, '', newRelativePathQuery);
    window.location.href = newRelativePathQuery;
}
function addQueryParam(id) {
    var searchParams = new URLSearchParams(window.location.search)
    let value = document.getElementById(id).value;
    if(value === ''){
        searchParams.delete(id);
    }else{
        searchParams.set(id,value);
    }
    searchParams.delete("page")
    var newRelativePathQuery = window.location.pathname + '?' + searchParams.toString();
    history.pushState(null, '', newRelativePathQuery);
    document.getElementById("link").href = newRelativePathQuery;

    if(id === "coin" || id === "location") {
        document.getElementById("reset").classList.remove("hidden")
    }

}
function resetAllAdminFilters(){
    document.getElementById("reset").classList.add("hidden")
    document.getElementById("fromDate").value = null
    document.getElementById("toDate").value = null
    document.getElementById("offerId").value = null;
    document.getElementById("tradeId").value = null;
    document.getElementById("complainer").value = null;

    deleteParams();

}
function deleteParams() {
    const searchParams = new URLSearchParams(window.location.search);
    const searchParamsCopy = new URLSearchParams(window.location.search)

    for(let [key] of searchParamsCopy){
        searchParams.delete(key);
    }

    const newRelativePathQuery = window.location.pathname + '?' + searchParams.toString();
    history.pushState(null, '', newRelativePathQuery);
    document.getElementById("link").href = newRelativePathQuery;
}
function hide(name){
    if(name === "desc"){
        document.getElementsByName("asc")[0].classList.remove("hidden");
    }else{
        document.getElementsByName("desc")[0].classList.remove("hidden");
    }
    document.getElementsByName(name)[0].classList.add("hidden");
}
function sendGet(){
    var searchParams = new URLSearchParams(window.location.search);
    document.getElementById("link").href = window.location.pathname + '?' + searchParams.toString();
    console.log(document.getElementById("link").href)
    document.getElementById("link").click();
}
function addDirection(id) {
    let selectValue = document.getElementById(id).value;
    var searchParams = new URLSearchParams(window.location.search);
    console.log(selectValue)
    if(selectValue === ''){
        searchParams.delete(id);
    }else if(selectValue === "2"){
        searchParams.set('orderingDirection', "1")
    }else{
        searchParams.delete('orderingDirection')
    }

    searchParams.delete("page")
    var newRelativePathQuery = window.location.pathname + '?' + searchParams.toString();
    history.pushState(null, '', newRelativePathQuery);
    document.getElementById("link").href = newRelativePathQuery;

}
function removeLocationParam() {
    document.getElementById("location-selected").classList.add("hidden");
    var searchParams = new URLSearchParams(window.location.search);
    searchParams.delete("location")
    var newRelativePathQuery = window.location.pathname + '?' + searchParams.toString();
    history.pushState(null, '', newRelativePathQuery);
    location.reload()
}
