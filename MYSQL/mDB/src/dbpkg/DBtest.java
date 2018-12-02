package dbpkg;

import dbpkg.DBQuery;
import java.util.*;

public class DBtest {

	public static void main(String[] args) {
		DBQuery mDB = DBQuery.getDB();
		
		if(mDB.connectDB())
			System.out.println("Connect Success!");
		else
			System.out.println("Connect Fail!");
		
		ArrayList<String> column = new ArrayList<String>();
		String table = null;
		ArrayList<String> cond = new ArrayList<String>();
		ArrayList<String> value = new ArrayList<String>();
		
		table = "mUSER";
//		mDB.selectQuery(column, table, cond);
		
//		column.add("Id");
//		column.add("Pw");
//		column.add("Sid");
//		column.add("Name");
//		column.add("Nick");
//		value.add("'test3'");
//		value.add("'1234'");
//		value.add("'4416718722'");
//		value.add("'test3'");
//		value.add("'test3'");
//		// 1: success, -1 : no table name, -2 : no values, -3 : sql error 
//		System.out.println(mDB.insertQuery(column, table, value));
		
		
		cond.add("Nick LIKE 'test%'");
//		>=0 : success, -1 : no table name, -2 : sql error
//		System.out.println(mDB.deleteQuery(table, cond));
		
//		>=0 : success, -1 : no table name, -2 : no value, -3 : sql error
		value.add("Name='123'");
		value.add("Pw='asdf'");
		
		System.out.println(mDB.updateQuery(value, table, cond));
		
		column.clear();
		table = null;
		cond.clear();
		value.clear();
	}

}
