<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>아이 정보 수정</title>
    <style>
	    #content_main {
	      width: 80%;
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
	    #companyAddr{
	    width: 550px;
	    }
	    .row mb-3 form-row > p{
	    display: block;
	    }
 
		</style>
</head>
<body>
	<div class="container-fluid">
	<%@ include file="../common/header.jsp" %>

	<div class="row" id="content">
  <%@ include file="../common/aside.jsp" %>  

  <div id="content_main">
        <h2 class="h2">내 아이 정보 수정</h2>
				<br><br>
        <div class="row">
            <div class="col-md-6 offset-md-3">

                <form method="POST" action="updateBabyInfo.do" class="needs-validation" >
            				
                    <div class="row mb-3 form-row">
                        <div class="col-md-3">
                            <label for="kidsName">이름</label>
                        </div>
                        <div class="col-md-6">
                            <input type="text" class="form-control" id="kidsName" name="kidsName" value=<%=loginGeneral.getKidsName() %> >
                        </div>
                    </div>


                    
                    <div class="row mb-3 form-row">
                        <div class="col-md-3">
                            <label for="kidsHeight">키</label>
                        </div>
                        <div class="col-md-6">
                            <input type="text" class="form-control" id="kidsHeight" name="kidsHeight" value="<%=loginGeneral.getKidsHeight()%> "  >
                        </div>
                    </div>
                    
                    <div class="row mb-3 form-row">
                        <div class="col-md-3">
                            <label for="kidsWeight">몸무게</label>
                        </div>
                        <div class="col-md-6">
                            <input type="text" class="form-control" id="kidsWeight" name="kidsWeight" value="<%=loginGeneral.getKidsWeight() %> " >
                        </div>
                    </div>				
                    <hr class="mb-4">
                   
                    <button class="btn btn-primary btn-lg btn-block" type="submit">아이정보 수정하기</button>
                    <a class="btn btn-primary btn-lg btn-block" href="<%=request.getContextPath()%>">취소</a>
                </form>
            </div>
        <br><br> 
	</div> 
</div>
		</div>
		<%@ include file="../common/footer.jsp" %>
		</div>
		<script type="text/javascript">
		
		
		
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
		</script>

		
</body>
</html>