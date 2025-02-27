import { init as coreInit, RenderingEngine, Enums } from '@cornerstonejs/core';
import { init as dicomImageLoaderInit } from '@cornerstonejs/dicom-image-loader';

let images = []; //이미지 정보 저장
let currentIndex = 0; //현재 이미지 인덱스
let imagesPerPage = 12; //한 페이지 갯수
let paginationSize = 5; //버튼 갯 수
let currentPage = 0;
let totalPages = 0;
let paginationStart = 0;

window.onload = function () {
    initializeCornerstone();
};

document.addEventListener("DOMContentLoaded", () => {
    loadPatientInfo();
});

async function initializeCornerstone() {
    await coreInit(); // cornerstone.js를 초기화
    await dicomImageLoaderInit(); // DICOM 이미지를 불러오기
    loadDicomImages(); // DICOM 이미지를 가져오기
}

async function loadPatientInfo() {
    try {
        const urlParts = window.location.pathname.split("/");

        const pid = urlParts[3];

        let response = await fetch(`/patientScan/action/${pid}`);
        let patientData = await response.json();

        if (!patientData || patientData.length === 0 || patientData[0].error) {
            console.error("환자 정보를 불러올 수 없습니다!");
            return;
        }

        const patient = patientData[0];
        document.getElementById("patientName").innerText = patient.pname || "N/A";
        document.getElementById("patientId").innerText = patient.pid || "N/A";
        document.getElementById("patientSex").innerText = patient.psex || "N/A";
        document.getElementById("patientBirth").innerText = patient.pbirthdate || "N/A";
    } catch (error) {
        console.error("환자 정보 불러오는 중 오류 발생:", error);
    }
}

document.addEventListener("DOMContentLoaded", () => {
    loadPatientInfo();
});

// DICOM 이미지 리스트 로드
async function loadDicomImages() {
    try {
        const urlParts = window.location.pathname.split("/");
        const studyKey = urlParts[4];
        const seriesKey = urlParts[5];

        // API 요청해서 JSON 데이터 저장
        let response = await fetch(`/patientScan/action/images/${studyKey}/${seriesKey}`);
        let imagesData = await response.json();

        // 예외
        if (!imagesData || imagesData.length === 0) {
            console.error("불러올 이미지가 없습니다!");
            return;
        }

        // 이미지 파일 경로, 파일 명만 저장
        images = imagesData.map(img => ({
            path: img.path,
            fname: img.fname
        }));

        // 퍼레이션 세팅
        totalPages = Math.ceil(images.length / imagesPerPage);
        updateThumbnailList(); // 시리즈 목록 업데이트
        updatePaginationControls(); //UI 업데이트
        displayImage(0); // 첫번쨰 사진 표시
    } catch (error) {
        console.error("DICOM 이미지 불러오는 중 오류 발생:", error);
    }
}

// cornerstone.js로 이미지 렌더링
function renderImage(arrayBuffer, viewportId) {
    const renderingEngine = new RenderingEngine(`renderingEngine-${viewportId}`);
    const viewportInput = {
        viewportId,
        element: document.getElementById(viewportId),
        type: Enums.ViewportType.STACK,
    };

    renderingEngine.enableElement(viewportInput);

    // cornerstone.js에서 DICOM 이미지 표시
    const imageId = "dicomweb:" + URL.createObjectURL(new Blob([arrayBuffer], { type: 'application/dicom' }));
    const viewport = renderingEngine.getViewport(viewportId);

    viewport.setStack([imageId], 0);
    viewport.render();
}

// 이미지 출력 함수
function displayImage(index) {
    if (index < 0 || index >= images.length) {
        return;
    }

    currentIndex = index;

    // 현재 보고 있는 이미지가 속한 페이지를 자동 변경
    let pageIndex = Math.floor(currentIndex / imagesPerPage);

    if (pageIndex !== currentPage) {
        currentPage = pageIndex;
        paginationStart = Math.floor(currentPage / paginationSize) * paginationSize;
        updateThumbnailList();
        updatePaginationControls();
    }

    // 선택한 이미지 가져와서 렌더링 요청
    updateActiveThumbnail();
    const image = images[index];
    const dicomFilePath = image.path + image.fname;
    fetchDicomFileAndRender(dicomFilePath, "dicomViewport");
}

