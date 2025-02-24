window.addEventListener('DOMContentLoaded', () => {
    const form = document.getElementById("signin-form");
    const username = document.getElementById("username")
    const password = document.getElementById("password")

    username.addEventListener("focusout", async () => {
        let target = username.value;
        const msg = document.getElementById("error-username");

        if(target === "") {
            msg.style.display = "block";
        } else {
            msg.style.display = "none";
        }

    });

    password.addEventListener("focusout", async () => {
        let target = password.value;
        const msg = document.getElementById("error-password");

        if(target === "") {
            msg.style.display = "block";
        } else {
            msg.style.display = "none";
        }

    });

    form.addEventListener("submit", async function (event) {
        event.preventDefault();

        const username = document.getElementById("username").value;
        const password = document.getElementById("password").value;

        await loginAction(username, password);
    });
})

async function loginAction(username, password) {
    const response = await fetch("/users/action/signin", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            "username": username,
            "password" : password
        })
    });

    if (response.ok) {
        window.location.href = "/";
    } else {
        const text = await response.json();
        alert(`오류 : ${text.message}`);
    }

    return json.isValid;
}