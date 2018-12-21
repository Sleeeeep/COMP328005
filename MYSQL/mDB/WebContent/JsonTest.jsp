<%@ page import="org.json.simple.JSONObject" %>
<%@ page import="org.json.simple.JSONArray" %>
<%@ page import="org.json.simple.JSONValue" %>
<%@ page import="org.json.simple.parser.JSONParser" %>
<%@ page import="java.util.*, java.lang.*, java.io.*" %>
<%@ page import="dbpkg.DBQuery"%>

<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>

<%
	request.setCharacterEncoding("utf-8");
	response.setCharacterEncoding("utf-8");
	// request에서 정보 parse
	String str;
	BufferedReader getAndroidData = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
	StringBuffer paramData = new StringBuffer();
	
	while((str = getAndroidData.readLine()) != null)
		paramData.append(str);
	
	JSONParser parser = new JSONParser();
	Object requestObject = parser.parse(paramData.toString());
	JSONObject temp = (JSONObject)requestObject;
	JSONArray test = (JSONArray)(temp.get("query"));
		
	// DB 연결
	DBQuery mDB = DBQuery.getDB();
	if (!mDB.connectDB())
		out.println("DB Connect Fail!");
	else
	{
		// DB 쿼리에 쓰이는 변수들
		ArrayList<String> column = new ArrayList<String>();
		String table = null;
		ArrayList<String> cond = new ArrayList<String>();
		ArrayList<String> value = new ArrayList<String>();
		// JSON 변수들
		JSONArray JArray = new JSONArray();
		JSONObject JObject = new JSONObject();
		
		if(((JSONObject)test.get(0)).get("Type").equals("SELECT"))
		{
			table = (((JSONObject)test.get(0)).get("Table")).toString();
			if(((JSONObject)test.get(0)).containsKey("Col"))
			{
				for(int i=0; i<((JSONArray)((JSONObject)test.get(0)).get("Col")).size(); i++)
					column.add((((JSONArray)((JSONObject)test.get(0)).get("Col")).get(i)).toString());
			}
			if(((JSONObject)test.get(0)).containsKey("Cond"))
			{
				for(int i=0; i<((JSONArray)((JSONObject)test.get(0)).get("Cond")).size(); i++)
					cond.add((((JSONArray)((JSONObject)test.get(0)).get("Cond")).get(i)).toString());
			}
	
			JArray = mDB.selectQuery(column, table, cond);
			JObject.put("response", JArray);
			out.println(JObject);
			System.out.println(JObject);
		}
		else if(((JSONObject)test.get(0)).get("Type").equals("DELETE"))
		{
			table = (((JSONObject)test.get(0)).get("Table")).toString();
			if(((JSONObject)test.get(0)).containsKey("Cond"))
			{
				for(int i=0; i<((JSONArray)((JSONObject)test.get(0)).get("Cond")).size(); i++)
					cond.add((((JSONArray)((JSONObject)test.get(0)).get("Cond")).get(i)).toString());
			}
			JObject.put("response", String.valueOf(mDB.deleteQuery(table, cond)));
			out.println(JObject);
			System.out.println(JObject);
		}
		else if(((JSONObject)test.get(0)).get("Type").equals("INSERT"))
		{
			table = (((JSONObject)test.get(0)).get("Table")).toString();
			if(((JSONObject)test.get(0)).containsKey("Col"))
			{
				for(int i=0; i<((JSONArray)((JSONObject)test.get(0)).get("Col")).size(); i++)
					column.add((((JSONArray)((JSONObject)test.get(0)).get("Col")).get(i)).toString());
			}
			if(((JSONObject)test.get(0)).containsKey("Value"))
			{
				for(int i=0; i<((JSONArray)((JSONObject)test.get(0)).get("Value")).size(); i++)
					value.add((((JSONArray)((JSONObject)test.get(0)).get("Value")).get(i)).toString());
			}
			JObject.put("response", String.valueOf(mDB.insertQuery(column, table, value)));
			out.println(JObject);
			System.out.println(JObject);
		}
		else if(((JSONObject)test.get(0)).get("Type").equals("UPDATE"))
		{
			table = (((JSONObject)test.get(0)).get("Table")).toString();
			if(((JSONObject)test.get(0)).containsKey("Cond"))
			{
				for(int i=0; i<((JSONArray)((JSONObject)test.get(0)).get("Cond")).size(); i++)
					cond.add((((JSONArray)((JSONObject)test.get(0)).get("Cond")).get(i)).toString());
			}
			if(((JSONObject)test.get(0)).containsKey("Value"))
			{
				for(int i=0; i<((JSONArray)((JSONObject)test.get(0)).get("Value")).size(); i++)
					value.add((((JSONArray)((JSONObject)test.get(0)).get("Value")).get(i)).toString());
			}
			JObject.put("response", String.valueOf(mDB.updateQuery(value, table, cond)));
			out.println(JObject);
			System.out.println(JObject);
		}
		else if(((JSONObject)test.get(0)).get("Type").equals("CUSTOM"))
		{
			JArray = mDB.customQuery((((JSONObject)test.get(0)).get("Query")).toString());
			JObject.put("response", JArray);
			out.println(JObject);
			System.out.println(JObject);
		}
		else if(((JSONObject)test.get(0)).get("Type").equals("LOGIN")){
			if(((JSONObject)test.get(0)).containsKey("Cond"))
			{
				for(int i=0; i<((JSONArray)((JSONObject)test.get(0)).get("Cond")).size(); i++)
					cond.add((((JSONArray)((JSONObject)test.get(0)).get("Cond")).get(i)).toString());
			}
	
			JObject = mDB.loginQuery(cond);
			out.println(JObject);
			System.out.println(JObject);
		}
	}
%>