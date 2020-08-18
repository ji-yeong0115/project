<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입</title>
    <style>
	    #content_main {
	      width: 100%;
	      text-align: center;
	    }
    	/* number 태그 화살표 제거 */
			input[type="number"]::-webkit-outer-spin-button,
	    input[type="number"]::-webkit-inner-spin-button {
	        -webkit-appearance: none;
	        margin: 0;
	    }

	    .h2{ 
	     padding-top : 30px;
			 margin-bottom: 0 !important;
	    }
	    .h3{
	    margin-bottom: 0 !important;;
	    }
 
		</style>
</head>
<body>
	<div class="container-fluid">
	<%@ include file="../common/header.jsp" %>

	<div class="row" id="content">
<%--   <%@ include file="../common/aside.jsp" %>  --%> 


  <div id="content_main">
        <h2 class="h2">회원 가입</h2>
				<br><br>
        <div class="row">
            <div class="col-md-6 offset-md-3">

                <form method="POST" action="signUpGeneral.do" class="needs-validation" name="signUpForm" onsubmit="return validate();">
                      
            				
                    <!-- 아이디 -->
                    <div class="row mb-3 form-row">
                        <div class="col-md-3">
                            <label for="id">* 아이디</label>
                        </div>
                        <div class="col-md-6">
                            <input type="text" class="form-control" name="id" id="id" maxlength="12" placeholder="아이디를 입력하세요" autocomplete="off" required>
                            <!-- required : 필수 입력 항목으로 지정 -->
                            <!-- autocomplete="off" : input 태그 자동완성 기능을 끔 -->
                        </div>
                        
                        <div class="col-md-6 offset-md-3">
                            <span id="checkId">&nbsp;</span>
                        </div> 
                    </div>

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

                    <!-- 이름 -->
                    <div class="row mb-3 form-row">
                        <div class="col-md-3">
                            <label for="name">* 이름</label>
                        </div>
                        <div class="col-md-6">
                            <input type="text" class="form-control" id="name" name="name" required>
                        </div>
                        
                        <div class="col-md-6 offset-md-3">
                            <span id="checkName">&nbsp;</span>
                        </div>
                    </div>

                    <!-- 닉네임 -->
                    <div class="row mb-3 form-row">
                        <div class="col-md-3">
                            <label for="nickname">* 닉네임</label>
                        </div>
                        <div class="col-md-6">
                            <input type="text" class="form-control" id="nickname" name="nickname" required>
                        </div>
                        
                        <div class="col-md-6 offset-md-3">
                            <span id="checkNickname">&nbsp;</span>
                        </div>
                    </div>

                    <!-- 이메일 -->
                    <div class="row mb-3 form-row">
                        <div class="col-md-3">
                            <label for="email">* 이메일</label>
                        </div>
                        <div class="col-md-6">
                            <input type="email" class="form-control" id="email" name="email" autocomplete="off" required>
                        </div>
                        
                        <div class="col-md-3">
                            <button type="button" class="btn btn-primary" id="sendEmail">인증번호 전송</button>
                        </div>
                        <div class="col-md-6 offset-md-3">
                            <span id="checkEmail">&nbsp;</span>
                        </div>
                    </div>
                    
                    <!-- 이메일 인증번호 -->
                     <div class="row mb-3 form-row">
                        <div class="col-md-3">
                            <label for="email2">* 인증번호 확인</label>
                        </div>
                        <div class="col-md-6">
                            <input type="number" class="form-control" id="emailCheck" name="emailCheck" autocomplete="off">
                        </div>
                        
                        <div class="col-md-3">
                            <button type="button" class="btn btn-primary" id="confirmCode">확인</button>
                        </div>
                        <div class="col-md-6 offset-md-3">
                            <span id="checkCode">&nbsp;</span>
                        </div>
                    </div>
                    <br>

                    <hr class="mb-4">
                    <h3 class="h3">추가 정보(선택 사항)</h3>
                    <br><br>
                    <!-- 주소 -->
                    <h5>- 주소 -</h5>
                    <!-- 오픈소스 도로명 주소 API -->
                    <!-- https://www.poesis.org/postcodify/ -->
                    <div class="row mb-3 form-row">
                        <div class="col-md-3">
                            <label for="postcodify_search_button">우편번호</label>
                        </div>
                        <div class="col-md-3">
                            <input type="text" name="post" class="form-control postcodify_postcode5">
                        </div>
                        <div class="col-md-3">
                            <button type="button" class="btn btn-primary" id="postcodify_search_button">검색</button>
                        </div>
                    </div>

                    <div class="row mb-3 form-row">
                        <div class="col-md-3">
                            <label for="address1">도로명 주소</label>
                        </div>
                        <div class="col-md-9">
                            <input type="text" class="form-control postcodify_address" name="address1" id="address1">
                        </div>
                    </div>

                    <div class="row mb-3 form-row">
                        <div class="col-md-3">
                            <label for="address2">상세주소</label>
                        </div>
                        <div class="col-md-9">
                            <input type="text" class="form-control postcodify_details" name="address2" id="address2">
                        </div>
                    </div>

                    <!-- jQuery와 postcodify 를 로딩한다. -->
                    <script src="//d1p7wdleee1q2z.cloudfront.net/post/search.min.js"></script>
                    <script>
                        // 검색 단추를 누르면 팝업 레이어가 열리도록 설정한다.
                        $(function(){
                            $("#postcodify_search_button").postcodifyPopUp();
                        });
                    </script>
										
										
                    <!-- 아기정보 -->
                    <h5>- 아이 정보 -</h5>
                    <div class="row mb-3 form-row">
                        <div class="col-md-3">
                            <label for="kidsName">이름</label>
                        </div>
                        <div class="col-md-6">
                            <input type="text" class="form-control" id="kidsName" name="kidsName" >
                        </div>

                    </div>
                    <div class="row mb-3 form-row">
                        <div class="col-md-3">
                            <label for="kidsGender">성별</label>
                        </div>
                        <div class="col-md-6">
                        	<select class="form-control postcodify_details col-md-10" name="kidsGender" id="kidsGender" >
                        		<option value="">--아이의 성별을 선택해주세요--</option>
                        		<option value="f">여자아이</option>
                        		<option value="m">남자아이</option>
                        	</select>
                       
                    
                        </div> 

                    </div>
                    
                    <div class="row mb-3 form-row">
                        <div class="col-md-3">
                            <label for="kidsBirth">생년월일</label>
                        </div>
                        <div class="col-md-6">
                            <input type="date" class="form-control" id="kidsBirth" name="kidsBirth" >
                        </div>

                    </div>
                    
                    <div class="row mb-3 form-row">
                        <div class="col-md-3">
                            <label for="kidsHeight">키</label>
                        </div>
                        <div class="col-md-6">
                            <input type="text" class="form-control" id="kidsHeight" name="kidsHeight" >
                        </div>
 
                    </div>
                    
                    <div class="row mb-3 form-row">
                        <div class="col-md-3">
                            <label for="kidsWeight">몸무게</label>
                        </div>
                        <div class="col-md-6">
                            <input type="text" class="form-control" id="kidsWeight" name="kidsWeight" >
                        </div>

                    </div>
                  
