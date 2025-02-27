// 필수 라이브러리 import
import { init as coreInit, RenderingEngine, Enums} from "@cornerstonejs/core";
import { init as dicomImageLoaderInit } from '@cornerstonejs/dicom-image-loader';

window.onload = function (){
    initializeCornerstone();
}


async function initializeCornerstone(){
    await coreInit();
    await dicomImageLoaderInit();
    loadImages();
}
//
// // dicom-image-loader의 외부 의존성 설정 (공식 가이드)
// cornerstoneDICOMImageLoader.external.cornerstone = cornerstone;
// cornerstoneDICOMImageLoader.external.dicomParser = dicomParser;
//
// // dicom-image-loader 구성
// cornerstoneDICOMImageLoader.configure({
//     useWebWorkers: true,
//     webWorkerPath: '/node_modules/@cornerstonejs/core/dist/esm/webWorkerManager/webWorkerManager.js', // 실제 로컬 경로
//     taskConfiguration: {
//         decodeTask: {
//             codecsPath: '/node_modules/@cornerstonejs/core/dist/esm/loaders/imageLoader.js' // 실제 로컬 경로
//         }
//     }
// });

// Oracle DB에서 영상 목록을 가져와 영상을 로드하는 함수
function loadImages() {
    const urlDiv = window.location.pathname.split("/");
    const studykey = urlDiv[3];
    const serieskey = urlDiv[4];

    console.log("스터디 키 : ", studykey, "시리즈 키 :", serieskey)
    // 백엔드 REST API 호출 (/reports/checking/{studykey}/{serieskey})
    fetch(`/reports/checking/${studykey}/${serieskey}`)
        .then(response => response.json())
        .then(data => {
            console.log("서버에서 불러온 DICOM 이미지 목록 ", data)

            if (!Array.isArray(data) || data.length === 0) {
                console.error('해당 스터디의 시리즈에 영상이 없습니다.:', studykey, serieskey);
                document.getElementById("dicomContainer").innerHTML = "<p> 해당 스터디의 시리즈에 영상이 없습니다. </p>"
                return;
            }

            const dicomContainer = document.getElementById("dicomContainer");
            dicomContainer.innerHTML = "";

            data.forEach((images, index) => {
                const dicomImagePath = (images.path + images.name).replace(/\\/g, "/");
                console.log(`[${index + 1} 최종 요청할 DICOM 파일 PATH`, dicomImagePath);

                const element = document.createElement("div");
                element.style.width = '500px';
                element.style.height = '500px';
                element.id = `dicomViewport-${index}`;
                dicomContainer.appendChild(element);

                fetchDicomImageFileAndRendering(dicomImagePath, element.id);
            });
        })
        .catch(error => console.error ("이미지를 불러오는 중 오류 발생", error))
}

function fetchDicomImageFileAndRendering(dicomImagePath, viewportId) {
    fetch(`reports/getDicomImage?path=${encodeURIComponent(dicomImagePath)}`)
        .then(response => response.arrayBuffer())
        .then(arraybuffer => {
            if (arraybuffer.byteLength === 0){
                throw new Error ("서버에서 받아온 DICOM 파일이 비어있습니다.");
            }
            console.log (`[${viewportId}]`, arraybuffer.byteLength, `byte DICOM 파일을 불러왔습니다.`);

            // DICOM RENDERING
            renderDicomImage(arraybuffer, viewportId);
        })
        .catch(error => console.error("DICOM 파일을 불러오는 중 오류가 발생했습니다.", error));
}

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