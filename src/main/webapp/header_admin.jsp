<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<f:setLocale value="${sessionScope.lang}"/>
<f:setBundle basename="locale"/>
<header class="p-3 mb-3 border-bottom">
    <div class="container">
        <div class="d-flex flex-wrap align-items-center justify-content-center justify-content-lg-start">
            <ul class="nav col-12 col-lg-auto me-lg-auto mb-2 justify-content-center mb-md-0">

                <li><a href="${pageContext.request.contextPath}/admin?page=1" class="nav-link px-2 link-dark">
                    <f:message key="users_information"/>
                </a></li>
                <li>
                    <ul class="list-group list-group-horizontal">
                        <c:forEach items="${locales}" var="locale">
                            <li class="list-group-item">
                                <a href="?lang=${locale}&${pageContext.request.queryString}">${locale}</a>
                            </li>
                        </c:forEach>
                    </ul>
                </li>

            </ul>

            <div class="dropdown text-end">

                <a href="#" class="d-block link-dark text-decoration-none dropdown-toggle" id="dropdownUser1"
                   data-bs-toggle="dropdown" aria-expanded="false">
                    <jsp:include page="${pageContext.request.contextPath}/greeting.jsp"/>
                    <img src="${pageContext.request.contextPath}/images/account.png" alt=
                    <f:message key="menu"/>>
                </a>
                <ul class="dropdown-menu text-small" aria-labelledby="dropdownUser1">
                    <li><a class="dropdown-item" href="${pageContext.request.contextPath}/unblock_account">
                        <f:message key="unblock"/> <f:message key="account"/>
                    </a></li>
                    <li><a class="dropdown-item" href="${pageContext.request.contextPath}/registration_from_admin">
                        <f:message key="registration"/>
                    </a></li>
                    <li><a class="dropdown-item" href="${pageContext.request.contextPath}/sign_out">
                        <f:message key="singOut"/>
                    </a></li>
                </ul>
            </div>
        </div>
    </div>
</header>
