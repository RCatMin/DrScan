import { init as coreInit, RenderingEngine, Enums } from '@cornerstonejs/core';
import { init as dicomImageLoaderInit } from '@cornerstonejs/dicom-image-loader';
import {
    addTool,
    ZoomTool,
    PanTool,
    ToolGroupManager,
    StackScrollTool,
    LengthTool,
    AngleTool,
    BidirectionalTool,
    ProbeTool
    } from '@cornerstonejs/tools';

let image = [];
let currentIndex = 0; //현재 이미지 인덱스
let imagesPerPage = 12; //한 페이지 갯수
let paginationSize = 5; //버튼 갯 수
let currentPage = 0;
let totalPages = 0;
let paginationStart = 0;

window.onload = function () {
    initializeCornerstone();
    addTools();
    setupSelectToolGroups();
};

document.addEventListener("DOMContentLoaded", () => {
    loadPatientInfo();
});

// Cornerstone.js 초기화
async function initializeCornerstone() {
    await coreInit(); // cornerstone.js 초기화
    await dicomImageLoaderInit(); // DICOM Image Loader 불러오기

    // 영상 로딩 및 렌더링 함수 호출
    loadImages();
}

// 환자 정보 불러오기
async function loadPatientInfo(){
    try {
        const urlDiv = window.location.pathname.split("/");
        const pid = urlDiv[3];

        console.log("환자 ID : ", pid)

        // 백엔드 REST API 호출 (/reports/{pid})
        let response = await fetch(`/reports/${pid}`);
        let patientData = await response.json();

        // 환자 정보가 없을 경우 예외처리
        if (!patientData || patientData.length === 0) {
            console.error("불러올 환자정보가 존재하지 않습니다.");
            return;
        }

        const patient = patientData[0];
        document.getElementById("patientName").innerText = patient.pname || "불명";
        document.getElementById("patientId").innerText = patient.pid || "불명";
        document.getElementById("patientSex").innerText = patient.psex || "불명";
        document.getElementById("patientBirth").innerText = patient.pbirthdate || "불명";

    } catch (error) {
        console.error("환자 정보 불러오기 실패", error);
    }
}

function addTools(){
    // 도구 등록
    addTool(PanTool);
    addTool(ZoomTool);
    addTool(StackScrollTool);
    addTool(LengthTool);
    addTool(AngleTool);
    addTool(BidirectionalTool);
    addTool(ProbeTool);
}

// 기능 활성화
function setupSelectToolGroups(){
    const toolGroupId = 'ctToolGroup';
    const ctToolGroup = ToolGroupManager.createToolGroup(toolGroupId);

    // 영상 탐색 도구
    ctToolGroup.addTool(PanTool.toolName); // 영상을 상하좌우로 이동시키는 도구
    ctToolGroup.addTool(ZoomTool.toolName); // 영상 확대 및 축소 도구
    ctToolGroup.addTool(StackScrollTool.toolName); // 마우스 휠로 영상 움직임

    // 기본 활성화
    ctToolGroup.setToolActive(StackScrollTool.toolName, {
        bindings: []
    });

    const zoomBtn = document.getElementById("zoomBtn");
    let zoomActive = false;
    zoomBtn.addEventListener('click', () => {
        if (!zoomActive) {
            ctToolGroup.setToolActive(ZoomTool.toolName, { bindings: [{ mouseButton: 1 }] });
            zoomActive = true;
            console.log("Zoom 도구 활성화됨");
        } else {
            ctToolGroup.setToolDisabled(ZoomTool.toolName);
            zoomActive = false;
            console.log("Zoom 도구 비활성화됨");
        }
    });

    document.getElementById("panBtn").addEventListener('click', () => {
        ctToolGroup.setToolActive(PanTool.toolName, {bindings : []});
        console.log("화면 이동 활성화됨");
    });

    // document.getElementById("stackScrollBtn").addEventListener('click', () => {
    //     ctToolGroup.setToolActive(StackScrollTool.toolName, { bindings: [] });
    //     console.log("StackScrollTool 활성화됨");
    // });

    // 측정 도구
    ctToolGroup.addTool(LengthTool.toolName); // 두 점 사이의 거리를 측정
    ctToolGroup.addTool(AngleTool.toolName); // 각도 측정
    ctToolGroup.addTool(BidirectionalTool.toolName); // 두 방향의 선을 이용해 거리를 측정
    ctToolGroup.addTool(ProbeTool.toolName);

    document.getElementById("calLengthBtn").addEventListener('click', () => {
        ctToolGroup.setToolActive(LengthTool.toolName, {bindings : []});
        console.log("거리 측정 도구 활성화");
    });

    document.getElementById("calAngleBtn").addEventListener('click', () => {
        ctToolGroup.setToolActive(AngleTool.toolName, {bindings : []});
        console.log("각도 측정 도구 활성화");
    });

    document.getElementById("calBidirectionalBtn").addEventListener('click', () => {
        ctToolGroup.setToolActive(BidirectionalTool.toolName, {bindings : []});
        console.log("양방향 거리 측정 도구 활성화");
    });

    document.getElementById ("probeBtn").addEventListener('click', () => {
        ctToolGroup.setToolActive(ProbeTool.toolName, {bindings : []});
        console.log("Probe 도구 활성화");
    });
}

