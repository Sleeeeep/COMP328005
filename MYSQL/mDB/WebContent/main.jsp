<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>

<%@ page import="java.lang.*, java.util.*"%>
<%@ page import="dbpkg.DBQuery"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>DB TEST</title>
</head>
<body>
	<%
		DBQuery mDB = DBQuery.getDB();

		if (mDB.connectDB())
			System.out.println("Connect Success!");
		else
			System.out.println("Connect Fail!");
		
		ArrayList<String> column = new ArrayList<String>();
		String table = null;
		ArrayList<String> cond = new ArrayList<String>();
		ArrayList<String> value = new ArrayList<String>();
		
		table = "mUSER";
		
		out.println(mDB.updateQuery(value, table, cond));
	%>
</body>
</html>