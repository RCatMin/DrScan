document.addEventListener('DOMContentLoaded', async function () {
    await createSession();
    initializeDropdowns();

    const showDropdownButton = document.getElementById('showDropdown');
    if (showDropdownButton) {
        showDropdownButton.addEventListener('click', function () {
            toggleDropdown(this);
        });
    }

    const tabs = document.querySelectorAll('.tab');
    tabs.forEach((tab, index) => {
        tab.addEventListener('click', () => openTab(index));
    });

    const forms = document.querySelectorAll("form[data-id]");
    forms.forEach(form => {
        form.addEventListener("submit", async event => {
            event.preventDefault();

            const index = form.getAttribute('data-id');
            const activeTab = document.querySelector(".tab-content.active").id;

            if (activeTab === "tab1") {
                const code = document.getElementById(`userCode-tab1-${index}`).value;
                const username = document.getElementById(`username-tab1-${index}`).value;
                const email = document.getElementById(`email-tab1-${index}`).value;
                const status = document.getElementById(`status-tab1-${index}`).value;
                const accountType = document.getElementById(`accountType-tab1-${index}`).value;

                await requestEdit(code, username, email, status, accountType);
            } else if (activeTab === "tab2") {
                const code = document.getElementById(`userCode-tab2-${index}`).value;

                await requestTemporaryApprove(code);
            }
        });
    });

});

function openTab(tabIndex) {
    const tabs = document.querySelectorAll('.tab');
    const tabContents = document.querySelectorAll('.tab-content');

    tabs.forEach((tab, index) => {
        tab.classList.remove('active');
        tabContents[index].classList.remove('active');
    });

    tabs[tabIndex].classList.add('active');
    tabContents[tabIndex].classList.add('active');

    initializeDropdowns();
}

function initializeDropdowns() {
    const buttons = document.querySelectorAll('.showDropdown');

    buttons.forEach(button => {
        button.removeEventListener('click', dropdownClickHandler);
        button.addEventListener('click', dropdownClickHandler);
    });
}

function dropdownClickHandler(event) {
    toggleDropdown(event.target);
}

function toggleDropdown(button) {
    const index = button.getAttribute('data-id');
    const dropdown = document.getElementById(`dropdownMenu-${index}`);

    if (dropdown) {
        dropdown.style.display = dropdown.style.display === 'block' ? 'none' : 'block';
    }
}

async function createSession() {
    try {
        const response = await fetch('/admin/session', { method: 'GET' });
        const json = await response.json();
        return json.isValid;
    } catch (error) {
        console.error('세션 생성 실패:', error);
        return false;
    }
}

async function requestEdit(code, username, email, status, accountType) {
    const response = await fetch("/admin/edit", {
        method: "PUT",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            "code": code,
            "username" : username,
            "email": email,
            "status": status,
            "accountType": accountType
        })
    });
    if (response.ok) {
        alert("사용자 수정 완료!");
        await createSession();
        window.location.reload()
        return true;
    } else {
        const json = await response.json();
        alert(`오류 : ${json.message}`);
        return json.isValid;
    }
}

async function requestTemporaryApprove(code) {
    const response = await fetch("/admin/approve", {
        method: "PUT",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            "code": code,
        })
    });
    if (response.ok) {
        alert("가입 승인 완료!");
        await createSession();
        window.location.reload()
        return true;
    } else {
        const json = await response.json();
        alert(`오류 : ${json.message}`);
        return json.isValid;
    }
}