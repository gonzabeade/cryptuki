window.onload = function getFilters() {
    if(window.location.search.length > 0 ){
        var searchParams = new URLSearchParams(window.location.search);
        for(const [key,value] of searchParams){
            document.getElementById(key).value = value;
        }
        document.getElementById("reset").classList.remove("hidden");
    }

}
function resetAllFilters(){
    document.getElementById("reset").classList.add("hidden")
    document.getElementById("coin").options[0].selected = true
    document.getElementById("pm").options[0].selected = true
    document.getElementById("price").value = 0;

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
    document.getElementById("username").value = null;

    deleteParams();

}
function deleteParams() {
    const searchParams = new URLSearchParams(window.location.search);
    for(const [key] of searchParams){
        searchParams.delete(key)
    }
    const newRelativePathQuery = window.location.pathname + '?' + searchParams.toString();
    history.pushState(null, '', newRelativePathQuery);
    document.getElementById("link").href = newRelativePathQuery;
}