// Oracle DB에서 영상 목록을 가져와 영상을 로드하는 함수
async function loadImages() {
    try {
        const urlDiv = window.location.pathname.split("/");
        const studykey = urlDiv[4];
        const serieskey = urlDiv[5];

        console.log("스터디 키 : ", studykey, "시리즈 키 :", serieskey)

        // 백엔드 REST API 호출 (/reports/checking/{studykey}/{serieskey})
        let response = await fetch(`/reports/checking/${studykey}/${serieskey}`);
        let imageData = await response.json();

        // 이미지가 없을 경우 예외처리
        if (!imageData || imageData.length === 0) {
            console.error("불러올 이미지가 존재하지 않습니다.");
            return;
        }

        image = imageData.map(img => ({
            path: img.path,
            fname: img.fname
        }));

        totalPages = Math.ceil(image.length / imagesPerPage);
        updateSeriesList();
        updatePaginationControls();
        printFirstImage(0);

    } catch (error) {
        console.error("DICOM 이미지 불러오기 실패", error);
    }
}

function fetchDicomImageFileAndRendering(dicomImagePath, viewportId) {
    fetch(`/reports/getDicomImage?path=${encodeURIComponent(dicomImagePath)}`)
        .then(response => response.arrayBuffer())
        .then(arraybuffer => {
            if (arraybuffer.byteLength === 0){
                console.log(`[${viewportId}]`);
                throw new Error ("서버에서 받아온 DICOM 파일이 비어있습니다.");
            }
            console.log (`[${viewportId}]`, arraybuffer.byteLength, `byte DICOM 파일을 불러왔습니다.`);

            // DICOM RENDERING
            renderDicomImage(arraybuffer, viewportId);
        })
        .catch(error => console.error("DICOM 파일을 불러오는 중 오류가 발생했습니다.", error));
}

// function fetchDicomFileAndRender(dicomFilePath, viewportId) {
//     fetch(`/reports/getDicomFile?path=${encodeURIComponent(dicomFilePath)}`)
//         .then(response => response.arrayBuffer())
//         .then(arrayBuffer => renderImage(arrayBuffer, viewportId))
//         .catch(error => console.error("DICOM 파일 로딩 중 오류 발생:", error));
// }

// 이미지 렌더링
function renderDicomImage(arraybuffer, viewportId) {
    const renderingEngineId = `renderEngine-${viewportId}`;
    const renderingEngine = new RenderingEngine(renderingEngineId);

    const viewportInput = {
        viewportId,
        element : document.getElementById(viewportId),
        type: Enums.ViewportType.STACK,
    };

    renderingEngine.enableElement(viewportInput);

    // ImageId를 사용하여 랜더링 실행
    const imageId = "dicomweb:" + URL.createObjectURL(new Blob([arraybuffer], {
        type: 'application/dicom'
    }));

    const viewport = renderingEngine.getViewport(viewportId);
    viewport.setStack([imageId], 0);
    viewport.render();
}

function printFirstImage (index){
    if (index < 0 || index >= image.length){
        console.error("잘못된 접근입니다.");
        return;
    }

    currentIndex = index;

    // 현재 출력되고 있는 이미지가 있는 페이지를 자동 변경
    let pageIdx = Math.floor(currentIndex / imagesPerPage);

    if (pageIdx !== currentPage){
        currentPage = pageIdx;
        paginationStart = Math.floor(currentPage / paginationSize) * paginationSize;
        updateSeriesList();
        updatePaginationControls();
    }

    // 선택한 이미지를 가져와서 렌더링
    updateActivePreview();
    const img = image[index];
    const dicomImagePath = img.path + img.fname;
    fetchDicomImageFileAndRendering(dicomImagePath, "dicomViewport");
}

// 선택된 미리보기 강조
function updateActivePreview() {
    document.querySelectorAll(".preview").forEach(preview => {
        let index = parseInt(preview.dataset.index);

        if (index === currentIndex){
            preview.classList.add("active");
        } else {
            preview.classList.remove("active");
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
            updateSeriesList();
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

// 시리즈 목록 내 미리보기 업데이트
function updateSeriesList (){
    const previewImageContainer = document.getElementById("previewImageContainer");
    previewImageContainer.innerHTML = "";

    const start = currentPage * imagesPerPage;
    const end = Math.min(start + imagesPerPage, image.length);

    // 미리보기 생성
    for (let i = start; i < end; i++){
        let img = image[i];
        let preview = document.createElement("div");

        preview.classList.add("preview");
        preview.dataset.index = i;

        // 미리보기 강조
        if (i === currentIndex){
            preview.classList.add("active");
        }

        let viewport = document.createElement("div");
        viewport.classList.add("preview-viewport");
        viewport.id = `preview-${i}`;
        preview.appendChild(viewport);

        fetchDicomImageFileAndRendering(img.path + img.fname, viewport.id);
    }
}