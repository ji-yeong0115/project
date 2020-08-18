<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ID/PWD 찾기</title>
        <style>
        input[type="number"]::-webkit-outer-spin-button,
		    input[type="number"]::-webkit-inner-spin-button {
		        -webkit-appearance: none;
		        margin: 0;
		    }
        #selectMember * {
		       
		        box-sizing: border-box;
		        font-family: Noto Sans KR;
		    }
		    #selectMember{
		        width: 80%;
		        margin: auto;
		    }
		    #selectHeader{
		        text-align: center;
		        padding: 50px;
		    }
		    /* Section */
		    #select{
		    		/* width: 100% */
		        display: flex;
		    }
		    
		    #emailNum {
		    	-webkit-appearance: none;
		    }
		   
        </style>
</head>
<body>
<div class="container-fluid">
	<%@ include file="../common/header.jsp" %>

	<div class="row" id="content">
  <%@ include file="../common/aside.jsp" %>  

	<div id="selectMember">
	    <header id="selectHeader"><h1>비밀번호 찾기</h1></header>
	    <section id="select">
	 		<form action="updatePwdForm.do" onsubmit="return validate();">
	 			<label>이메일 입력 : </label>
	 			<input type="email" id="inputEmail" name="inputEmail">
	 			<span id="checkEmail">&nbsp;</span>
	 			<button type="button" id="sendCode">인증번호 보내기</button>
	 			
	 			<label>인증번호 확인 : </label>
	 			<input type="number" id="emailCode" name="emailCode">
	 			
	 			<button type="submit">확인</button>
	 		</form>   	
	    </section>
    </div>
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
	
	$.ajax({
		url : "selectEmail.do",
		data : {"inputEamil" : $inputEmail.val()},
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