function showSendButton(){
    document.getElementById("sendProfile").click()
}
function getUploadedFileName(input){
    var fullPath = input.value;
    if (fullPath) {
        var startIndex = (fullPath.indexOf('\\') >= 0 ? fullPath.lastIndexOf('\\') : fullPath.lastIndexOf('/'));
        var filename = fullPath.substring(startIndex);
        if (filename.indexOf('\\') === 0 || filename.indexOf('/') === 0) {
            filename = filename.substring(1);
        }
    }
    document.getElementById("fileName").innerText=filename
}