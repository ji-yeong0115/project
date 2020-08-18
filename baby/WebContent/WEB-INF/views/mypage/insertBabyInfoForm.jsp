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

        <div class="row">
            <div class="col-md-6 offset-md-3">

                <form method="POST" action="insertBabyInfo.do" class="needs-validation" name="signUpForm" onsubmit="return validate();">
                      
            
                    <h3 class="h3">아이 정보를 입력해주세요</h3>
                    <br>
                    <br>
        					
										
                    <!-- 아기정보 -->
                 
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
                    <button class="btn btn-primary btn-lg btn-block" type="submit">아이정보입력</button>
                    <a class="btn btn-primary btn-lg btn-block" href="<%=request.getContextPath()%>">취소</a>
                </form>
            </div>
        <br><br> 
	</div> 
</div>
		</div>
		<%@ include file="../common/footer.jsp" %>
		</div>
	
</body>
</html>