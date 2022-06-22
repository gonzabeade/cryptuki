window.onload = function successMessage() {
    const searchParams = new URLSearchParams(window.location.search);
    if (searchParams.has("success")) {
        var element = document.getElementById("confirmationToggle")
        element.classList.remove('hidden')
    }

}