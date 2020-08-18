<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.kh.baby.mypage.model.vo.Grow"%>
<%
	Grow grow = (Grow)request.getAttribute("grow");
	String gender = "";
	if(grow != null) {
		if(grow.getGender().equals("f")) {
			gender = "여아";
		} else {
			gender = "남아";
		}
	}
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>우리 아이 정보</title>
    <style>
	    #content_main {
	      width: 80%;
	      text-align: center;
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
	    
	    #grow {
	    	background-color: rgb(255, 255, 100);
	    	padding : 15px;
	    	border-radius: 10em;
	    	width: 400px;
	    	margin: auto;
	    }
	    
	    #grow > p {
	    	font-size: 25px;
	    	font-weight: bold;
	    }
	    
	    #grow > span {
	    	font-size: 20px;
	    }
 
		</style>
</head>
<body>
	<div class="container-fluid">
	<%@ include file="../common/header.jsp" %>

	<div class="row" id="content">
  <%@ include file="../common/aside.jsp" %>  

  <div id="content_main">
        <h2 class="h2">내 아이 정보</h2>
				<br><br>
        <div class="row">
            <div class="col-md-6 offset-md-3">

                <form method="POST" action="updateBabyInfoForm.do" class="needs-validation" >
            				

                    <div class="row mb-3 form-row">
                        <div class="col-md-3">
                            <label for="kidsName">이름</label>
                        </div>
												<div class="col-md-6">
													<h5 id="kidsName"><%=loginGeneral.getKidsName() %></h5>
												</div>

                    </div>
                   <div class="row mb-3 form-row">
                        <div class="col-md-3">
                            <label for="">생년월일</label>
                        </div>
												<div class="col-md-6">
													<h5 id="kidsBirth"><%=loginGeneral.getKidsBirth() %></h5>
												</div>

                    </div>
                   <div class="row mb-3 form-row">
                        <div class="col-md-3">
                            <label for="">성별</label>
                        </div>
												<div class="col-md-6">
													<h5 id="kidsGender"><%=babyGender %></h5>
												</div>

                    </div>
                   <div class="row mb-3 form-row">
                        <div class="col-md-3">
                            <label for="companyAddr">키</label>
                        </div>
												<div class="col-md-6">
													<h5 id="kidsHeight"><%=loginGeneral.getKidsHeight() %> cm</h5>
												</div>

                    </div>							
                    			
                   <div class="row mb-3 form-row">
                        <div class="col-md-3">
                            <label for="kidsWeight">몸무게</label>
                        </div>
												<div class="col-md-6">
													<h5 id="kidsWeight"><%=loginGeneral.getKidsWeight() %> kg</h5>
												</div>

                    </div>										

                    <hr class="mb-4">
                    <div id="grow">
            
											<p>[<%=grow.getMonth() %>개월 <%=gender %> 평균 신체 발달]</p>
											<span>키 : <%=grow.getCm() %> / 몸무게 : <%=grow.getKg() %></span>  <br>
											(출처: 질병관리본부, 2007) <br>

                   </div>	
                    
                    <button class="btn btn-primary btn-lg btn-block" type="submit" style="margin-top: 15px;">아이정보 수정하기</button>
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