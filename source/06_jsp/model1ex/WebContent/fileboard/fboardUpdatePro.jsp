<%@page import="com.lec.dao.FileboardDao"%>
<%@page import="com.lec.ex.dto.FileboardDto"%>
<%@page import="com.oreilly.servlet.multipart.DefaultFileRenamePolicy"%>
<%@page import="java.io.FileOutputStream"%>
<%@page import="java.io.FileInputStream"%>
<%@page import="java.io.File"%>
<%@page import="java.io.OutputStream"%>
<%@page import="java.io.InputStream"%>
<%@page import="java.io.IOException"%>
<%@page import="com.oreilly.servlet.MultipartRequest"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% String conPath = request.getContextPath(); %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>
</head>
<body>
	<%
		// 첨부한 파일을 서버에 저장하고 파일이름 가져오기
		String path = request.getRealPath("fileboardUpload");
		int maxSize = 1024*1024*5;
		String ffilename = null;
		MultipartRequest mRequest = null;
		try{
			mRequest = new MultipartRequest(request,path,maxSize,"utf-8",new DefaultFileRenamePolicy());
			ffilename = mRequest.getFilesystemName("ffilename");
		}catch(IOException e){
			System.out.println(e.getMessage());
		}
		
		// 서버에 저장한 파일을 소스폴더로 복사하기
		if(ffilename!=null){
			InputStream is = null;
			OutputStream os = null;
			try{
				File serverFile = new File(path+"/"+ffilename);
				is = new FileInputStream(serverFile);
				os = new FileOutputStream("D:/webPro/source/06_jsp/model1ex/WebContent/fileboardUpload/"+ffilename);
				byte[] bs = new byte[(int)serverFile.length()];
				int readByteCnt;
				while((readByteCnt = is.read(bs)) != -1){
					os.write(bs,0,readByteCnt);
				}
				System.out.println(ffilename+"복사함");
			}catch(IOException e){
				System.out.println(e.getMessage());
			}finally{
				if(os!=null) os.close();
				if(is!=null) is.close();
			}
		}
		
		// ffilename외의 다른 파라미터들 받기 (pageNum, dbfilename, fnum, fsubject, fcontent, fpw)
		String pageNum = mRequest.getParameter("pageNum");
		String dbfilename = mRequest.getParameter("dbfilename");
		int fnum = Integer.parseInt(mRequest.getParameter("fnum"));
		String fsubject = mRequest.getParameter("fsubject");
		String fcontent = mRequest.getParameter("fcontent");
		String fpw		  = mRequest.getParameter("fpw");
		String fip 			= request.getRemoteAddr();
		
		// 첨부파일 입력없을시 기존 첨부파일로 유지 : 입력있으면 새로운 첨부파일로 대체
		ffilename = ffilename==null? dbfilename : ffilename;  
		
		// 파라미터를 이용하여 dto객체 생성 (cid, fref .. 등은 어차피 업데이트 안할거라서 뭘 넣든 상관없다)
		FileboardDto fDto = new FileboardDto(fnum, null, fsubject, fcontent, ffilename, fpw, 0, 0, 0, fip);
		
		// dao를 통해서 update
		FileboardDao fDao = FileboardDao.getInstance();
		int result = fDao.updateBoard(fDto);
		if(result==FileboardDao.SUCCESS){
			%>
				<script>
					alert('글 수정 완료');
<%-- 					location.href = 'fboardList.jsp?pageNum=<%=pageNum%>'; --%>
					location.href = 'fboardContent.jsp?fnum=<%=fnum%>&pageNum=<%=pageNum%>';
				</script>
			<%
		}else{
			%>
				<script>
					alert('글 수정 실패(data길이 확인요망)');
					history.back();
				</script>
			<%
		}
	%>
</body>
</html>