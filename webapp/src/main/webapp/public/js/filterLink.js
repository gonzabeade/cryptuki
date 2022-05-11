window.onload = function getFilters() {
    if(window.location.search.length > 0 ){
        var searchParams = new URLSearchParams(window.location.search);
        let elementToSelect;
        for(const [key,value] of searchParams){
            elementToSelect = document.getElementById(key);
            if(elementToSelect != null){
                elementToSelect.value = value;
            }
        }
        let reset = document.getElementById("reset");
        if(reset !== null){
            reset.classList.remove("hidden");
        }
    }

}
function resetAllFilters(){
    document.getElementById("reset").classList.add("hidden")
    document.getElementById("coin").options[0].selected = true
    document.getElementById("pm").options[0].selected = true
    document.getElementById("price").value = null;

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
    document.getElementById("reset").classList.remove("hidden");
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