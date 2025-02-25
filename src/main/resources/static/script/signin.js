window.addEventListener('DOMContentLoaded', () => {
    const form = document.getElementById("signin-form");
    const verifyBtn = document.getElementById("email-verify-btn");
    const username = document.getElementById("username")
    const password = document.getElementById("password")

    verifyBtn.addEventListener("click", async () => {
        const username = document.getElementById("username").value;

        if(username){
            alert("인증코드 발송 완료!");
            document.getElementById("verification-code-container").style.display = "block";
        }

        await sendEmailByUsername(username);
    });

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
        const code = document.getElementById("verification-code").value;

        await loginAction(username, password, code);
    });
})

async function sendEmailByUsername(username) {
    const response = await fetch("/send-verification", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            "username": username
        })
    });
    const json = await response.json();
    return json.isValid;
}

async function loginAction(username, password, code) {
    const response = await fetch("/users/action/signin", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            "username": username,
            "password" : password,
            "code" : code
        })
    });

    if (response.ok) {
        window.location.href = "/";
        alert("로그인 성공!");
        return true;
    } else {
        const json = await response.json();
        alert(`오류 : ${json.message}`);
        return json.isValid;
    }
}