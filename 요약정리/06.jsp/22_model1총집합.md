# Mode1 으로 게시판 만들기

[프로젝트 소스 경로](https://github.com/Alipheese16th/webPro/tree/main/source/06_jsp/model1ex)

- Customer 관련 : 회원가입, 로그인, 정보수정, 로그아웃, 회원리스트보기
- 고객센터(파일첨부게시판) – 글쓰기, 답변글쓰기, 글목록(페이징추가), 글수정, 글 삭제
- 도서등록, 도서리스트보기(페이징), 도서상세보기
- 로그인한 사람만 도서등록과 글쓰기(원글, 답변글)가 가능합니다
- 글수정은 내가 쓴 글만 수정 가능합니다
- 글삭제는 내가 쓴 글이 아니여도, 로그인전이여도, 삭제용비번만 알면 삭제 가능
- header의 gnb와 footer copyright영역의 배경색은 #003300


- 로그인 후 해더 변경, 도서등록 메뉴 생기고 글쓰기 생김
- 로그인 전 글 상세보기 페이지엔 답변버튼이 없습니다. 내가 쓴 글이 아니면 수정버튼도 없습니다
- 첨부파일이 없을 경우 “첨부파일 없음”라고 출력
- 로그인 한 후 고객센터 게시판 : 수정(본인 글만 수정), 답변(로그인 후), 삭제(비번알면 타인글도 로그인 전도 가능)



## 프로젝트 구성요소

### DTO 3개(BookDto,CustomerDto,FileboardDto)
### DAO 3개(BookDao,CustomerDao,FileboardDao)

### img 폴더
- 이 프로그램에서 필요한 이미지들(로고, hot.gif, level.gif, re.gif, fileup.jpg)
### main 폴더 
- footer.jsp
- header.jsp
- main.jsp(첫화면)

### customer폴더
- joinForm.jsp
- joinPro.jsp
- loginForm.jsp
- loginPro.jsp
- logout.jsp
- modifyForm.jsp
- modifyPro.jsp

### book폴더
- bookList.jsp
- bookRegister.jsp
- bookRegisterPro.jsp
- bookContent.jsp

### bookImg폴더
- 도서등록시 업로드할 파일들이 저장될 폴더

### fileboard폴더
– fboardList.jsp
- fboardContent.jsp
- fboardDeleteForm.jsp
- fboardDeletePro.jsp
- fboardUpdateForm.jsp
- fboardUpdatePro.jsp
- fboardWriteForm.jsp
- fboardWritePro.jsp

### fileboardUpload 폴더
- 파일첨부게시판에서 첨부한 파일들이 저장될 폴더

### 그 외 css

