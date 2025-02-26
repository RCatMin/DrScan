import { validateUsername, validatePassword, validateEmail, validateName, validatePhone, formatPhoneString } from "./validation.js";

window.addEventListener('DOMContentLoaded', () => {
    const form = document.getElementById("me-form");
    const password = document.getElementById("password")
    const hospital = document.getElementById("hospital")
    const department = document.getElementById("department")
    const name = document.getElementById("name")
    const phone = document.getElementById("phone")

    let checkPassword = false;
    let checkHospitalDepartment = false;
    let checkName = false;
    let checkPhone = false;

    password.addEventListener("focusout", async () => {
        let target = password.value;
        const msg = document.getElementById("error-password");
        const inputPassword = document.getElementById("input-password");
        const labelPassword = document.getElementById("label-password");

        if(target === "" || !validatePassword(target)) {
            msg.style.display = "block";
            msg.style.marginBottom = "15px";
            password.style.marginBottom = "5px";
            inputPassword.style.marginBottom = "0";
            labelPassword.style.top = "45%";
            checkPassword = false;
        } else {
            msg.style.display = "none";
            msg.style.marginBottom = "0";
            password.style.marginBottom = "0";
            inputPassword.style.marginBottom = "15px";
            labelPassword.style.top = "50%";
            checkPassword = true;
        }

    });

    hospital.addEventListener("focusout", checkEmptyFields);
    department.addEventListener("focusout", checkEmptyFields);

    name.addEventListener("focusout", async () => {
        let target = name.value;
        const msg = document.getElementById("error-name");
        const inputName = document.getElementById("input-name");
        const labelName = document.getElementById("label-name");

        if(target === "" || !validateName(target)) {
            msg.style.display = "block";
            msg.style.marginBottom = "15px";
            name.style.marginBottom = "5px";
            inputName.style.marginBottom = "0";
            labelName.style.top = "45%";
            checkName = false;
        } else {
            msg.style.display = "none";
            msg.style.marginBottom = "0";
            name.style.marginBottom = "0";
            inputName.style.marginBottom = "15px";
            labelName.style.top = "50%";
            checkName = true;
        }

    });

    phone.addEventListener("focusout", async () => {
        let target = phone.value;
        const msg = document.getElementById("error-phone");
        const inputPhone = document.getElementById("input-phone");
        const labelPhone = document.getElementById("label-phone");

        phone.value = formatPhoneString(target);

        if(target === "" || !validatePhone(phone.value)) {
            msg.style.display = "block";
            msg.style.marginBottom = "15px";
            phone.style.marginBottom = "5px";
            inputPhone.style.marginBottom = "0";
            labelPhone.style.top = "45%";
            checkPhone = false;
        } else {
            msg.style.display = "none";
            msg.style.marginBottom = "0";
            phone.style.marginBottom = "0";
            inputPhone.style.marginBottom = "15px";
            labelPhone.style.top = "50%";
            checkPhone = true;
        }
    });

    form.addEventListener("submit", async function (event) {
        event.preventDefault();

        if(!checkPassword) {
            const msg = document.getElementById("error-password");
            const inputPassword = document.getElementById("input-password");
            const labelPassword = document.getElementById("label-password");

            msg.style.display = "block";
            msg.style.marginBottom = "15px";
            password.style.marginBottom = "5px";
            inputPassword.style.marginBottom = "0";
            labelPassword.style.top = "45%";
        }

        if(!checkHospitalDepartment) {
            const msg = document.getElementById("error-hospital-department");
            const inputHpDp = document.getElementById("input-hospital-department");
            const labelHospital = document.getElementById("label-hospital");
            const labelDepartment = document.getElementById("label-department");

            msg.style.display = "block";
            msg.style.marginBottom = "15px";
            hospital.style.marginBottom = "5px";
            department.style.marginBottom = "5px";
            inputHpDp.style.marginBottom = "0";
            labelHospital.style.top = "45%";
            labelDepartment.style.top = "45%";
        }

        if(!checkName) {
            const msg = document.getElementById("error-name");
            const inputName = document.getElementById("input-name");
            const labelName = document.getElementById("label-name");

            msg.style.display = "block";
            msg.style.marginBottom = "15px";
            name.style.marginBottom = "5px";
            inputName.style.marginBottom = "0";
            labelName.style.top = "45%";
        }

        if(!checkPhone) {
            const msg = document.getElementById("error-phone");
            const inputPhone = document.getElementById("input-phone");
            const labelPhone = document.getElementById("label-phone");

            msg.style.display = "block";
            msg.style.marginBottom = "15px";
            phone.style.marginBottom = "5px";
            inputPhone.style.marginBottom = "0";
            labelPhone.style.top = "45%";
        }

        else if(checkPassword && checkHospitalDepartment && checkName && checkPhone){
            await edit(password.value, hospital.value, department.value, name.value, phone.value);
            alert("test");
        }
    });

    function checkEmptyFields() {
        const msg = document.getElementById("error-hospital-department");
        const inputHpDp = document.getElementById("input-hospital-department");
        const labelHospital = document.getElementById("label-hospital");
        const labelDepartment = document.getElementById("label-department");

        if (!hospital.value.trim() || !department.value.trim()) {
            msg.style.display = "block";
            msg.style.marginBottom = "15px";
            hospital.style.marginBottom = "5px";
            department.style.marginBottom = "5px";
            inputHpDp.style.marginBottom = "0";
            labelHospital.style.top = "45%";
            labelDepartment.style.top = "45%";
            checkHospitalDepartment = false;
        } else {
            msg.style.display = "none";
            msg.style.marginBottom = "0";
            hospital.style.marginBottom = "0";
            department.style.marginBottom = "0";
            inputHpDp.style.marginBottom = "15px";
            labelHospital.style.top = "50%";
            labelDepartment.style.top = "50%";
            checkHospitalDepartment = true;
        }
    }
});

async function edit(password, hospital, department, name, phone) {
    const response = await fetch("/edit", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            "password": password,
            "hospital": hospital,
            "department": department,
            "name": name,
            "phone": phone

        })
    });
    const json = await response.json();
    return json.isValid;
}