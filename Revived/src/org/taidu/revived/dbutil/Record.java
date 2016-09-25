package org.taidu.revived.dbutil;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Record {

	public List<String> columns = new ArrayList<String>();

	public List<String> wheres = new ArrayList<String>();

	public List<String> values = new ArrayList<String>();

	public int save(String tableName, Connection con) throws Exception {

		String columnStr = this.getColumnsStr(this.columns);
		String values = this.getValueStr(this.values);
		String sql = SqlUtil.getInsertStr(tableName, columnStr, values);
		PreparedStatement ps = con.prepareStatement(sql);
		int result = ps.executeUpdate();
		ps.close();
		return result;
	}

	public int delete(String tableName, Connection con) throws Exception {
		String whereStr = this.getWereStr(this.wheres);
		String sql = SqlUtil.getDeleteStr(tableName, whereStr);
		PreparedStatement ps = con.prepareStatement(sql);
		int result = ps.executeUpdate();
		ps.close();
		return result;
	}

	/*
	 * 获得泛型的单个对象
	 */
	public <T> T selectOne(String tableName, Connection con, Class<T> modelClass)
			throws Exception {
		//获得要查询的列
		String columnStr = this.getColumnsStr(this.columns);
		//获得要查询的条件
		String whereStr = this.getWereStr(wheres);
		//判断是否带条件，如果带条件，就获得带条件的语句，否则就获得普通查询语句
		String sql = "";
		if (whereStr == null || whereStr.trim().length() < 1) {
			sql = SqlUtil.getSelectStr(columnStr, tableName);
		} else {
			sql = SqlUtil.getSelectStr(columnStr, tableName, whereStr);
		}
		
		
		
		PreparedStatement ps = con.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		
		
		T model = null;
		if (rs != null && rs.next()) {
			model = modelClass.newInstance();//获得真实需要的对象
			ResultSetMetaData rsmd = rs.getMetaData();// rs的结构信息
			int count = rsmd.getColumnCount();			//获得列的数量
			Method[] methods = modelClass.getDeclaredMethods();//获得该类中声明的所有方法
			for (int i = 1; i <= count; i++) {
				String columnName = rsmd.getColumnName(i);//获得每一列的列名
				
				String setMethodName = "set" + columnName.substring(0, 1)
						+ columnName.substring(1).toLowerCase();//根据列名获得set方法名
				
				//遍历所有方法，并且判断该方法和约定方法名是否一致，如果一致就调用，赋值 
				for (Method method : methods) {
					if (method.getName().equals(setMethodName)) {
						// 获取set参数类型
						Class<?> clazz = method.getParameterTypes()[0];
						// 获取类名 去掉包名
						String className = clazz.getName().substring(
								clazz.getName().lastIndexOf('.') + 1);
						// 根据类名获取ResultSet的get方法
						Method rsGetMethod = ResultSet.class.getMethod("get"
								+ className, String.class);
						// 执行ResultSet得get方法得到字段值
						Object val = rsGetMethod.invoke(rs, columnName);
						// 判断得到值是否为空
						if (null != val) {
							// 执行实体类的set方法，赋值
							method.invoke(model, val);
						}
					}
				}
			}
		}
		return model;
	}

	public <T> List<T> selectList(String tableName, Connection con,
			Class<T> modelClass) throws Exception {
		String columnStr = this.getColumnsStr(this.columns);
		String whereStr = this.getWereStr(wheres);
		String sql = "";
		if (whereStr == null || whereStr.trim().length() < 1) {
			sql = SqlUtil.getSelectStr(columnStr, tableName);
		} else {
			sql = SqlUtil.getSelectStr(columnStr, tableName, whereStr);
		}
		PreparedStatement ps = con.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		List<T> modelList = null;
		if (rs != null) {
			modelList = new ArrayList<T>();
			while (rs.next()) {
				T model = modelClass.newInstance();
				ResultSetMetaData rsmd = rs.getMetaData();// rs读取
				int count = rsmd.getColumnCount();
				Method[] methods = modelClass.getDeclaredMethods();
				for (int i = 1; i <= count; i++) {
					String columnName = rsmd.getColumnName(i);
					String setMethodName = "set" + columnName.substring(0, 1)
							+ columnName.substring(1).toLowerCase();
					for (Method method : methods) {
						if (method.getName().equals(setMethodName)) {
							// 获取set参数类型
							Class<?> clazz = method.getParameterTypes()[0];
							// 获取类名 去掉包名
							String className = clazz.getName().substring(
									clazz.getName().lastIndexOf('.') + 1);
							// 根据类名获取ResultSet的get方法
							Method rsGetMethod = ResultSet.class.getMethod("get"
									+ className, String.class);
							// 执行ResultSet得get方法得到字段值
							Object val = rsGetMethod.invoke(rs, columnName);
							// 判断得到值是否为空
							if (null != val) {
								// 执行实体类的set方法，赋值
								method.invoke(model, val);
							}
						}
					}
				}
				modelList.add(model);
			}

		}
		return modelList;
	}

	public int update(String tableName, Connection con) throws Exception {
		String columnStr = this.getColumnsStr(this.columns);
		String whereStr = this.getWereStr(this.wheres);
		String sql = SqlUtil.getUpdateStr(tableName, columnStr, whereStr);
		PreparedStatement ps = con.prepareStatement(sql);
		int result = ps.executeUpdate();
		ps.close();
		return result;
	}

	public static void main(String[] args) {
		List<String> columns = new ArrayList<String>();
		columns.add("name=1");
		columns.add("age=2");
		columns.add("id=3");
		System.out.println(columns.toString().replace("[", "").replace("]", "")
				.replace(",", " and "));
	}

	private String getColumnsStr(List<String> columns) {
		return columns.toString().replace("[", "").replace("]", "");
	}

	private String getWereStr(List<String> wheres) {

		return wheres == null || wheres.size() < 1 ? "" : " and "
				+ wheres.toString().replace("[", "").replace("]", "")
						.replace(",", " and ");
	}
	
	
	
	private String getValueStr(List<String> values){
		return values.toString().replace("[", "").replace("]", "");
	}
}
