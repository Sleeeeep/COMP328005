package dbpkg;

import java.sql.*;
import java.util.*;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;

public class DBQuery { 	
	private static DBQuery mDB = new DBQuery();

	public DBQuery() {

	}

	public static DBQuery getDB() {
		return mDB;
	}

	private static Connection conn;

	private String Id = "root";
	private String Pw = "1234";
	private String url = "jdbc:mysql://localhost/MOAPP?useSSL=false&user=" + Id + "&password=" + Pw;
	// allowPublicKeyRetrieval=true

	private PreparedStatement pstmt;
	private ResultSet rs;
	private ResultSetMetaData rsmd;

	public boolean connectDB() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public JSONArray selectQuery(ArrayList<String> column, String table, ArrayList<String> cond) {
		JSONArray jArray = new JSONArray();
		
		if (table == null) {
			System.err.println("select : no table");
			return jArray;
		}

		StringBuffer query = new StringBuffer();
		query.append("SELECT FROM " + table);

		if (column.size() == 0)
			query.insert(7, "* ");
		else {
			for (int i = column.size() - 1; i >= 0; i--) {

				if (i != column.size() - 1)
					query.insert(7, column.get(i) + ", ");
				else
					query.insert(7, column.get(i) + " ");
			}
		}

		if (cond.size() != 0) {
			query.append(" WHERE ");
			for (int i = 0; i < cond.size(); i++) {
				if (i != cond.size() - 1)
					query.append(cond.get(i) + " AND ");
				else
					query.append(cond.get(i));
			}
		}
		System.out.println(query);
		try {
			pstmt = conn.prepareStatement(query.toString());
			rs = pstmt.executeQuery();
			rsmd = rs.getMetaData();
			int colNum = rsmd.getColumnCount();
			
			while (rs.next()) {
				JSONObject jObject = new JSONObject();
				
				for (int i = 1; i <= colNum; i++) {
					jObject.put(rsmd.getColumnName(i), rs.getString(i));
				}
				jArray.add(jObject);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return jArray;
	}

	public JSONArray customQuery(String query) {
		JSONArray jArray = new JSONArray();
		System.out.println(query);
		try {
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();
			rsmd = rs.getMetaData();
			int colNum = rsmd.getColumnCount();
			
			while (rs.next()) {
				JSONObject jObject = new JSONObject();
				
				for (int i = 1; i <= colNum; i++) {
					jObject.put(rsmd.getColumnName(i), rs.getString(i));
				}
				jArray.add(jObject);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return jArray;
	}
	
	public int insertQuery(ArrayList<String> column, String table, ArrayList<String> value) {
		int result = 0;

		if (table == null) {
			System.err.println("insert : no table");
			return -1;
		}

		StringBuffer query = new StringBuffer();
		query.append("INSERT INTO " + table);

		if (column.size() != 0) {
			query.append("(");
			for (int i = 0; i < column.size(); i++) {
				if (i == column.size() - 1)
					query.append(column.get(i) + ")");
				else
					query.append(column.get(i) + ", ");
			}
		}

		if (value.size() == 0) {
			System.err.println("insert : no values");
			return -2;
		}
		query.append(" VALUES (");
		for (int i = 0; i < value.size(); i++) {
			if (i == value.size() - 1)
				query.append(value.get(i) + ")");
			else
				query.append(value.get(i) + ", ");
		}
		System.out.println(query);

		try {
			pstmt = conn.prepareStatement(query.toString());
			result = pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
			return -3;
		}
		return result;
	}

	public int deleteQuery(String table, ArrayList<String> cond) {
		int result = 0;

		if (table == null) {
			System.err.println("delete : no table");
			return -1;
		}

		StringBuffer query = new StringBuffer();
		query.append("DELETE FROM " + table);

		if (cond.size() != 0) {
			query.append(" WHERE ");
			for (int i = 0; i < cond.size(); i++) {
				if (i != cond.size() - 1)
					query.append(cond.get(i) + " AND ");
				else
					query.append(cond.get(i));
			}
		}
		System.out.println(query);

		try {
			pstmt = conn.prepareStatement(query.toString());
			result = pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
			return -2;
		}
		return result;	
	}

	public int updateQuery(ArrayList<String> value, String table, ArrayList<String> cond) {
		int result = 0;

		if (table == null) {
			System.err.println("insert : no table");
			return -1;
		}

		StringBuffer query = new StringBuffer();
		query.append("UPDATE " + table);

		if (value.size() == 0) {
			System.err.println("insert : no values");
			return -2;
		}
		query.append(" SET ");
		for (int i = 0; i < value.size(); i++) {
			if (i == value.size() - 1)
				query.append(value.get(i));
			else
				query.append(value.get(i) + ", ");
		}
		
		if (cond.size() != 0) {
			query.append(" WHERE ");
			for (int i = 0; i < cond.size(); i++) {
				if (i != cond.size() - 1)
					query.append(cond.get(i) + " AND ");
				else
					query.append(cond.get(i));
			}
		}
		System.out.println(query);

		try {
			pstmt = conn.prepareStatement(query.toString());
			result = pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
			return -3;
		}
		return result;
	}
}
