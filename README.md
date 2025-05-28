
<div align="center">

![Image](https://github.com/user-attachments/assets/e54c5e95-bce4-4733-a294-0acaf8bf1e90)

### 👨🏻‍⚕️닥터스캔 (DICOM 활용 의료 영상 서비스) 

### 의료영상 표준 웹 뷰어 프로젝트 🖍️
![프로젝트 기간](https://img.shields.io/badge/프로젝트_기간-2025.02.01~2025.03.14-fab2ac?style=for-the-badge&logo=&logoColor=white&color=dc143c)
<br/>
![프로젝트 기획 및 DB 모델링](https://img.shields.io/badge/프로젝트_기획_및_DB_모델링-2025.02.01~2025.02.04-fab2ac?style=for-the-badge&logo=&logoColor=white&color=skyblue)
<br/>
![서비스 개발](https://img.shields.io/badge/서비스_개발-2025.02.04~2025.03.04-fab2ac?style=for-the-badge&logo=&logoColor=white&color=skyblue)
<br/>
![피드백 수정 및 배포](https://img.shields.io/badge/피드백_수정_및_배포-2025.03.05~2025.03.14-fab2ac?style=for-the-badge&logo=&logoColor=white&color=skyblue)
<br/>
![사용 언어 수](https://img.shields.io/github/languages/count/RCatMin/DrScan?style=for-the-badge&color=green)
![커밋수](https://img.shields.io/github/commit-activity/t/RCatMin/DrScan?style=for-the-badge&color=green)
![릴리즈](https://img.shields.io/badge/Releases-v1.0.0-1?style=for-the-badge&logo=google-chrome&color=green)
<br/>
![Node.js](https://img.shields.io/badge/Node.js-white?logo=nodedotjs&style=for-the-badge&color=edacb1)
![Figma](https://img.shields.io/badge/Figma-white?logo=figma&style=for-the-badge&color=edacb1)
![JSP/JSTL](https://img.shields.io/badge/JSP/JSTL-white?style=for-the-badge&color=edacb1)
![HTML5](https://img.shields.io/badge/HTML5-white?logo=html5&style=for-the-badge&color=edacb1)
![CSS](https://img.shields.io/badge/CSS-white?logo=css3&style=for-the-badge&color=edacb1)
![JavaScript](https://img.shields.io/badge/JavaScript-white?logo=javascript&style=for-the-badge&color=edacb1)
![npm](https://img.shields.io/badge/npm-white?logo=npm&style=for-the-badge&color=edacb1)
![Webpack](https://img.shields.io/badge/Webpack-white?logo=webpack&style=for-the-badge&color=edacb1)
![Cornerstone JS](https://img.shields.io/badge/Cornerstone_JS-white?style=for-the-badge&color=edacb1)

![Java 17](https://img.shields.io/badge/Java_17-white?style=for-the-badge&color=edacb1)
![Spring](https://img.shields.io/badge/Spring-white?logo=spring&style=for-the-badge&color=edacb1)
![SpringBoot](https://img.shields.io/badge/SpringBoot-white?logo=springboot&style=for-the-badge&color=edacb1)
![dcm4che](https://img.shields.io/badge/dcm4che-white?style=for-the-badge&color=edacb1)
![jcifs](https://img.shields.io/badge/jcifs-white?style=for-the-badge&color=edacb1)

![MySQL](https://img.shields.io/badge/MySQL-white?logo=mysql&style=for-the-badge&color=edacb1)
![AWS RDS](https://img.shields.io/badge/AWS_RDS-white?logo=amazonrds&style=for-the-badge&color=edacb1)
![Oracle DB](https://img.shields.io/badge/Oracle_DB-white?style=for-the-badge&color=edacb1)

![Intellij IDEA](https://img.shields.io/badge/Intellij_IDEA-white?logo=intellijidea&style=for-the-badge&color=edacb1)
![Git](https://img.shields.io/badge/Git-white?logo=git&style=for-the-badge&color=edacb1)
![GitHub](https://img.shields.io/badge/GitHub-white?logo=github&style=for-the-badge&color=edacb1)

</div> 


### 프로젝트 소개
- 닥터스캔은 웹 기반으로 의료기관이 자체 환경과 필요에 맞게 손쉽게 통합 및 커스터마이징 가능한 웹 DICOM Viewer입니다.
- DICOM 의료 영상의 실시간 렌더링과 판독 보고서 작성 및 관리가 가능합니다!
- 의료 영상 판독 및 데이터 관리를 효율화하고 사용자 친화적 UI로 의료 업무 환경 개선을 목표로 했습니다!


>#### 🚨 빌드 시 주의
>DrScan 프로젝트는 **제공된 Oracle DB와 네트워크 Storage(NAS) 주소**를 기반으로 설정 및 빌드가 진행됩니다.</br>
> 아래와 같은 상황에서 빌드에 어려움이 발생할 수 있으니, 실행 전 다음 사항을 반드시 확인해주시기 바랍니다.:
>- DrScan 프로젝트는 Oracle DB에 접근하여 데이터를 처리합니다.<br/>
   >  제공된 DB 주소, 사용자 계정, 비밀번호 등이 누락되거나 잘못된 경우 실행이 불가능합니다.
>- 의료 영상 데이터(DICOM 파일)는 네트워크 스토리지에 저장되어 있으며,<br/>
   >  해당 주소가 유효하지 않거나 접근 권한이 없는 경우 영상 렌더링 및 조회 기능이 동작하지 않습니다.

<br/>



### 1. 팀원 구성 및 역할 분담

#### 🐉 이민수 (팀장)
 - 기능 : 의료 영상 렌더링 및 조작
	- 해당 환자 DICOM 영상 렌더링
	- 영상 확대 기능
   	- 영상 이동 기능
	- 영상 음영 처리
	- 영상 좌우 반전 및 기울기 기능
	- 선택 후 주석 삽입 기능
	- 특정 부위 길이 측정 및 그리기 기능

#### 🐇 박현균 (서기)
 - 기능 : 판독 및 조회
	- 환자,  스터디, 시리즈, 이미지, 판독 정보 조회
	- 정렬 기능
   	- 세부 목록 표시
	- 판독 내용 입력, 저장, 수정, 삭제
	- 판독 자동 저장
	- 판독 등급 분류
	- 판독 상태 관리
   	- DICOM 영상 렌더링

#### 🐅 방서준 (팀원)
 - 기능 : 환자 목록 조회 및 진료 기록 관리
   - 환자 목록 조회
   - 환자의 진료 기록 조회
   - 진료 기록 상세 조회
   - 진료 기록 등록
   - 진료 기록 수정
   - 진료 기록 삭제

#### 🐔 김현창 (팀원)
 - 기능 : 보안 및 관리
	- 개인정보보호법 준수
 	- 필터
  	- 비정상적인 로그인 시 계정정지
   	- 캡쳐방지
   	- 로그 저장 및 출력 		 

#### 🐍 이준호 (팀원)



### 2. 개발환경

### Front-end
<div>
<img src="https://github.com/user-attachments/assets/5caa5b35-314b-4d44-82c9-b9bc1b0d27cd" width="80">
<img src="https://github.com/user-attachments/assets/87ae75e5-bbf7-48e8-98ad-04da6e708a89" width="80">
<img src="https://github.com/user-attachments/assets/4a0f33c1-37d7-4b2b-9454-d13ec723fc72" width="80">
<img src="https://github.com/user-attachments/assets/76af8467-0cc6-4ab6-af5e-d995817b25d5" width="80">
<img src="https://github.com/user-attachments/assets/185c428c-2206-4014-944c-b6bb1f0c5c18" width="80">
<img src="https://github.com/user-attachments/assets/b0058543-d60d-45e8-a9eb-a5873c558b91" width="80">
<img src="https://github.com/user-attachments/assets/c082ba58-1f43-4f5f-abd6-dc9cb6208d8f" width="80">
<img src="https://github.com/user-attachments/assets/bf5cbb32-920c-4ff3-b213-4bb0112170b8" width="80">
<img src="https://github.com/user-attachments/assets/e90e70cc-fb4d-43f8-b4d0-e8f8813ba6f0" width="80">
</div>

### Back-end
<div>
<img src="https://github.com/user-attachments/assets/aed9f647-486a-4bde-b296-680a5c48937c" width="80">
<img src="https://github.com/user-attachments/assets/6fa87a9c-a159-4e25-815a-281d9969202b" width="80">
<img src="https://github.com/user-attachments/assets/66e05d43-7247-42aa-b6f7-a1fccd9e39a6" width="80">
<img src="https://github.com/user-attachments/assets/bc65ad72-8ddf-4145-802c-e1151cb482ea" width="80">
<img src="https://github.com/user-attachments/assets/92664f96-2f46-4402-b777-872356e041e4" width="80">
<img src="https://github.com/user-attachments/assets/a6942a4d-1daf-4da1-bff7-8b9a1fa45d56" width="80">
<img src="https://github.com/user-attachments/assets/574dfe5f-1db9-44ff-91ba-f2ebd4d8fdb8" width="80">
</div>

### Database
<div>
<img src="https://github.com/user-attachments/assets/a4f7d7d0-cf9f-434e-97ff-d2b9a020eb97" width="80">
<img src="https://github.com/user-attachments/assets/ea902b55-c6a2-4678-b9c5-e5295bc5aa3f" width="80">
<img src="https://github.com/user-attachments/assets/c544939e-38dd-4820-8b5c-4f7a6843ceab" width="80">
</div>

### Tool
<div>
<img src="https://github.com/user-attachments/assets/4b7bbf9a-6ab5-4756-95d9-a14300c465be" width="80">
<img src="https://github.com/user-attachments/assets/b5d225af-1363-427b-bec2-799da1535c29" width="80">
<img src="https://github.com/user-attachments/assets/4463e299-8d6a-40a8-8d91-263509d9feba" width="80">
</div>

<br />

### 3. 개발 기간 및 작업 관리
![프로젝트 기간](https://img.shields.io/badge/프로젝트_기간-2025.02.01~2025.03.14-fab2ac?style=for-the-badge&logo=&logoColor=white&color=skyblue)
<br/>
![프로젝트 기획 및 DB 모델링](https://img.shields.io/badge/프로젝트_기획_및_DB_모델링-2025.02.01~2025.02.04-fab2ac?style=for-the-badge&logo=&logoColor=white&color=skyblue)
<br/>
![서비스 개발](https://img.shields.io/badge/서비스_개발-2025.02.04~2025.03.04-fab2ac?style=for-the-badge&logo=&logoColor=white&color=skyblue)
<br/>
![피드백 수정 및 배포](https://img.shields.io/badge/피드백_수정_및_배포-2025.03.05~2025.03.14-fab2ac?style=for-the-badge&logo=&logoColor=white&color=skyblue)
<br/>


### 4. 문서
- [프로젝트 기획서](https://drive.google.com/file/d/1BAfhfP24IKO7qfxHzUTf0FfOh66tursc/view?usp=drive_link)
- [요구사항 명세서](https://docs.google.com/spreadsheets/d/1IQUEEyGvU-f12gtRoEWC9gzALvhmW6Rt/edit?usp=drive_link&ouid=116757251093669453414&rtpof=true&sd=true)
- [화면 설계서](https://docs.google.com/presentation/d/1gT2KXG8XfJNKqk7hueVldkOYEA2sdGOc/edit?usp=drive_link&ouid=116757251093669453414&rtpof=true&sd=true)
- [테이블 정의서](https://docs.google.com/spreadsheets/d/1iomm7_cP0ewT_BbrJy3bBUIy0tGq9goJ/edit?usp=drive_link&ouid=116757251093669453414&rtpof=true&sd=true)
- [인터페이스 정의서](https://docs.google.com/spreadsheets/d/1QC7firwHMEuktxPQkzh02QiAH5vuFHVY/edit?usp=drive_link&ouid=116757251093669453414&rtpof=true&sd=true)
  

### [판독 및 조회]

#### [메인 (검색바)]
- **조회** | 검색창 아래쪽에 **모든 스터디 목록**이 테이블 형식으로 표시됩니다.
- **검색** | 검색은 **환자 이름, 환자 코드, 검사 날짜, 설명, 촬영 장비, 검사 번**호 총 6가지 항목 중 
             **원하는 항목**을 자유롭게 조합하여 사용할 수 있습니다! 

![메인 검색](https://github.com/user-attachments/assets/2cadc66a-3b8d-4cdf-9368-949a45d66c50)

#### [메인 (목록)]
- **정렬** | **테이블 제목**을 클릭하면 해당 목록에 맞게 **오름차순**으로 정렬합니다. 
               한 번더 클릭하면 **내림차순**으로 정렬합니다.
![메인 정렬](https://github.com/user-attachments/assets/341e463a-d9a5-411b-a91f-4563026d11a2)

#### [메인 (상세목록)]
- **상세목록** | **판독 내용있는 환자만 표시**가 됩니다.
                 판독 내용이 없는 경우엔 없음으로 표시됩니다.
- **중증도 레벨** |중증도 레벨은 **한국형 응급환자 분류 도구**를 참고하여 **버튼 색상**을 구분하였습니다.

![세부목록](https://github.com/user-attachments/assets/21b5f42c-bd1a-4ff5-86da-5a152330ae1d)

#### [DICOM Viewer (판독 저장)]
- **표시** | **환자, Study, Series** 정보가 **외부 데이터베이스**에서 가져와 표시했습니다.
- **저장** | **중증도 레벨, 보고서 상태, 판독내용**을 입력후 저장하면  **내부 데이터베이스**에 저장합니다.
- **자동저장** | 판독 중 입력된 데이터는 중간중간 **자동 저장**되어, 예상치 못한 데이터 손실을 예방할 수 있습니다.
- **렌더링** | Cornerstone.js (코너스톤)로 초기화 및 이미지 로드 -> WebGL을 활용하여 DICOM 파일을 표시
  		  -> DICOM 데이터를 Blob 형태로 변환 후, HTML 뷰포트에 표시
  
![판독저장](https://github.com/user-attachments/assets/50038eb1-5f78-4824-8e2d-3fcd362c077e)

#### [판독 기록(목록)]
- **정렬** | **테이블 제목**을 클릭하면 해당 목록에 맞게 **오름차순**으로 정렬합니다. 
               한 번더 클릭하면 **내림차순**으로 정렬합니다.
- **중증도 레벨** | 중증도 레벨에 따라 **색상이 다르게 표시되므로 직관적**으로 데이터를 확인할 수 있습니다.
- **상세보기** | 상세보기 버튼을 클릭하면 해당 판독의 **상세 정보 페이지**로 이동할 수 있습니다.
  
![판독기록 정렬](https://github.com/user-attachments/assets/749a13df-165d-40e3-a91b-ce5d2248882d)

#### [판독 상세(수정, 삭제)]
- **수정** | **환자 정보와 검사정보**는 **외부 데이터베이스**에서 가져온 정보이므로 수정이 불가능합니다.
             **중증도 레벨, 보고서 상태, 판독 내용**은 **내부 데이터베이스**에서 관리되며 수정 가능합니다.
- **삭제** | 삭제 버튼 누르면 **삭제 확인 메세지**가 나오는데 확인을 눌려야지 **삭제가 완료**됩니다.

![수정](https://github.com/user-attachments/assets/5a657212-a64b-497c-b616-171a52fa55f5)

![삭제](https://github.com/user-attachments/assets/b2574090-3054-40d4-bed1-2a108187a4a5)

### 환자 목록 조회 및 진료 기록 관리

#### [환자 목록 조회]
![Image](https://github.com/user-attachments/assets/6cf1be2a-8e95-451b-8ff6-c7f3d38e0121)
- 외부 데이터 베이스에 존재하는 환자정보를 GET요청을 통하여 요청 및 조회하여 출력합니다.
- 사용자에게 데이토를 효율적으로 제공하기 위해 페이징 처리를 하였습니다.

#### [진료 목록 조회]
![Image](https://github.com/user-attachments/assets/e52f5a68-0fbe-4f58-b8a2-915ad99a1a11)
- 환자 목록 페이지에서 한 환자를 선택 후 진료 보기 버튼을 누를 시 해당 환자의 진료 목록 페이지로 이동합니다. 

#### [진료 등록]
![Image](https://github.com/user-attachments/assets/afbea619-fa9a-4cf1-a043-80499836087d)
- 하단에 위치한 진료 등록 추가 버튼을 클릭하여 내용을 작성하고 진료를 추가합니다. 

#### [진료 수정]
![Image](https://github.com/user-attachments/assets/2142f108-88e0-408f-a998-79cb14b4f48e)
- 우측에 위치한 진료 상세보기 버튼을 눌러 진료 상세 페이지로 이동할 수 있습니다.
- 수정할 내용을 작성하고 하단에 위치한 수정 버튼을 클릭할 시 해당 진료 정보가 수정됩니다.

#### [진료 삭제]
![Image](https://github.com/user-attachments/assets/10c1d115-51b0-46f7-a5cf-54dd66419487)
- 상세 페이지에서 하단에 위치한 삭제 버튼을 클릭할 시에 해단 진료 정보를 삭제합니다.
- 삭제한 후에 해당 진료의 대상인 환자의 진료 목록페이지로 이동하게 됩니다.

#### [진료 등록 및 수정 시 유효성 검사]
![Image](https://github.com/user-attachments/assets/86970a61-27ab-40dc-bbab-4a3cfbf2bc0e)
- 진료 내용과 날짜가 비어있다면 "해당 내용을 작성해주세요"라는 오류 메세지를 출력합니다.
- 진료 날짜가 오늘의 날짜보다 미래일 경우 "유효하지 않은 날짜입니다."라는 오류 메세지를 출력합니다.


### 보안 및 관리

#### [비정상적인 로그인 시 계정정지]
![Image](https://github.com/user-attachments/assets/efd05a8b-9a4a-4267-9d3c-40c06ffd6ae1)
- 5회 이상 실패 시 계정정지
- 관리자가 해제 가능

#### [로그리스트]
![Image](https://github.com/user-attachments/assets/83c42de7-18b4-4823-bd55-3f3cba3d4ad1)
- 필요한 접근 기록을 저장
- 유저코드별로 검색 가능

### 6. 발표 영상
[<작품발표영상> 이민수 팀 - DrScan ](https://www.youtube.com/watch?v=XUc5bNPgREE)


### 7. 프로젝트 후기

### 🐉이민수
이번 프로젝트는 이전에 진행했던 프로젝트와 달리 규모가 커진 5명이 하나의 팀이 되어 **의료 영상 판독 및 관리 시스템 서비스**를 만들어 **개발자로서 첫 걸음을 시작하는 귀중한 시간**이 되었습니다.

백엔드에서는 **Spring MVC, RESTful API**를 중심으로 구조화 및 설계하고 **Spring Data JPA와 Hibernate**를 통하여 **Oracle DB, 직접 설계한 MySQL DB**를 동시 활용하여 환자의 기본 정보 및 검사 정보, 영상 데이터를 분리하였습니다.

프론트엔드에서는 **dcm4che**를 통해 **의료 영상 (DICOM 영상) 렌더링 기능을 구현**, **Cornerstone.js API**를 적극 도입하여 영상 판독에 필요한 **확대 및 이동, 음영 처리, 주석 삽입 및 그리기 기능 등**을 구현하였습니다.

프로젝트 간 처음 팀장을 담당하였습니다.

처음에 서로 의견이 맞지 않는 일도 있었지만 의견 조율을 중재하고 협업하는 분위기를 만든 결과 프로젝트를 잘 마무리 할 수 있었습니다.

많은 기술들을 써가며, 실제 의료 시스템에서 제공되는 서비스를 직접 만들어 본 경험과 팀장으로서 팀을 이끌어 간 경험은 앞으로 있을 개발자로서의 활동에 좋은 경험으로 남을 것 입니다.

본 프로젝트가 끝난 이후로도 실제 서비스를 만들며 어떻게 해야 더 좋은 서비스를 만들 수 있는지, **단순 기능을 구현하는 Coder가 아닌 Service를 제공하는 Developer**로 성장해 나가겠습니다.


### 🐇박현균
이번 프로젝트에서는 **Spring Boot 기반의 의료 영상 판독 및 관리 시스템**을 직접 설계하고 개발했습니다. 의료 데이터를 웹 환경에서 다루는 경험은 처음이었지만, 팀 프로젝트를 통해 하나의 시스템으로 완성해가는 과정이 무척 뜻깊었습니다.

백엔드에서는 **Spring MVC**와 **RESTful API**를 중심으로 구조화하고, **Spring Data JPA + Hibernate**를 통해 **Oracle**과 **MySQL**을 병행 사용해 검사 정보와 판독 데이터를 분리하여 관리했습니다. 검색 기능, 환자 조회, 판독 보고서 처리 등 데이터 흐름 전반을 설계하면서 실무와 가까운 경험을 할 수 있었습니다.

프론트엔드에서는 **Cornerstone.js**를 사용해 **DICOM 영상 렌더링 기능**을 구현하는 역할을 맡았습니다. 브라우저 상에서 실제 의료 영상을 **WebGL 기반으로 빠르게 렌더링**하고, 썸네일 생성, 페이지네이션 등도 함께 구성해 사용자 친화적인 이미지 뷰어를 만들 수 있었습니다.

또한, 프로젝트 후반부에는 **판독 보고서 실시간 자동 저장 기능과 최신 판독 데이터 불러오기 기능**을 구현해 사용자의 입력을 안정적으로 관리할 수 있도록 했습니다. 일정 주기로 판독 내용을 서버에 자동 저장하며, 이전에 저장된 내용을 다시 불러와 바로 이어서 작업할 수 있는 흐름도 직접 구성했습니다. 실제 병원 현장에서 편리하게 사용할 수 있도록 고민하며 설계한 기능이었습니다.

다만 아쉬운 점도 있었습니다. 영상 뷰어에서 확대, 이동, 거리 측정 등의 판독 툴 기능은 아직 완성하지 못했습니다. 툴 버튼과 일부 로직은 구현했지만 실제 기능이 작동하도록 연결하지 못한 점은 아쉽게 느껴집니다. 추후 여유가 생긴다면 개인적으로라도 해당 기능을 완성해보고 싶습니다.

그럼에도 불구하고 제가 맡은 **영상 시각화, 시리즈 검색기능, 그리고 자동 저장 및 최신 데이터 불러오기 로직**은 안정적으로 구현되었고, 팀원들과 협업하여 프로젝트 전체를 잘 마무리할 수 있었습니다. 다양한 기술을 접목하며 실제 의료 시스템과 유사한 환경을 구현한 이 경험은 개발자로서 한층 성장할 수 있었던 소중한 계기였습니다.
### 🐅방서준

### 🐔김현창
이번 프로젝트를 진행하면서 Spring Boot의 기본적인 기능들을 직접 적용해볼 수 있어 매우 유익한 경험이었습니다. REST API 설계, JPA를 활용한 데이터베이스 연동, 기본적인 예외 처리 등 주요 기능들을 직접 구현해보며 백엔드 개발에 대한 이해를 한층 더 넓힐 수 있었습니다.

하지만 프로젝트를 마친 후 돌아보니, Spring Boot가 제공하는 다양한 기능들 중 일부만 활용한 것이 아쉬움으로 남았습니다. 특히 Spring Security를 활용한 인증 및 권한 관리, AOP를 이용한 로깅 처리, 그리고 DevTools, Actuator 등을 통한 운영 환경 모니터링 등 보다 고급 기능들까지 다뤄봤다면 더 풍부한 프로젝트가 되었을 것이라 생각합니다.

앞으로는 이러한 Spring Boot의 다양한 기능들을 능동적으로 학습하고, 실제 서비스에 어떻게 효율적으로 적용할 수 있을지를 고민하며 프로젝트에 녹여내고자 합니다. 단순히 기능을 구현하는 것에 그치지 않고, 스프링 생태계 전반을 이해하고 활용할 수 있는 개발자로 성장하고 싶습니다.

### 🐍이준호

