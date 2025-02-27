import { init as coreInit, RenderingEngine, Enums } from '@cornerstonejs/core';
import { init as dicomImageLoaderInit } from '@cornerstonejs/dicom-image-loader';
import { ZoomTool, PanTool, ToolGroupManager, StackScrollTool, LengthTool, AngleTool, BidirectionalTool, ProbeTool} from '@cornerstonejs/tools';

window.onload = function () {
    initializeCornerstone();
    setupSelectToolGroups();
};

// Cornerstone.js 초기화
async function initializeCornerstone() {
    await coreInit();
    await dicomImageLoaderInit();

    // 영상 로딩 및 렌더링 함수 호출
    loadDicomImages();
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

    document.getElementById("zoomBtn").addEventListener('click', () => {
        ctToolGroup.setToolActive(ZoomTool.toolName, {bindings : []});
        console.log("줌 도구 활성화됨");
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
}

// 줌 기능
// let zoomActive = false;
// function setupZoomButton() {
//     const zoomBtn = document.getElementById("zoomBtn");
//
//     zoomBtn.addEventListener('click', () => {
//         if (!zoomActive){
//             setToolActive('ZoomTool', {mouseButtonMask : 1});
//             console.log ("Zoom 툴 활성화");
//         } else {
//             zoomActive = false;
//             console.log ("Zoom 툴 비활성화)")
//         }
//
//     });
// }

// 서버에서 받은 DICOM 데이터를 Cornerstone.js로 렌더링
function loadDicomImages() {
    const urlParts = window.location.pathname.split("/");
    const studyKey = urlParts[4];
    const seriesKey = urlParts[5];

    console.log("studyKey:", studyKey, "seriesKey:", seriesKey);

    fetch(`/patientScan/action/images/${studyKey}/${seriesKey}`)
        .then(response => response.json())
        .then(images => {
            console.log("서버에서 받은 DICOM 이미지 리스트:", images);

            if (!Array.isArray(images) || images.length === 0) {
                console.error("해당 시리즈에 대한 DICOM 이미지가 없습니다.");
                document.getElementById("dicomContainer").innerHTML = "<p>해당 시리즈에 대한 DICOM 이미지가 없습니다.</p>";
                return;
            }

            const dicomContainer = document.getElementById("dicomContainer");
            dicomContainer.innerHTML = ""; // 기존 이미지 제거하고 새로 추가

            images.forEach((image, index) => {
                // DICOM 파일 경로 생성 (백슬래시 → 슬래시 변환)
                const dicomFilePath = (image.path + image.fname).replace(/\\/g, "/");
                console.log(`🔹 [${index + 1}] 최종 요청할 DICOM 파일 경로:`, dicomFilePath);

                // 여러 개의 뷰포트 생성 (각 이미지마다 개별 뷰포트)
                const element = document.createElement("div");
                element.style.width = "512px";
                element.style.height = "512px";
                element.style.border = "1px solid #ccc";
                element.id = `dicomViewport-${index}`; // 각 이미지마다 다른 ID 지정
                dicomContainer.appendChild(element);

                // DICOM 이미지 로드 및 렌더링
                fetchDicomFileAndRender(dicomFilePath, element.id);
            });
        })
        .catch(error => console.error("이미지 로드 중 에러 발생:", error));
}

// DICOM 파일을 가져와 Cornerstone.js로 렌더링
function fetchDicomFileAndRender(dicomFilePath, viewportId) {
    fetch(`/patientScan/action/getDicomFile?path=${encodeURIComponent(dicomFilePath)}`)
        .then(response => response.arrayBuffer())
        .then(arrayBuffer => {
            if (arrayBuffer.byteLength === 0) {
                throw new Error("서버에서 받은 DICOM 파일이 비어 있음!");
            }
            console.log(`[${viewportId}] DICOM 파일 로드 성공! 크기:`, arrayBuffer.byteLength);

            // DICOM 렌더링
            renderImage(arrayBuffer, viewportId);
        })
        .catch(error => console.error("DICOM 파일 로딩 중 오류 발생:", error));
}

// Cornerstone.js 기반 DICOM 렌더링 함수 (뷰포트 ID를 다르게 적용)
function renderImage(arrayBuffer, viewportId) {
    const renderingEngineId = `renderingEngine-${viewportId}`; // 각 뷰포트마다 다른 ID 사용
    const renderingEngine = new RenderingEngine(renderingEngineId);

    const viewportInput = {
        viewportId,
        element: document.getElementById(viewportId),
        type: Enums.ViewportType.STACK,
    };

    renderingEngine.enableElement(viewportInput);

    const imageId = "dicomweb:" + URL.createObjectURL(new Blob([arrayBuffer], {
        type: 'application/dicom'
    }));

    const viewport = renderingEngine.getViewport(viewportId);
    viewport.setStack([imageId], 0);
    viewport.render();
}
