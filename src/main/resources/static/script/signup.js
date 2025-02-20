window.addEventListener('DOMContentLoaded', () => {
    const verifyBtn = document.getElementById("email-verify-btn");

    verifyBtn.addEventListener("click", async () => {
        const email = document.getElementById("email").value;
        console.log(email)
        await sendEmail(email);
    });

});

async function sendEmail(email) {
    const response = await fetch("/send-verification", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(email)
    });
    const json = await response.json();
    return json.isValid;
}