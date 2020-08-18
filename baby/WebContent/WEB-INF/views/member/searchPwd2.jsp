<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>비밀번호 찾기</title>
<style type="text/css">
        input[type="number"]::-webkit-outer-spin-button,
		    input[type="number"]::-webkit-inner-spin-button {
		        -webkit-appearance: none;
		        margin: 0;
		    }
		    
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
			 .inputEmail{
			     height: 10%;
			     position: relative;
			 }
			 .inputNumber{
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
			 #sendCode{
			     position: absolute; 
			     top: 0; 
			     border-radius: 5px; 
			     right: 0px; 
			     z-index: 2; 
			     border: none; 
			     top: 2px; 
			     height: 30px; 
			     cursor: pointer; 
			     color: #aaaaaa; 
			     background-color: #f1f1f1; 
			     transform : translate(-10px, 8px)
			 }
			 #sendCode:hover{
	     background-color: #ffeaa7;
	     }
	     
	     #findBtn:hover{
    	 background-color: #ffeaa7 ;
     
    	 }
	     #findId:hover{
    	 background-color: #ffeaa7 ;
     
    	 }
	     #findPwd:hover{
    	 background-color: #ffeaa7 ;
     
    	 }
    	 

</style>
</head>
<body>
<div class="container-fluid">
	<%@ include file="../common/header.jsp" %>

	 <div class="wrapper">
		 <form action="updatePwdForm.do" class="form" onsubmit="return validate();">
     
         <header class="header">
             <h2 class="title">비밀번호 찾기</h2>
         </header>
         <div class="atags">
             <a id="findId" href="<%=request.getContextPath()%>/member/searchSelectForm.do">아이디 찾기</a>
             <a id="findPwd">비밀번호 찾기</a>
         </div>
         <div class="inputEmail">
	         <input type="email" id="inputEmail" name="inputEmail" placeholder="이메일 입력" autocomplete=off required>
	         <button type="button" id="sendCode">인증번호 요청</button>
         </div>
         <span id="checkEmail">&nbsp;</span>
         <div class="inputNumber">
        	 <input type="number" id="emailCode" name="emailCode"placeholder="인증번호 입력">
         </div>
         <div class="footer">
          	<button id="findBtn" type="submit">비밀번호 재설정</button>
        
     		 </div>
	 	</form>
   </div>
   	
	
	<%@ include file="../common/footer.jsp" %>
 </div>
   <script>
var email = false;
var code = false;

var codeNum = 0;

var $inputEmail = $("#inputEmail");
var $emailCode = $("#emailCode");

$("#inputEmail").on("input", function() {
	console.log($inputEmail.val());
	$.ajax({
		url : "selectEmail.do",
		data : {"inputEmail" : $inputEmail.val()},
		success : function(result) {
			if(result > 0) {
				$("#checkEmail").text("해당 이메일로 인증번호를 전송하시려면 보내기 버튼을 눌러주세요.").css("color", "green");
				email = true;
			} else {
				$("#checkEmail").text("해당 이메일이 존재하지 않습니다.").css("color", "red");
				email = false;
			}
		}, error : function() {
			console.log("ajax 통신 실패");
		}
	});
});

var $emailCode = $("#emailCode");
$("#sendCode").on("click", function() {
	
	$.ajax({
		url : "sendEmail.do",
		data : {"inputEmail" : $inputEmail.val()},
		success : function(result) {
			codeNum = result;
		}, error : function() {
			console.log("ajax 통신 실패");
		}
	});
});

$("#emailCode").on("input", function() {
	if($emailCode.val() == codeNum) {
		code = true;
	} else {
		code = false;
	}
});

function validate(){
	var msg = "확인되었습니다.";
	var flag = true;
	
	if(!email) {
		msg = "확인되지 않는 이메일입니다.";
		flag = false;
	} else if(!code) {
		msg = "인증번호가 불일치합니다.";
		flag = false;
	}
	
	alert(msg);
	return flag;
}
</script>
</body>
</html>