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
        button.removeEventListener('click', dropdownClickHandler); // 기존 이벤트 제거
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