package demo;

import java.util.List;

import org.taidu.revived.dbutil.DBUtil;
import org.taidu.revived.dbutil.Record;
import org.taidu.revived.model.DBData;

import entity.Dept;

public class Demo {

	/**
	 * jfinal
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		DBData db=new DBData();
		db.driver="oracle.jdbc.driver.OracleDriver";
		db.url="jdbc:oracle:thin:@localhost:1521:orcl";
		db.uid="accp";
		db.pwd="accp";
		
		
		
		Record re=new Record();
		re.columns.add("deptno");
		re.columns.add("dname");
		re.columns.add("loc");
		
		re.values.add("'11'");
		re.values.add("'haha'");
		re.values.add("'sz'");
		
		
		
		DBUtil dbutil=new DBUtil(db);
		
		int result=dbutil.save("scott.dept", re);
		
		System.out.println(result>0);
		
		/*List<Dept> depts= dbutil.selectList("scott.dept", re, Dept.class);
		for (Dept dept : depts) {
			System.out.println(dept.getDname());
		}*/
	}

}
