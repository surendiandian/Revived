package org.taidu.revived.dbutil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

import org.taidu.revived.model.DBData;

public class DBUtil {

	private Connection con;

	public DBUtil(DBData db) throws Exception {
		super();
		Class.forName(db.driver);
		
		this.con = DriverManager.getConnection(db.url,db.uid,db.pwd);
	}

	public int save(String tableName, Record record) throws Exception {
		return record.save(tableName, this.con);
	}

	public int delete(String tableName, Record record) throws Exception{
		return record.delete(tableName, con);
	}
	
	public int update(String tableName,Record record) throws Exception{
		return record.update(tableName, con);
	}
	
	public <T>T selectOne(String tableName,Record record,Class<T> modelClass) throws Exception{
		return record.selectOne(tableName, con, modelClass);
	}
	
	public <T>List<T> selectList(String tableName,Record record,Class<T> modelClass) throws Exception{
		return record.selectList(tableName, con, modelClass);
	}
	
}
