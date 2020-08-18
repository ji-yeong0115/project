<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>우리 회사 정보</title>
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
 
		</style>
</head>
<body>
	<div class="container-fluid">
	<%@ include file="../common/header.jsp" %>

	<div class="row" id="content">
  <%@ include file="../common/aside.jsp" %>  

  <div id="content_main">
        <h2 class="h2">회사정보</h2>
				<br><br>
        <div class="row">
            <div class="col-md-6 offset-md-3">

                <form method="POST" action="updateSellerForm.do" class="needs-validation" >
                        <%-- action="<%=request.getContextPath() %>/member/signUp.do" --%>
            				
                    <!-- 아이디 -->
                    <div class="row mb-3 form-row">
											<div class="col-md-3">
												<h6>아이디</h6>
											</div>
											<div class="col-md-6">
												<h5 id="id"><%=loginMember.getMemberId() %></h5>
											</div>
                    </div>


                    <div class="row mb-3 form-row">
												<div class="col-md-3">
													<h6>회사명</h6>
												</div>
												<div class="col-md-6">
													<h5 id="name"><%=loginMember.getMemberName() %></h5>
												</div>
                    </div>
                    <div class="row mb-3 form-row">
                        <div class="col-md-3">
                            <label for="email">이메일</label>
                        </div>
												<div class="col-md-6">
													<h5 id="email"><%=loginMember.getMemberEmail() %></h5>
												</div>

                    </div>
                   <div class="row mb-3 form-row">
                        <div class="col-md-3">
                            <label for="email">전화번호</label>
                        </div>
												<div class="col-md-6">
													<h5 id="email"><%=loginSeller.getCompanyPhone() %></h5>
												</div>

                    </div>
                   <div class="row mb-3 form-row">
                        <div class="col-md-3">
                            <label for="email">사업자번호</label>
                        </div>
												<div class="col-md-6">
													<h5 id="email"><%=loginSeller.getCompanyNo() %></h5>
												</div>

                    </div>
                   <div class="row mb-3 form-row">
                        <div class="col-md-3">
                            <label for="companyAddr">주소</label>
                        </div>
												<div class="col-md-6">
													<h5 id="companyAddr"><%=loginMember.getMemberAddress() %></h5>
												</div>

                    </div>										

                    <hr class="mb-4">
                    <button class="btn btn-primary btn-lg btn-block" type="submit">회사정보 수정하기</button>
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