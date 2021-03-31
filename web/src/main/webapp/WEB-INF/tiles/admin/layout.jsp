<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html lang="ko-KR">
<head>
    <meta charset="utf-8">
    <meta http-equiv="Content-Language" content="ko">
    <title></title>
    <tiles:insertAttribute name="css" />
</head>
<body class="hold-transition sidebar-mini">
<div class="wrapper">
    <tiles:insertAttribute name="navbar" />
    <tiles:insertAttribute name="sidebar" />
    <div class="content-wrapper">
        <tiles:insertAttribute name="breadcrumb" />
        <tiles:insertAttribute name="content" />
    </div>
    <tiles:insertAttribute name="footer" />
    <tiles:insertAttribute name="script" />
    <tiles:insertAttribute name="js" />
</div>
</body>
</html>