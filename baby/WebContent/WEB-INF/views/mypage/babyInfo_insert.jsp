<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

<title>아기 정보 등록</title>

<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">

	<style>
	
    @font-face { font-family: 'ImcreSoojin'; src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_20-04@2.3/ImcreSoojin.woff') 
    format('woff'); font-weight: normal; font-style: normal; }          
          
     #content-main {
       width: 80%;
     }
     
     #title{
     font-family: 'ImcreSoojin';
     margin-top: 20px;
     }
     
     .contentMainBtn{
       float: right; 
       margin-right: 15px;
     }   
     
     .marginBot{
       margin-bottom: 20px;
     }
     
     #divPosition{
         position: relative;
         padding-left: 15px;
         padding-right: 15px;
     }
     
     #babyImgArea{
         width: 400px; 
         height: 400px; 
         margin: auto;
         border: 1px solid black;
     }
     
     #imgSpan{
       position:absolute;
       top:50%;left:50%;
       transform: translate(-50%, -50%)
     }
     
     .mypageContent{
         float: left;
     }
     
     #babyTable{
         width: 410px;
         height: 300px;
         margin-top: 50px;
     }
     
     .table-td{
         text-align: left;
     }
     
     .borderBox{
         width: 100%;
     }
     
	
	</style>

</head>
<body>
	
	<div class="container-fluid">
		<%@ include file="../common/header.jsp" %>
		<!-- content-fluid & header -->
		
		<div class="row" id="content">
			<%@ include file="../common/aside.jsp" %>
			<!-- content & aside -->
			
				<div id="content-main">
				<!-- content-main -->
				
					<div class="row">
                    	<div class="col-md-12">
                        	<h2 id="title">우리 아이 정보</h2>
                        </div>
                    </div>
                    
                    <!-- 아이정보 수정 및 추가 -->
                    <form action="#" method="post">
                    	<div class="row marginBot">
                        	<div class="col-md-12">
	                            <button type="submit" class="btn btn-primary contentMainBtn">확인</button> 
                        	</div>
                      	</div>
                      
                      	<!-- 아이 사진 등록 -->
                      	<div class="row marginBot" id="divPosition">
                        	<div class="col-md-6 mypageContent">
                            	<div id="babyImgArea">
                                	<span id="imgSpan">아이 사진을 등록해주세요.</span>
                            	</div>
                        	</div>
                        	<div class="col-md-6 mypageContent" >
                        	
                        		<!-- 아이 정보 테이블 -->
                          		<table id="babyTable">
            
                                    <tr>
                                        <th>이름 : </th>
                                        <td><input type="text" name="babyName" class="borderBox form-control"></td>
                                        <td></td>
                                    </tr>
                                    
                                    <tr>
                                        <th>생년월일 : </th>
                                        <td><input type="text" id="babyBirth" class="borderBox form-control"></td>
                                        <td><span class="font-text" id="inputAge">만 0세</span></td>
                                    </tr>
            
                                    <tr>
                                        <th>성별 : </th>
                                        <td>
                                          <div class="form-check form-check-inline">
                                            <input class="form-check-input" type="radio" name="inlineRadioOptions" id="inlineRadio1" value="option1">
                                            <label class="form-check-label font-text" for="inlineRadio1">남아</label> &nbsp;&nbsp;
                                          </div>
                                          <div class="form-check form-check-inline">
                                            <input class="form-check-input" type="radio" name="inlineRadioOptions" id="inlineRadio2" value="option2">
                                            <label class="form-check-label font-text" for="inlineRadio2">여아</label>
                                          </div>
                                        </td>
                                        <td></td>
                                    </tr>
            
                                    <tr>
                                        <th>키 : </th>
                                        <td><input type="number" name=babyHeight class="borderBox form-control"></td>
                                        <td class="font-text">cm</td>
                                    </tr>
            
                                    <tr>
                                        <th>몸무게 : </th>
                                        <td><input type="number" name="babyWeight" class="borderBox form-control"></td>
                                        <td class="font-text">kg</td>
                                    </tr>
            
                                </table>
                        	</div>
                      	</div>
                    </form>
			<!-- content-main 끝 -->
			</div>
			
		<!-- content 끝 -->
		</div>
		
		<!-- footer -->
		<%@ include file="../common/footer.jsp" %>
		
	<!-- container-fluid 끝 -->
	</div>
	
	<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
	<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js" integrity="sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI" crossorigin="anonymous"></script>

</body>
</html>