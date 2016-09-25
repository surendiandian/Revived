package demo;

import org.taidu.revived.dbutil.DBUtil;
import org.taidu.revived.dbutil.Record;
import org.taidu.revived.model.DBData;

import entity.Dept;

public class SelectOneDemo {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		DBData db=new DBData();
		db.driver="oracle.jdbc.driver.OracleDriver";
		db.url="jdbc:oracle:thin:@localhost:1521:orcl";
		db.uid="accp";
		db.pwd="accp";
		
		Record re=new Record();
		
		re.columns.add("*");
		
		re.wheres.add(" deptno=1");
		
		
		DBUtil dbutil=new DBUtil(db);
		Dept dept=dbutil.selectOne("scott.dept", re, Dept.class);
		
		System.out.println(dept.getDname());
	}

}
