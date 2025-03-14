# 👨🏻‍⚕️닥터스캔 (DICOM 활용 의료 영상 서비스) 


### 프로젝트 소개



### 1. 팀원 구성 및 역할 분담

#### 🐉 이민수 (팀장)
 - 기능 : 


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
 - 기능 : 
   - 

#### 🐔 김현창 (팀원)
 - 기능 : 
	- 

#### 🐍 이준호 (팀원)
 - 기능 : 
	- 



### 2. 개발환경



### 3. 개발 기간 및 작업 관리
- 프로젝트 기회 및 DB Modeling :
- 서비스 개발 : 
- 피드백 수정 및 배포 : 


### 4. 문서
- [프로젝트 기획서]()
- [요구사항 명세서]()
- [화면 설계서]()
- [테이블 정의서]()
- [인터페이스 정의서]()
  

#### [기능1]
- 
![로그인 X](영상주소)


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

https://github.com/user-attachments/assets/1e9d8db8-7405-4c73-b25f-aeee2a34d09c

#### [DICOM Viewer (판독 저장)]
- **표시** | **환자, Study, Series** 정보가 **외부 데이터베이스**에서 가져와 표시했습니다.
- **저장** | **중증도 레벨, 보고서 상태, 판독내용**을 입력후 저장하면  **내부 데이터베이스**에 저장합니다.
- **자동저장** | 판독 중 입력된 데이터는 중간중간 **자동 저장**되어, 예상치 못한 데이터 손실을 예방할 수 있습니다.
- **렌더링** | Cornerstone.js (코너스톤)로 초기화 및 이미지 로드 -> WebGL을 활용하여 DICOM 파일을 표시
  		  -> DICOM 데이터를 Blob 형태로 변환 후, HTML 뷰포트에 표시

https://github.com/user-attachments/assets/389f2865-6a9b-41d8-adc1-bd2910dd5dfb

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

https://github.com/user-attachments/assets/34e8e4f8-df40-4e43-9233-ac86f991888a

https://github.com/user-attachments/assets/6ff44f07-b6a4-4463-aa81-98ed3b1d42ab

### 6. 발표 영상
- https://www.youtube.com/watch?v=XUc5bNPgREE

### 7. 프로젝트 후기

### 🐉이민수

### 🐇박현균

### 🐅방서준

### 🐔김현창

### 🐍이준호

