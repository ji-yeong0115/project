<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회사정보수정</title>
    <style>
      input[type="number"]::-webkit-outer-spin-button,
	    input[type="number"]::-webkit-inner-spin-button {
	        -webkit-appearance: none;
	        margin: 0;
	    }
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

		<%
			String[] address = loginMember.getMemberAddress().split(",");
			String[] phone = loginSeller.getCompanyPhone().split("-");
			
		    // 주소 중 일부 내용이 비어있을 경우
	         int aLength = 0;
	         if(address.length != 0) aLength = address.length;
	         
	         if(aLength < 3){
	            String[] newArr = new String[3];
	            
	            for(int i=0;i<aLength ;i++){
	               newArr[i] = address[i];
	            }
	            for(int i=aLength; i< newArr.length ; i++){
	               newArr[i] = "";
	            }
	            address = newArr;
	         }
			
		%>
  <div id="content_main">
        <h2 class="h2">회사 정보 수정</h2>
				<br><br>
        <div class="row">
            <div class="col-md-6 offset-md-3">

                <form method="POST" action="updateSeller.do" class="needs-validation" name="updateSellerForm" onsubmit="return validate();">
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


                    <!-- 이름 -->
                    <div class="row mb-3 form-row">
												<div class="col-md-3">
													<h6>이름(회사명)</h6>
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
                    <!-- 닉네임 -->
                    <div class="row mb-3 form-row">
                        <div class="col-md-3">
                            <label for="nickname">* 닉네임</label>
                        </div>
                        <div class="col-md-6">
                            <input type="text" class="form-control" id="nickname" name="nickname" value="<%=loginMember.getMemberNickname()%>"required>
                        </div>

                        <div class="col-md-6 offset-md-3">
                            <span id="checkNickname">&nbsp;</span>
                        </div>
                    </div>

                    <!-- 이메일 -->



                    <!-- 오픈소스 도로명 주소 API -->
                    <!-- https://www.poesis.org/postcodify/ -->
                    <div class="row mb-3 form-row">
                        <div class="col-md-3">
                            <label for="postcodify_search_button">우편번호</label>
                        </div>
                        <div class="col-md-3">
                            <input type="text" name="post" class="form-control postcodify_postcode5" value="<%=address[2]%>">
                        </div>
                        <div class="col-md-3">
                            <button type="button" class="btn btn-primary" id="postcodify_search_button">검색</button>
                        </div>
                    </div>

                    <div class="row mb-3 form-row">
                        <div class="col-md-3">
                            <label for="address1">도로명 주소</label>
                        </div>
                        <div class="col-md-9">
                            <input type="text" class="form-control postcodify_address" name="address1" id="address1" value="<%=address[0]%>">
                        </div>
                    </div>

                    <div class="row mb-3 form-row">
                        <div class="col-md-3">
                            <label for="address2">상세주소</label>
                        </div>
                        <div class="col-md-9">
                            <input type="text" class="form-control postcodify_details" name="address2" id="address2" value="<%=address[1]%>">
                        </div>
                    </div>

                    <!-- jQuery와 postcodify 를 로딩한다. -->
                    <script src="//d1p7wdleee1q2z.cloudfront.net/post/search.min.js"></script>
                    <script>
                        // 검색 단추를 누르면 팝업 레이어가 열리도록 설정한다.
                        $(function(){
                            $("#postcodify_search_button").postcodifyPopUp();
                        });
                    </script>
                    <!-- 전화번호 -->
						<div class="row mb-3 form-row">
							<div class="col-md-3">
								<label for="phone1">전화번호</label>
							</div>
							<!-- 전화번호1 -->
							<div class="col-md-3">
								<select class="custom-select" id="phone1" name="phone1">
										            <option>010</option>
                                <option>02</option>
                                <option>051</option>
                                <option>053</option>
                                <option>032</option>
                                <option>062</option>
                                <option>042</option>
                                <option>052</option>
                                <option>044</option>
                                <option>031</option>
                                <option>033</option>
                                <option>043</option>
                                <option>041</option>
                                <option>063</option>
                                <option>061</option>
                                <option>054</option>
                                <option>055</option>
                                <option>064</option>
								</select>
							</div>
							<script>
								$.each($("#phone1 > option"),function(index, item){
									// index : 현재 접근중인 인덱스 번호
									// item : 현재 접근중인 요소
									
									if($(item).text() == "<%=phone[0]%>"){
										// 현재 접근한 요소의 내용이
										// phone[0] 인덱스 값과 같다면
										$(item).prop("selected", true);
									
									}
								});
							</script>


							<!-- 전화번호2 -->
							<div class="col-md-3">
								<input type="number" class="form-control phone" id="phone2" name="phone2" maxlength="4" value="<%=phone[1]%>">
							</div>

							<!-- 전화번호3 -->
							<div class="col-md-3">
								<input type="number" class="form-control phone" id="phone3" name="phone3" maxlength="4" value="<%=phone[2]%>">
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
	<script type="text/javascript">
		$(".phone").on("input", function() {
			if ($(this).val().length > $(this).prop("maxLength")) {
				$(this).val($(this).val().slice(0,$(this).prop("maxLength")));
			}
		});
	</script>
		
	<script>
        // 각 유효성 검사 결과를 저장할 객체
        var signUpCheck = { 
					"nickname":true,
				};
 	//********** 실시간 유효성 검사  ************/
			// 정규표현식
			
			// jQuery 변수 : 변수에 직접적으로 jQuery메소드를 사용할 수 있음.
			var $nickname = $("#nickname");
			
			
		
			// 닉네임유효성 검사
			$("#nickname").on("input",function(){
			
				var regExp = /^[가-힣a-zA-Z0-9]{2,10}$/;
				if(!regExp.test($nickname.val())){
					$("#checkNickname").text("유효하지 않은 닉네임 형식입니다.").css("color", "red");
					signUpCheck.nickname = false;
				}else{ 
					$.ajax({
						url : "nicknameDupCheck.do",
						data : {"nickname" : $nickname.val()},
						type : "get",
						success : function(result){
							if(result==0 ){
								$("#checkNickname").text("사용 가능한 닉네임입니다.").css("color", "green");
								signUpCheck.nickname = true;
							}else{
								$("#checkNickname").text("이미 존재하는 닉네임입니다.").css("color", "red");
								signUpCheck.nickname = false;
								
							}
							
						},error : function(){
							console.log("ajax 통신 실패");
						}
									
					});
					
				}
					
			});
		// 전화번호 관련
		 	$(".phone").on("input",function(){
		 		
				// 전화번호 input 태그에 4글자 이상 입력하지 못하게 하는 이벤트
                if ($(this).val().length > $(this).prop("maxLength")){
                    $(this).val($(this).val().slice(0, $(this).prop("maxLength")));
                }
            });
			
			
		 	// 전화번호 유효성 검사
            var regExp1 =  /^\d{3,4}$/; // 숫자 3~4 글자
            var regExp2 =  /^\d{4,4}$/; // 숫자 4 글자
            
            if(!regExp1.test($phone2.val()) || !regExp2.test($phone3.val())){
				singUpCheck.phone3 = false;
            }else{
				singUpCheck.phone3 = true;
			}
			
		function validate(){
			for(var key in signUpCheck){
				if(!signUpCheck[key]){
					
					var msg;
					switch(key){
					case "nickname" : msg="닉네임이"; break;
					}
					
					alert(msg + "유효하지 않습니다.");
					var el = "#"+key;
					$(el).focus();
					return false;
				}
			}
		}
		
    </script>

</body>
</html> 