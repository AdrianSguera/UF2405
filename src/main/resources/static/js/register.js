document.getElementById("registerForm").onsubmit = (event) => {
    event.preventDefault();
    if (document.getElementById("password").value === document.getElementById("passRepeat").value)
        document.getElementById("registerForm").submit();
    else
        alert("Different passwords submitted")
}