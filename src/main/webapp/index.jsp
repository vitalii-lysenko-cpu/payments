<%@page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
            crossorigin="anonymous"></script>
</head>
<body>
<div class="container">
    <form class="form-horizontal needs-validation" id="loginform" role="form" method="post"
          action="${pageContext.request.contextPath}/login" novalidate>
        <div class="mb-3 form-group col-lg-2">
            <label for="login-username" class="form-label">Email*</label>
            <input id="login-username" type="email" class="form-control" name="email" required>
            <div class="invalid-feedback">${requestScope.error}</div>
        </div>

        <div class="mb-3 form-group col-lg-2">
            <label for="login-password" class="form-label">Password*</label>
            <input id="login-password" type="password" class="form-control" name="password" minlength="8" required>
            <div class="invalid-feedback">${requestScope.error}</div>
        </div>

<%--        <div class="alert alert-danger d-flex align-items-center col-lg-2" role="alert">--%>
<%--            <div>--%>
<%--                ${requestScope.error}--%>
<%--            </div>--%>
<%--        </div>--%>

        <input type="submit" value="Login" class="btn btn-primary">
        <a href="${pageContext.request.contextPath}/registration">     Not have account</a>
    </form>
</div>
</body>
</html>