<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>비밀번호변경</title>
    <style>
	    #content_main {
	      width: 80%;
	      text-align: center;
	    }
    	/* number 태그 화살표 제거 */

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
  <%@ include file="../common/aside.jsp" %>  
	
  <div id="content_main">
        <h2 class="h2">비밀번호 변경</h2>
				<br><br>
        <div class="row">
            <div class="col-md-6 offset-md-3">

                <form method="POST" action="replacePwd.do" class="needs-validation" name="replacePwdForm" onsubmit="return validate();">
            				
                    <!-- 아이디 -->
                    <div class="row mb-3 form-row">
											<div class="col-md-3">
												<h6>아이디</h6>
											</div>
											<div class="col-md-6">
												<h5 id="id"><%=loginMember.getMemberId() %></h5>
											</div>
                    </div>

										<hr>
                    <!-- 이름 -->
						<div class="row mb-3 form-row">
							<div class="col-md-3">
								<h6>현재 비밀번호</h6>
							</div>
							<div class="col-md-6">
								<input type="password" class="form-control" id="currentPwd"
									name="currentPwd">
							</div>
						</div>

						<!-- 새 비밀번호 -->
						<div class="row mb-3 form-row">
							<div class="col-md-3">
								<h6>새 비밀번호</h6>
							</div>
							<div class="col-md-6">
								<input type="password" class="form-control" id="newPwd1"
									name="newPwd1">
							</div>
						</div>

						<!-- 새 비밀번호 확인-->
						<div class="row mb-3 form-row">
							<div class="col-md-3">
								<h6>새 비밀번호 확인</h6>
							</div>
							<div class="col-md-6">
								<input type="password" class="form-control" id="newPwd2"
									name="newPwd2">
							</div>
						</div>


                    <hr class="mb-4">
                    <button class="btn btn-primary btn-lg btn-block" type="submit">비밀번호변경하기</button>
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
		// submit 동작
		function validate() {
			// 비밀번호  유효성 검사
			//영어 대,소문자 + 숫자, 총 6~12글자
			var regExp = /^[A-Za-z0-9]{6,12}$/;
			if(!regExp.test($("#newPwd1").val())){ 
				alert("유효하지 않은 비밀번호 입니다.");
				$("#newPwd1").focus();
				
				return false;
            }
			
			if($("#newPwd1").val() != $("#newPwd2").val()){
				alert("새 비밀번호가 일치하지 않습니다.");
				$("#newPwd2").focus();
				
				return false;
			}

		}
		
	</script>
		
</body>
</html>