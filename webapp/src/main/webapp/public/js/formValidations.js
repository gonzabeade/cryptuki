function preventSubmitPasswordNotMatching(){
    if(document.getElementById("password").value === document.getElementById("repeatPassword").value){
        document.getElementById("registerForm").submit();
    }
}

function preventChangePasswordNotMatching(){
    if(document.getElementById("password").value === document.getElementById("repeatPassword").value){
        document.getElementById("changePasswordForm").submit();
    }
}

function preventRecoverPasswordNotMatching(){
    if(document.getElementById("password").value === document.getElementById("repeatPassword").value){
        document.getElementById("recoverPasswordForm").submit();
    }
}

function passwordMatch(){
    const same = document.getElementById("password").value === document.getElementById("repeatPassword").value;
    if(!same){
        document.getElementById("repeatPassError").classList.remove("hidden");
        document.getElementById("passError").classList.remove("hidden");
    }
    else{
        document.getElementById("repeatPassError").classList.add("hidden");
        document.getElementById("passError").classList.add("hidden");
    }
}


