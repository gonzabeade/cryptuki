window.onload = function successMessage() {
    const searchParams = new URLSearchParams(window.location.search);
    console.log(searchParams)
    if (searchParams.has("success")) {
        var element = document.getElementById("confirmationToggle")
        element.classList.remove('hidden')
    }

}