<!--                          <div class="col-md-9">
                           <div class="form-check form-check-inline">
                                <input type="checkbox" name="terms" id="terms" value="terms"
                                class="form-check-input custom-control-input">
                                <label class="form-check-label custom-control-label" for="terms">약관동의</label>
                            </div>
                          </div> -->
                    <hr class="mb-4">
                    <button class="btn btn-primary btn-lg btn-block" type="submit">회원가입하기</button>
                    <a class="btn btn-primary btn-lg btn-block" href="<%=request.getContextPath()%>">취소</a>
                </form>
            </div>
        <br><br> 
	</div> 
</div>
		</div>
		<%@ include file="../common/footer.jsp" %>
		</div>
	<script>
        // 각 유효성 검사 결과를 저장할 객체
        var signUpCheck = { 
       		"id":false,
					"pwd1":false,
					"pwd2":false,
					"name":false,
					"nickname":false,
					"email":false,
					"emailCheck":false
				};
 	//********** 실시간 유효성 검사  ************/
			// 정규표현식
			
			// jQuery 변수 : 변수에 직접적으로 jQuery메소드를 사용할 수 있음.
			var $id = $("#id");
			var $pwd1 = $("#pwd1");
			var $pwd2 = $("#pwd2");
			var $pwd = $("#pwd1, #pwd2");
			var $name = $("#name");
			var $phone2 = $("#phone2");
			var $phone3 = $("#phone3");
			var $email = $("#email");
			var $nickname = $("#nickname");
			var $emailCheck = $("#emailCheck");
			

			
			// id를 입력하는 경우 발생하는 이벤트
			$("#id").on("input",function(){
				
				// 아이디유효성 검사
				var regExp = /^[a-z][a-zA-Z\d]{5,11}$/;
				if(!regExp.test($id.val())){
					$("#checkId").text("유효하지 않은 아이디 형식입니다.").css("color", "red");
					signUpCheck.id = false;
				}else{ // 
					$.ajax({
						url : "idDupCheck.do",
						data : {"id" : $id.val()},
						type : "get",
						success : function(result){
							if(result==0){
								$("#checkId").text("사용 가능한 아이디입니다.").css("color", "green");
								signUpCheck.id = true;
							}else{
								$("#checkId").text("이미 존재하는 아이디입니다.").css("color", "red");
								signUpCheck.id = false;
								
							}
							
						},error : function(){
							console.log("ajax 통신 실패");
						}
									
					});
					
				}
				
				
			});
			
			
			
			// 비밀번호 유효성 및 일치 검사
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
			
			
			// 이름 유효성 검사
			$name.on("input", function(){
				var regExp =  /^[가-힣]{2,}$/; // 한글 두 글자 이상
				
				if(!regExp.test($(this).val())){
					$("#checkName").text("이름 형식이 유효하지 않습니다.").css("color", "red");
					signUpCheck.name = false;
				}else{
					$("#checkName").text("이름 입력 성공.").css("color","green");
					signUpCheck.name = true;
				}
			});
			
			// 이메일 유효성 검사
			$("#email").on("input",function(){
		
				var regExp = /^[\w]{4,}@[\w]+(\.[\w]+){1,3}$/;
				if(!regExp.test($email.val())){
					$("#checkEmail").text("유효하지 않은 이메일 형식입니다.").css("color", "red");
					signUpCheck.email = false;
				}else{ // 
					$.ajax({
						url : "emailDupCheck.do",
						data : {"email" : $email.val()},
						type : "get",
						success : function(result){
							if(result==0){
								$("#checkEmail").text("사용 가능한 이메일입니다.").css("color", "green");
								signUpCheck.email = true;
							}else{
								$("#checkEmail").text("이미 존재하는 이메일입니다.").css("color", "red");
								signUpCheck.email = false;
								
							}
							
						},error : function(){
							console.log("ajax 통신 실패");
						}
									
					});
				}
			});
			
			var codeNum = 0;
			// 이메일 인증번호 전송
			$("#sendEmail").on("click", function() {
				$.ajax({
					url : "sendEmail.do",
					data : {"inputEmail" : $email.val()},
					success : function(result) {
						$("#checkEmail").text("인증번호가 전송되었습니다.").css("color", "blue");
						codeNum = result;
					}, error : function() {
						console.log("ajax 통신 실패");
					}
				});
			});
			
			// 인증번호 확인 후 수정 시, 회원가입 차단
			$("#emailCheck").on("input", function() {
				signUpCheck.emailCheck = false;
			});
			
			// 인증번호 확인
			$("#confirmCode").on("click", function() {
				if($emailCheck.val() == codeNum) {
					$("#checkCode").text("인증번호 확인").css("color", "green");
					signUpCheck.emailCheck = true;
				} else {
					$("#checkCode").text("인증번호가 불일치합니다.").css("color", "red");
					signUpCheck.emailCheck = false;
				}
			});
			
			// 닉네임유효성 검사
			$("#nickname").on("input",function(){
			
				var regExp = /^[가-힣a-zA-Z0-9]{2,10}$/;
				if(!regExp.test($nickname.val())){
					$("#checkNickname").text("유효하지 않은 닉네임 형식입니다.").css("color", "red");
					signUpCheck.nickname = false;
				}else{ 
					$.ajax({
						url : "nicknameDupCheck.do",
						data : {"nickname" : $nickname.val()},
						type : "get",
						success : function(result){
							if(result==0){
								$("#checkNickname").text("사용 가능한 닉네임입니다.").css("color", "green");
								signUpCheck.nickname = true;
							}else{
								$("#checkNickname").text("이미 존재하는 닉네임입니다.").css("color", "red");
								signUpCheck.nickname = false;
								
							}
							
						},error : function(){
							console.log("ajax 통신 실패");
						}
									
					});
					
				}
				
				
			});
			
		function validate(){
			for(var key in signUpCheck){
				if(!signUpCheck[key]){
					
					var msg;
					switch(key){
					case "id" : msg="아이디가 ";  break;
					case "pwd1" : case "pwd2" : msg="비밀번호가 ";  break;
					case "nickname" : msg="닉네임이"; break;
					case "name" : msg="이름이 ";  break;
					case "email" : msg="이메일이 ";  break;
					case "emailCheck" : msg="인증번호가 "; break;
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