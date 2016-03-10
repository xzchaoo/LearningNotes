<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>My JSP 'login.jsp' starting page</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">

</head>

<body>
	<s:form action="login" method="post">
		<s:textfield type="text" name="username" value="xzc" key="username"/>
		<s:textfield type="password" name="password" value="xzc" key="password"/>
		<s:textfield type="text" name="sureCode" value="" key="sureCode"/>
		<s:textfield type="submit" value="提交" />
		<s:textfield type="reset" value="重置" />
	</s:form>
</body>
</html>
