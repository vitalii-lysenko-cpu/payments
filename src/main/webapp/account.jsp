<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<f:setLocale value="${sessionScope.lang}"/>
<f:setBundle basename="locale"/>
<!DOCTYPE html>
<html lang="${sessionScope.lang}">
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
            crossorigin="anonymous"></script>
    <title>AccountInfo</title>
    <style>
        table, th, td {
            border: 1px solid black;
        }
    </style>
</head>
<body>
<jsp:include page="${pageContext.request.contextPath}/header.jsp"/>
<c:if test="${error=='theValueMustNotBeNegative'}">
    <div class="alert alert-danger" role="alert" style="width:50%">
        <f:message key="the_value_must_not_be_negative"/>
    </div>
</c:if>
<div class="container-sm"><f:message key="balance"/> ${balance}</div>
<form method="post" action="${pageContext.request.contextPath}/top_up" style="width:40%">
    <div class="mb-3 form-group col-lg-2">
        <label for="total" class="form-label"><f:message key="total"/>*</label>
        <input id="total" type="number" min="0" step="0.01" class="form-control" name="total" required>
        <div class="invalid-feedback">${requestScope.error}</div>
    </div>
    <input type="hidden" name="accountId" id="id2" value="${pageContext.request.getParameter("id")}">
    <input type="submit" value="<f:message key="topUp"/>" class="btn btn-primary">
</form>
<br>
<h4><f:message key="card_information"/></h4>
<table id="table-accounts" class="table" style="width: 50%">
    <thead>
    <tr>
        <th scope="col"><f:message key="cardNumber"/></th>
        <th scope="col"><f:message key="expiration"/></th>
        <th scope="col"><f:message key="cvc"/></th>

    </tr>
    </thead>
    <tbody>
    <c:forEach items="${cards}" var="card">
        <tr>
            <td>${card.getNumber()}</td>
            <td>${card.getExpiration()}</td>
            <td>${card.getCvc()}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<br>
<h4><f:message key="payments_information"/></h4>
<table id="table-payments" class="table" style="width: 50%">
    <thead>
    <tr>
        <th scope="col">
            <c:choose>
                <c:when test="${sortOrder.equalsIgnoreCase(\"DESC\")}">
                    <a href="${pageContext.request.contextPath}/account?sortBy=id&page=1&id=${pageContext.request.getParameter("id")}&sortOrder=ASC">
                        <f:message key="number"/></a>
                </c:when>
                <c:otherwise>
                    <a href="${pageContext.request.contextPath}/account?sortBy=id&page=1&id=${pageContext.request.getParameter("id")}&sortOrder=DESC">
                        <f:message key="number"/></a>
                </c:otherwise>
            </c:choose>
        </th>
        <th scope="col"><f:message key="amount"/></th>
        <th scope="col"><f:message key="status"/></th>
        <th scope="col">
            <c:choose>
                <c:when test="${sortOrder.equalsIgnoreCase(\"DESC\")}">
                    <a href="${pageContext.request.contextPath}/account?sortBy=date&page=1&id=${pageContext.request.getParameter("id")}&sortOrder=ASC">
                        <f:message key="dateCreated"/></a>
                </c:when>
                <c:otherwise>
                    <a href="${pageContext.request.contextPath}/account?sortBy=date&page=1&id=${pageContext.request.getParameter("id")}&sortOrder=DESC">
                        <f:message key="dateCreated"/></a>
                </c:otherwise>
            </c:choose></th>

    </tr>
    </thead>
    <tbody>
    <c:forEach items="${payments}" var="payment">
        <tr>
            <td>${payment.getId()}
                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="red" class="bi bi-file-pdf-fill">
                    <a href="${pageContext.request.contextPath}/create_pdf?id=${payment.getId()}">
                        <path d="M5.523 10.424c.14-.082.293-.162.459-.238a7.878 7.878 0 0 1-.45.606c-.28.337-.498.516-.635.572a.266.266 0 0 1-.035.012.282.282 0 0 1-.026-.044c-.056-.11-.054-.216.04-.36.106-.165.319-.354.647-.548zm2.455-1.647c-.119.025-.237.05-.356.078a21.035 21.035 0 0 0 .5-1.05 11.96 11.96 0 0 0 .51.858c-.217.032-.436.07-.654.114zm2.525.939a3.888 3.888 0 0 1-.435-.41c.228.005.434.022.612.054.317.057.466.147.518.209a.095.095 0 0 1 .026.064.436.436 0 0 1-.06.2.307.307 0 0 1-.094.124.107.107 0 0 1-.069.015c-.09-.003-.258-.066-.498-.256zM8.278 4.97c-.04.244-.108.524-.2.829a4.86 4.86 0 0 1-.089-.346c-.076-.353-.087-.63-.046-.822.038-.177.11-.248.196-.283a.517.517 0 0 1 .145-.04c.013.03.028.092.032.198.005.122-.007.277-.038.465z"></path>
                        <path fill-rule="evenodd" fill="red"
                              d="M4 0h8a2 2 0 0 1 2 2v12a2 2 0 0 1-2 2H4a2 2 0 0 1-2-2V2a2 2 0 0 1 2-2zm.165 11.668c.09.18.23.343.438.419.207.075.412.04.58-.03.318-.13.635-.436.926-.786.333-.401.683-.927 1.021-1.51a11.64 11.64 0 0 1 1.997-.406c.3.383.61.713.91.95.28.22.603.403.934.417a.856.856 0 0 0 .51-.138c.155-.101.27-.247.354-.416.09-.181.145-.37.138-.563a.844.844 0 0 0-.2-.518c-.226-.27-.596-.4-.96-.465a5.76 5.76 0 0 0-1.335-.05 10.954 10.954 0 0 1-.98-1.686c.25-.66.437-1.284.52-1.794.036-.218.055-.426.048-.614a1.238 1.238 0 0 0-.127-.538.7.7 0 0 0-.477-.365c-.202-.043-.41 0-.601.077-.377.15-.576.47-.651.823-.073.34-.04.736.046 1.136.088.406.238.848.43 1.295a19.707 19.707 0 0 1-1.062 2.227 7.662 7.662 0 0 0-1.482.645c-.37.22-.699.48-.897.787-.21.326-.275.714-.08 1.103z"></path>
                    </a>
                </svg>

            </td>
            <td>${payment.getAmount()}</td>
            <td>${payment.getStatus()}</td>
            <td>${payment.getDateCreated()}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<nav aria-label="Page navigation">
    <ul class="pagination">
        <c:forEach var="i" begin="1" end="${numberOfPages}" step="1">
            <li class="page-item <c:if test="${i == page}">active</c:if>">
                <a class="page-link"
                   href="${pageContext.request.contextPath}/account?sortBy=${sortBy}&sortOrder=${sortOrder}&id=${pageContext.request.getParameter("id")}&page=${i}">${i}</a>
            </li>
        </c:forEach>
    </ul>
</nav>
</body>
</html>
