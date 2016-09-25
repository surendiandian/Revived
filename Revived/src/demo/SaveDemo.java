package demo;

import org.taidu.revived.dbutil.DBUtil;
import org.taidu.revived.dbutil.Record;
import org.taidu.revived.model.DBData;

public class SaveDemo {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		DBData db=new DBData();
		db.driver="oracle.jdbc.driver.OracleDriver";
		db.url="jdbc:oracle:thin:@localhost:1521:orcl";
		db.uid="accp";
		db.pwd="accp";
		
		
		Record cord=new Record();
		cord.columns.add("deptno");
		cord.columns.add("dname");
		cord.columns.add("loc");
		
		cord.values.add("'1'");
		cord.values.add("'2'");
		cord.values.add("'3'");
		
		
		DBUtil dbutil=new DBUtil(db);
		
		int result=dbutil.save("scott.dept", cord);
		System.out.println(result>0);
		
		
		
		
		
		
	}

}
