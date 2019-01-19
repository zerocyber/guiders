<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp" %>

<link rel="stylesheet" href="/css/main/joinForm.css">
<script src="/js/main/joinForm.js" defer></script>

<section>
    <div id="join_form">
    <c:choose>
      <c:when test="${guider eq true}">
	      <h2>Guider Join</h2>
      </c:when>
      <c:otherwise>
	      <h2>Follower Join</h2>
      </c:otherwise>
    </c:choose>
      <div>
          <label>이메일</label>
          <input type="text" id="email" placeholder="이메일">
        </div>
      <div>
        <label>이름</label>
        <input type="text" id="mname" placeholder="이름을 입력하세요">
      </div>
      <div>
        <label>비밀번호</label>
        <input type="password" id="password"placeholder="비밀번호를 입력하세요">
      </div>
      <div>
        <label>재입력</label>
        <input type="password" id="repassword"placeholder="비밀번호 재입력">
      </div>
      <div id="phone-wrap">
          <label>전화번호</label>
        <input type="text">―<input type="text">―<input type="text">
        <input type="hidden" id="phone">
      </div>
      <div id="identity">
        <label>주민등록번호</label>
        <input type="text" id="birth">―<input type="text" id="gender" maxlength="1">●●●●●●
      </div>
       <div>
        <label>지역</label>
        <input type="text" id="address" class="inputAddr" readonly />
      </div>
      <c:if test="${guider eq true }">
	      <div id="introdution-wrap">
	        <div>
	          <label>경력란</label>
	        </div>
	        <textarea id="introdution" placeholder="경력 상세 기술"></textarea>
	      </div>
	      <div id="quote-wrap">
	        <div>
	          <label>인용문</label>
	        </div>
	        <textarea id="quote" placeholder="오늘 나는 이렇게 무너진다만, 내일의 나는 무너진다."></textarea>
	      </div>
	      <div id="field">
	        <div>
	          <label>분야</label>
	        </div>
	        <input type="checkbox" id="c1" name="field" value="a"/>
	        <label for="c1"><span></span>취업상담</label>
	        <input type="checkbox" id="c2" name="field" value="b"/>
	        <label for="c2"><span></span>학업조언</label>
	        <input type="checkbox" id="c3" name="field" />
	        <label for="c3"><span></span>자소서첨삭</label>
	        <p></p>
	      </div>
	      <div id="lang">
	        <div>
	          <label>언어</label>
	        </div>
	        <input type="checkbox" id="l1" name="lang" value="Java" />
	        <label for="l1"><span></span>JAVA</label>
	
	        <input type="checkbox" id="l2" name="lang" value="C"/>
	        <label for="l2"><span></span>C</label>
	
	        <input type="checkbox" id="l3" name="lang" value="Python"/>
	        <label for="l3"><span></span>파이썬</label>
	
	        <input type="checkbox" id="l4" name="lang" value="Ruby" />
	        <label for="l4"><span></span>루비</label>
	
	        <input type="checkbox" id="l5" name="lang" value="PHP"/>
	        <label for="l5"><span></span>PHP</label>
	      </div>
      </c:if>
      <div class="clearfix">
        <button type="button" id="join-btn2">확인</button>

      </div>
    </div>
  </section>
  

<%@ include file="../include/footer.jsp" %>