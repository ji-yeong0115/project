<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>아이디 찾기</title>
<style type="text/css">
		 
		 body{
		 cursor: default;
		 }
		 
     *{
         box-sizing: border-box;
     }
		 
		 .title{
		 padding-top: 30px;
		 }
     .wrapper{
         width: 100%;
         height: 700px;
         margin: auto;
     }
     .form{
         text-align: center;
         margin: auto;
         width: 25%;
         height: 100%;
         border: 30px;
         font-family: 'Noto Sans KR', sans-serif;
     }
     a{
         text-decoration: none !important;
         font-size: 15px;
     }
     .header{
         height: 15%;
     }
     .atags{
         height: 10%;
     }
     .inputName{
         height: 10%;
         position: relative;
     }
     .inputEmail{
         height: 10%;
     }
     .footer{
         height: 30%;
     }
     input{
         width: 100%;
         height: 50px;
         border: 1px solid #f1f1f1  ;
     }
     .atags {
     overflow: hidden;
     }
     .atags > a,
     .atags > button {
     display: block;
     width: 50%;
     box-sizing: border-box;
     padding: 14px 0;
     background-color: #f1f1f1;
     border: 1px solid #f1f1f1;
     border-bottom: none;
     color: #aaaaaa;
     text-align: center;
     float: left;
     }
     .atags > a.is-active,
     .atags > button.is-active {
     background-color: #ffffff;
     color: #000000;
     }
     #findBtn{
         background-color:#f1f1f1 ;
         cursor: pointer;
         border: none;
         outline: none;
         width: 100%;
         height: 50px;
         align-items: center;
         position: relative;
         top: 100px;
         color: #aaaaaa;
     }
     
     #findBtn:hover{
     background-color: #ffeaa7 ;
     
     }

     a:hover{
     background-color: #ffeaa7 ;
     }

</style>
</head>
<body>
<div class="container-fluid">
	<%@ include file="../common/header.jsp" %>
  <div class="wrapper">
      <form class="form" action="searchId.do">
          <header class="header">
              <h2 class="title">아이디찾기</h2>
          </header>
          <div class="atags">
              <a href="">아이디 찾기</a>
              <a href="<%=request.getContextPath()%>/member/searchPwdForm.do">비밀번호 찾기</a>
          </div>
          <div class="inputName">
          		<input type="text" name="inputName" placeholder="이름 입력" autocomplete=off required>		
          </div>
					<span id="">&nbsp;</span>
				
          <div class="inputEmail">
          		<input type="email" name="inputEmail" placeholder="이메일 입력" autocomplete=off required>
          </div>
          <!-- <span id="checkEmail">&nbsp;</span> -->
          <div class="footer">
             	<button id="findBtn">아이디 찾기</button>
          </div>
      </form>
  </div>
  </div>
</body>
</html>