// DICOM 파일 서버에서 가져오기(Z 드라이버에서 가져옴)
function fetchDicomFileAndRender(dicomFilePath, viewportId) {
    fetch(`/patientScan/action/getDicomFile?path=${encodeURIComponent(dicomFilePath)}`)
        .then(response => response.arrayBuffer())
        .then(arrayBuffer => renderImage(arrayBuffer, viewportId))
        .catch(error => console.error("DICOM 파일 로딩 중 오류 발생:", error));
}

document.addEventListener("DOMContentLoaded", () => {
    initializeCornerstone();
});

// 썸네일 리스트 업데이트 시 마우스 이벤트 추가
function updateThumbnailList() {
    const thumbnailContainer = document.getElementById("thumbnailContainer");
    thumbnailContainer.innerHTML = "";
    const start = currentPage * imagesPerPage;
    const end = Math.min(start + imagesPerPage, images.length);

    // 썸네일 생성
    for (let i = start; i < end; i++) {
        let image = images[i];
        let thumbnail = document.createElement("div");

        thumbnail.classList.add("thumbnail");
        thumbnail.dataset.index = i;

        // 현재 썸네일 강조
        if (i === currentIndex) {
            thumbnail.classList.add("active");
        }

        let viewport = document.createElement("div");
        viewport.classList.add("thumbnail-viewport");
        viewport.id = `thumbnail-${i}`;
        thumbnail.appendChild(viewport);

        // DICOM 이미지 불러와 썸네일에 표시
        fetchDicomFileAndRender(image.path + image.fname, viewport.id);

        // 클릭 & 마우스 이벤트 추가
        thumbnail.onclick = () => displayImage(i);
        thumbnail.onmouseover = () => thumbnail.classList.add("hover");
        thumbnail.onmouseout = () => thumbnail.classList.remove("hover");

        thumbnailContainer.appendChild(thumbnail);
    }

    updateActiveThumbnail();
}

// 현재 선택된 썸네일 강조
function updateActiveThumbnail() {
    document.querySelectorAll(".thumbnail").forEach(thumbnail => {
        let index = parseInt(thumbnail.dataset.index);

        if (index === currentIndex) {
            thumbnail.classList.add("active");
        } else {
            thumbnail.classList.remove("active");
        }
    });
}

// 페이지네이션 업데이트
function updatePaginationControls() {
    const pageButtons = document.getElementById("pageButtons");
    pageButtons.innerHTML = "";

    for (let i = paginationStart; i < Math.min(paginationStart + paginationSize, totalPages); i++) {
        let button = document.createElement("button");

        button.innerText = i + 1;
        button.classList.add("pagination-btn");

        if (i === currentPage) {
            button.classList.add("active");
        }

        // 버튼 클릭시 이동
        button.onclick = function () {
            currentPage = i;
            updateThumbnailList();
            updatePaginationControls();
        };
        pageButtons.appendChild(button);
    }

    // 이전/다음 버튼 활성화 여부 설정
    document.getElementById("prevPageBtn").disabled = paginationStart === 0;
    document.getElementById("nextPageBtn").disabled = paginationStart + paginationSize >= totalPages;
}

// 이전 페이지 버튼 이벤트
document.getElementById("prevPageBtn").onclick = () => {
    paginationStart = Math.max(0, paginationStart - paginationSize);
    updatePaginationControls();
};

// 다음 페이지 버튼 이벤트
document.getElementById("nextPageBtn").onclick = () => {
    paginationStart = Math.min(totalPages - paginationSize, paginationStart + paginationSize);
    updatePaginationControls();
};

// 마우스 휠 이벤트 추가
document.getElementById("viewerContainer").addEventListener("wheel", (event) => {
    event.preventDefault();
    if (event.deltaY > 0 && currentIndex < images.length - 1) {
        displayImage(currentIndex + 1);
    } else if (event.deltaY < 0 && currentIndex > 0) {
        displayImage(currentIndex - 1);
    }
});
