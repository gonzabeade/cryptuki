function hoverOnRating(number) {
    let element;
    for(let i = 1; i <= number; i++){
        element = document.getElementById("star"+i);
        element.classList.remove("fa-star-o");
        element.classList.add("fa-star");
    }
}
function leaveHoverOnRating(number) {
    let element;
    for(let i = 1; i <= number; i++){
        element = document.getElementById("star"+i);
        element.classList.remove("fa-star");
        element.classList.add("fa-star-o");
    }

}
function setRatingAndSend(rating) {
    let button =  document.getElementById("sendRating");
    document.getElementById("rating").value = rating * 2 ;
    button.click()
}