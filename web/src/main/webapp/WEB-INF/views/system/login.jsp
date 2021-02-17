<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Repetentia | Log in</title>
  <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,400i,700&display=fallback">
  <link rel="stylesheet" href="<c:url value='/static/plugins/fontawesome-free/css/all.min.css'/>">
  <link rel="stylesheet" href="<c:url value='/static/dist/css/adminlte.min.css'/>">
  <link rel="stylesheet" href="<c:url value='/static/plugins/icheck-bootstrap/icheck-bootstrap.min.css'/>">
</head>
<body class="hold-transition login-page">
	<form action="<c:url value="/system/processlogin"/>" method="post">
		<input type="text" name="username">
		<input type="password" name="password">
		<button type="submit">login</button>
	</form>
</body>
</html>