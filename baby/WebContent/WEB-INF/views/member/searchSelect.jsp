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
		   
        </style>
</head>
<body>
<div class="container-fluid">
	<%@ include file="../common/header.jsp" %>

	<div class="row" id="content">
  <%@ include file="../common/aside.jsp" %>  

	<div id="selectMember">
            <header id="selectHeader"><h1>아이디/비밀번호 찾기</h1></header>
            <section id="select">
                    <a class="btn btn-warning individualBtn" href="<%=request.getContextPath()%>/member/searchIdForm.do">아이디 찾기</a>
                    <a class="btn btn-warning businessBtn" href="<%=request.getContextPath()%>/member/searchPwdForm.do">비밀번호 찾기</a>
            </section>
        </div>
	</div>
	
	<%@ include file="../common/footer.jsp" %>
</div>
</body>
</html>