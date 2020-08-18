<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입 유형 선택</title>
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
		    #select > div{
		        width: 50%;
		        text-align: center;
		    }
		    .membericon img{
		        width: 200px;
		    }
		    #select div h3{
		        padding: 30px;
		    }
		    #select button:hover{
		        cursor: pointer;
		    }
		    #select button{
		        background-color: #fbc531 ;
		        width: 100px;
		        height: 30px;
		        border-radius: 5px;
		        border: none;
		    }
		    button:focus {
				    border: none;
				    cursor: pointer;
				    outline: none;
		    }
		
		    /* Expalin */
		    .explain{
		        display: flex;
		        text-align: center;
		    }
		    .explain>div{  	
		        width: 50%;
		        text-align: center;
		    }
			.btn{
				margin-bottom: 30px;
			}
			

        </style>
</head>
<body>
<div class="container-fluid">
	<%@ include file="../common/header.jsp" %>

	<div class="row" id="content">
  <%@ include file="../common/aside.jsp" %>  

	<div id="selectMember">
            <header id="selectHeader"><h1>회원가입</h1></header>
            <section id="select">
                <div class="individualMember">
                    <div class="membericon"><img src="<%=request.getContextPath()%>/resources/user.png"></div>
                    <div class="text"><h3>개인회원</h3></div>
                    <a class="btn btn-warning individualBtn" href="<%=request.getContextPath()%>/member/signUpGeneralForm.do">회원가입하기</a>
                </div>
                <div class="businessMember">
                    <div class="membericon"><img src="<%=request.getContextPath()%>/resources/user.png"></div>
                    <div class="text">
                        <h3>사업자회원</h3>
                    </div>
                    <a class="btn btn-warning businessBtn" href="<%=request.getContextPath()%>/member/signUpSellerForm.do">회원가입하기</a>
                </div>
            </section>
            <div class="explain">
                <div class="individualmemberEX">
                    <span>무료 회원에 가입하시면 <br>여러가지 메뉴를 무료로 이용하실 수 있습니다.</span>
                </div>
                <div class="businessmemberEx">
                    <span>사업자 회원에 가입하시면 <br>사업자 전용 메뉴를 이용하실 수 있습니다.</span>
                </div>
            </div>
        </div>
	</div>
	
	<%@ include file="../common/footer.jsp" %>
</div>
</body>
</html>