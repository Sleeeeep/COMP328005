<%@ page import="org.json.simple.JSONObject" %>
<%@ page import="org.json.simple.JSONArray" %>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>

<%
	request.setCharacterEncoding("utf-8");
	String[] getAndroidData = { "", "" };
	getAndroidData[0] = request.getParameter("hey");
	getAndroidData[1] = request.getParameter("really");
	System.out.println(getAndroidData[0] + " " + getAndroidData[1]);
	
	JSONObject jsonMain = new JSONObject();
	JSONArray jArray = new JSONArray();
	JSONObject jObject = new JSONObject();
	jObject.put("hey", "you_SUCK!!!!");
	jObject.put("really", getAndroidData[0] + getAndroidData[1]);
	jArray.add(0, jObject);
	jsonMain.put("dataSend", jArray);
	
	out.println(jsonMain);
%>