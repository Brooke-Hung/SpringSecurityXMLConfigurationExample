<%@ include file="/WEB-INF/jsp/include/taglibs.jsp" %>
<div class="banner top_nav">
	<a id="menuItemHeader" href="<c:url value='/'/>"><spring:message code="homepage.menu.item.homepage"/></a>			
	<sec:authorize access="hasRole('USER')">
		<a id="menuItemPersonalCenter" href="<c:url value='/personal-center/messages'/>"><spring:message code="homepage.menu.item.personalCenter"/></a>
	</sec:authorize>
	<sec:authorize access="hasRole('ADMIN')">
		<a id="menuItemAdminConsole" href="<c:url value='/admin-console/messages'/>"><spring:message code="homepage.menu.item.adminConsole"/></a>
	</sec:authorize>
	<sec:authorize access="hasRole('OPERATOR')">
		<a id="menuItemOperationCenter" href="<c:url value='/operation-center/messages'/>"><spring:message code="homepage.menu.item.operationCenter"/></a>
	</sec:authorize>
	<span class="user-auth">
		<sec:authorize access="isAnonymous()">
			<a id="menuItemLogin" href="<c:url value='/account-management/loginViaPassword'/>">
				<spring:message code="homepage.menu.item.login"/>
			</a>
		</sec:authorize>
		<sec:authorize access="isAuthenticated()">
			<spring:message code="homepage.message.welcome" 
				arguments="${pageContext.request.userPrincipal.principal.firstName},${pageContext.request.userPrincipal.principal.lastName}"/>
				<c:url value="/account-management/logout" var="logoutAction"/>
				<form:form id="logoutForm" action="${logoutAction}" method="POST">
					<a id="menuItemLogin" href="#" onclick="document.getElementById('logoutForm').submit();"><spring:message code="homepage.menu.item.logout"/></a>
				</form:form>
		</sec:authorize>
	</span>
</div>