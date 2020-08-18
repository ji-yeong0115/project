<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ID/PWD 찾기</title>
        <style>

        #selectMember * {	       
		        box-sizing: border-box;
		        font-family: Noto Sans KR;
		    }
		    #selectMember{
		    		height: 700px;
		        width: 50%;
		        margin: auto;
		    }
		    #selectHeader{
		        text-align: center;
		        padding: 50px;
		    }
		    /* Section */
		    #select{
		    		/* width: 100% */
		       
		    }
		    
		    #emailNum {
		    	-webkit-appearance: none;
		    }
		    
		    .btn btn-primary btn-lg btn-block{
		    width: 100px;
		    }
		    
		    #btn{
		    margin-top: 50px;
		    }
		    
		  
		   
        </style>
</head>
<body>
<div class="container-fluid">
	<%@ include file="../common/header.jsp" %>


	<div id="selectMember">
	    <header id="selectHeader"><h1>새로운 비밀번호 설정하기</h1></header>
	    <section id="select">
	 		<form action="updateNewPwd.do" onsubmit="return validate();">
	 			<!-- 비밀번호 -->
        <div class="row mb-3 form-row">
            <div class="col-md-3">
                <label for="pwd1">* 비밀번호</label>
            </div>
            <div class="col-md-6">
                <input type="password" class="form-control" id="pwd1" name="pwd1" maxlength="12" placeholder="비밀번호를 입력하세요" required>
            </div>
            
            <div class="col-md-6 offset-md-3">
                <span id="checkPwd1">&nbsp;</span>
            </div>
        </div>

        <!-- 비밀번호 확인 -->
        <div class="row mb-3 form-row">
            <div class="col-md-3">
                <label for="pwd2">* 비밀번호 확인</label>
            </div>
            <div class="col-md-6">
                <input type="password" class="form-control" id="pwd2" maxlength="12" placeholder="비밀번호 확인" required>
            </div>
            
            <div class="col-md-6 offset-md-3">
                <span id="checkPwd2">&nbsp;</span>
            </div>
        </div>
        
        <button class="btn btn-primary btn-lg btn-block" type="submit" id="btn">비밀번호 변경하기</button>
        
	 		</form>   	
	    </section>
    </div>
	</div>
	
	<%@ include file="../common/footer.jsp" %>
</div>

<script>
//각 유효성 검사 결과를 저장할 객체
var signUpCheck = { 
			"pwd1":false,
			"pwd2":false,
};
		
var $pwd1 = $("#pwd1");
var $pwd2 = $("#pwd2");
var $pwd = $("#pwd1, #pwd2");

//비밀번호 유효성 및 일치 검사
$pwd.on("input", function(){
	//영어 대,소문자 + 숫자, 총 6~12글자
	var regExp = /^[A-Za-z0-9]{6,12}$/;
	
	// 비밀번호1 유효성 검사
	if(!regExp.test($("#pwd1").val())){ 
		$("#checkPwd1").text("비밀번호 형식이 유효하지 않습니다.").css("color","red");
		signUpCheck.pwd1 = false;
	}else{
		$("#checkPwd1").text("유효한 비밀번호 형식입니다.").css("color","green");
		signUpCheck.pwd1 = true;
	}
	
	// 비밀번호1이 유효하지 않은 상태로 비밀번호 2를 작성하는 경우
	if(!signUpCheck.pwd1 && $pwd2.val().length > 0){
		swal("유효한 비밀번호를 작성해 주세요.");
		$pwd2.val("");
		$pwd1.focus();
	}else if(signUpCheck.pwd1 && $pwd2.val().length > 0){
		if($("#pwd1").val().trim() != $("#pwd2").val().trim()){
			$("#checkPwd2").text("비밀번호 불일치").css("color","red");
			signUpCheck.pwd2 = false;
		}else{
			$("#checkPwd2").text("비밀번호 일치").css("color","green");
			signUpCheck.pwd2 = true;
		}
	}
});

function validate(){
	for(var key in signUpCheck){
		if(!signUpCheck[key]){
			
			var msg;
			switch(key){
			case "pwd1" : case "pwd2" : msg="비밀번호가 ";  break;
			}
			
			alert(msg + "유효하지 않습니다.");
			var el = "#"+key;
			$(el).focus();
			return false;
		}
	}
}
</script>
</body>
</html>