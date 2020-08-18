<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.kh.baby.member.model.vo.Member"%>
<%@page import="com.kh.baby.member.model.vo.General"%>
<%
	Member mploginMember = (Member)session.getAttribute("loginMember");
  General mploginGeneral = (General)session.getAttribute("loginGeneral"); 
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<style>
/* -------------------------------------------- */

    .dropdown-toggle::after {
      display: block;
      position: absolute;
      top: 50%;
      right: 20px;
      transform: translateY(-50%);
    }

    .asideCategory
    {
        text-decoration: none; color: black;
        display: block;
    }

    .asideCategory:hover{
        text-decoration: none; color: black; font-weight: bold;
    }
    
    
    
	#aside_menu li>a{
		width: 100%;
	}
	

</style>
</head>
<body>


	        <ul class="list-group">
	        <%if(mploginMember.getMemberGrade().equals("G")) { %>
	        		<%if(mploginGeneral.getKidsHeight()==0 && mploginGeneral.getKidsWeight()==0){ %>
								<li class="list-group-item" id="atag1"><a href="<%=request.getContextPath()%>/mypage/insertBabyInfoForm.do" class="asideCategory">우리 아이 정보</a></li>
							<%}else{ %>
			       		<li class="list-group-item" id="atag1"><a href="<%=request.getContextPath()%>/mypage/babyInfoForm.do" class="asideCategory">우리 아이 정보</a></li>
			       		<li class="list-group-item" id="atag2" ><a href="<%=request.getContextPath()%>/mypage/babyDiaryForm.do?cp=1" class="asideCategory"">육아일기</a></li>			
							<% }%>
	       	<%} else { %>
	       		<li class="list-group-item" id="atag1"><a href="<%=request.getContextPath()%>/member/myCompanyInfoForm.do" class="asideCategory">우리 회사 정보</a></li>
	       	<% } %>
	       		<li class="list-group-item" id="atag3"><a href="<%=request.getContextPath()%>/mypage/myBoardList.do?cp=1" class="asideCategory" >작성한 글</a></li>
	
	       		<li class="list-group-item" id="atag4">
	           		<a href="#pageSubmenu" data-toggle="collapse" aria-expanded="false" aria-haspopup="true" class="asideCategory">개인 정보 관리</a>
	           		<ul class="collapse list-unstyled" id="pageSubmenu">
	               	<%if(mploginMember.getMemberGrade().equals("G")) { %>
	               		<li >
	                   		<a href="<%=request.getContextPath()%>/member/updateMemberForm.do" class="asideCategory"> - 개인 정보 수정</a> 
	               		</li>
	               	<%} else { %>       
	               		<li >
	                   		<a href="<%=request.getContextPath()%>/member/updateSellerForm.do" class="asideCategory"> - 회사 정보 수정</a> 
	               		</li>
	               	<% } %>
	               		<li>
	                   		<a href="<%=request.getContextPath()%>/member/signOutForm.do" class="asideCategory"> - 회원 탈퇴</a>
	               		</li>
	               		<li>
	                   		<a href="<%=request.getContextPath()%>/mypage/replacePwdForm.do" class="asideCategory"> - 비밀번호 변경</a>
	               		</li>
	           		</ul>
	       		</li>
	     	</ul>

	
	<script>
	
		$("#atag1, #atag2, #atag3, #atag4").on({"mouseenter" : function(){
			$(this).css("backgroundColor", "rgb(186, 234, 253)");
		}, "mouseleave" : function(){
			$(this).css("backgroundColor", "rgb(0, 0, 0, 0)");
		}});
	
	</script>
	
	
</body>
</html>