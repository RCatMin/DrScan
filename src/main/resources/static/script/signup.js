import { validateUsername, validatePassword, validateEmail, validateName, validatePhone, formatPhoneString } from "./validation.js";

window.addEventListener('DOMContentLoaded', () => {
    const form = document.getElementById("signup-form");
    const verifyBtn = document.getElementById("email-verify-btn");
    const username = document.getElementById("username")
    const password = document.getElementById("password")
    const name = document.getElementById("name")
    const email = document.getElementById("email")
    const phone = document.getElementById("phone")

    verifyBtn.addEventListener("click", async () => {
        const email = document.getElementById("email").value;

        if(email){
            document.getElementById("verification-code-container").style.display = "block";
        }

        await sendEmail(email);
    });

    username.addEventListener("focusout", async () => {
       let target = username.value;
       const msg = document.getElementById("error-username");

       if(target === "" || !validateUsername(target)) {
           msg.style.display = "block";
       } else {
           msg.style.display = "none";
       }

    });

    password.addEventListener("focusout", async () => {
        let target = password.value;
        const msg = document.getElementById("error-password");

        if(target === "" || !validatePassword(target)) {
            msg.style.display = "block";
        } else {
            msg.style.display = "none";
        }

    });

    name.addEventListener("focusout", async () => {
        let target = name.value;
        const msg = document.getElementById("error-name");

        if(target === "" || !validateName(target)) {
            msg.style.display = "block";
        } else {
            msg.style.display = "none";
        }

    });

    email.addEventListener("focusout", async () => {
        let target = email.value;
        const msg = document.getElementById("error-email");

        if(target === "" || !validateEmail(target)) {
            msg.style.display = "block";
        } else {
            msg.style.display = "none";
        }

    });

    phone.addEventListener("focusout", async () => {
        let target = phone.value;
        const msg = document.getElementById("error-phone");

        if(target === "" || !validatePhone(target)) {
            msg.style.display = "block";
        } else {
            msg.style.display = "none";
        }

    });

    form.addEventListener("submit", async function (event) {
        event.preventDefault();

        const username = document.getElementById("username").value;
        const password = document.getElementById("password").value;
        const hospital = document.getElementById("hospital").value;
        const department = document.getElementById("department").value;
        const name = document.getElementById("name").value;
        const email = document.getElementById("email").value;
        const phone = document.getElementById("phone").value;

        await registAction(username, password, hospital, department, name, email, phone);
    });

});

async function sendEmail(email) {
    const response = await fetch("/send-verification", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            "email": email
        })
    });
    const json = await response.json();
    return json.isValid;
}

async function registAction(username, password, hospital, department, name, email, phone) {
    const response = await fetch("/users/action/signup", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            "username": username,
            "password" : password,
            "hospital": hospital,
            "department": department,
            "name": name,
            "email": email,
            "phone": phone
        })
    });

    if (response.ok) {
        window.location.href = "/users/signin";
    } else {
        const text = await response.json();
        alert(`오류 : ${text.message}`);
    }

    return json.isValid;
}