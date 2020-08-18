<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
</head>
<body>
	<div class="container-fluid">
	<%@ include file="WEB-INF/views/common/header.jsp" %>

	<div class="row" id="content">
  <%@ include file="WEB-INF/views/common/aside.jsp" %>  

  <%@ include file="WEB-INF/views/member/main.jsp" %>  
	</div>
	
	<%@ include file="WEB-INF/views/common/footer.jsp" %>
		</div>
</body>
</html>