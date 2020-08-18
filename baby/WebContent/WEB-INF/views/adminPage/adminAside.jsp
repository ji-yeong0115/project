<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
	
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="https://fonts.googleapis.com/css2?family=Do+Hyeon&family=Gamja+Flower&family=Poor+Story&family=Recursive&family=Sunflower:wght@300;500&display=swap" rel="stylesheet">
<style>
.adminPageSide{
	font-family: Poor Story;
    font-size: 25px;
    font-weight: bolder;
    color:black;
}
button{
  text-align: center;
}

section{
  display: none;
  float:left;
}
.nav-item{
width : 100%;
height : 20%;
}

.adminAside{
color:black;
}
</style>
</head>
<body>
<ul class="nav flex-column">
  <li class="nav-item adminPageSide">
      <a id="report" class="nav-link adminAside" href="<%=request.getContextPath()%>/adminPage/reportBoard.do">신고게시판 <span class="badge badge-pill badge-dark"></span></a>
    </li>
  <li class="nav-item adminPageSide">
    <a id="request" class="nav-link adminAside" href="<%=request.getContextPath()%>/adminPage/requestBoard.do">요청게시판 <span class="badge badge-pill badge-dark"></span>  </a>
  </li>
  <li class="nav-item adminPageSide">
    <a class="nav-link adminAside" href="<%=request.getContextPath()%>/adminPage/noticeBoard.do">공지사항</a>
  </li>
  <li class="nav-item adminPageSide">
    <a class="nav-link adminAside" href="<%=request.getContextPath()%>/adminPage/memberView.do?type=0">회원 정보 조회</a>
  </li>
  <li class="nav-item adminPageSide">
    <a class="nav-link nav-link dropdown-toggle adminAside" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false" width="30%" >글쓰기</a>
        <div class="dropdown-menu" aria-labelledby="navbarDropdown">
          <a class="dropdown-item" href="<%=request.getContextPath()%>/adminPage/adminWrite.do?type=0">공지사항</a>
          <a class="dropdown-item" href="<%=request.getContextPath()%>/adminPage/adminWrite.do?type=1">병원게시판</a>
          <a class="dropdown-item" href="<%=request.getContextPath()%>/adminPage/adminWrite.do?type=2">상식게시판</a>
        </div>
  </li>
</ul>

<script>
$(function(){
	$.ajax({
		url : "<%=request.getContextPath()%>/adminPage/reportCount.do",
		success : function(count){
			$("#report > span").text(count);
			
		}, error : function(count){
			console.log("Ajax 통신 실패");
		} 
	});		
	
	$.ajax({
		url : "<%=request.getContextPath()%>/adminPage/requestCount.do",
		success : function(count){
			$("#request > span").text(count);
			
		}, error : function(count){
			console.log("Ajax 통신 실패");
		} 
	});		
});
</script>
</body>
</html>