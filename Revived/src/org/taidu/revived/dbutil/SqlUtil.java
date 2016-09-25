package org.taidu.revived.dbutil;

public class SqlUtil {

	public static String getSelectStr(String columns,String table){
		return "select "+columns+" from "+table;
	}
	
	
	public static String getSelectStr(String columns,String table,String wheres){
		return "select "+columns+" from "+table+" where 1=1 "+wheres;
	}
	
	
	
	public static String getDeleteStr(String table,String wheres){
		return "delete "+table+" where "+wheres;
	}
	
	
	public static String getUpdateStr(String table,String columns,String wheres){
		return "update set "+columns+" where "+wheres;
	}
	
	public static String getInsertStr(String table,String columns,String values){
		return "insert into "+table+" ("+columns+")"+" values("+values+")";
		
	}
}
