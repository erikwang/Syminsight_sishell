package com.ibm.stg.pc.sym.si.widget;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.ibm.stg.pc.sym.si.util.Dbconn;
import com.mysql.jdbc.Connection;

public class DataInitialWidget implements Widget {

	Dbconn dbconn = null;
	Connection conn = null;
	Statement stmt1 = null;
	ResultSet rst1 = null;
	
	
	@Override
	public void runWidget() {
		
		verifyWidget();
		
		System.out.println();
		System.out.println("Welcome to coverage data initialize widget.");
		
		// TODO Auto-generated method stub
		String sql_everyoffice = "insert into sym.coverage (engineersn,coverage,date)"
				+ " select a.sn,1,b.db_date from sym.td_engineers a,sym.time_dimension b "
				+ "where weekend_flag='f' and holiday_flag='f' and a.team = 1 and year = '2016'";
		
		
		
		String sql_getEngineerSn = "select sn from sym.td_engineers "
				+ "where estatus = 1 and team =1 and chotline=1 "
				+ "order by firstname";
		
		try {
			stmt1 = conn.createStatement();
			rst1 = stmt1.executeQuery(sql_getEngineerSn);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		

		
		String sql_hotline1, sql_hotline2;
		String i_sql_hotline1, i_sql_hotline2;
		String sql_naw, i_sql_naw;
		System.out.println("delete from sym.coverage where date >= '2016-01-01'");
		
		try {
			int t = 0;
			for (;rst1.next();){
				sql_hotline1 = "select NULL,"+rst1.getInt(1)+",8,b.db_date "
						+ "from sym.td_engineers a,sym.time_dimension b "
						+ "where weekend_flag='f' and a.team = 1 and year = '2016' and week%11="+t+" and a.sn = "+rst1.getInt(1) +" and b.month <= 12";
				
				sql_hotline2 = "select NULL,"+rst1.getInt(1)+",9,b.db_date "
						+ "from sym.td_engineers a,sym.time_dimension b "
						+ "where weekend_flag='t' and a.team = 1 and year = '2016' and week%11="+t+" and a.sn = "+rst1.getInt(1) +" and b.month <= 12";
							
				
				
				i_sql_hotline1 = "insert into sym.coverage (sn,engineersn,coverage,date) "+ sql_hotline1+";";
				i_sql_hotline2 = "insert into sym.coverage (sn,engineersn,coverage,date) "+ sql_hotline2+";";
				
				
				t++;
				System.out.println(i_sql_hotline1);
				System.out.println(i_sql_hotline2);
				System.out.println();
			}
			
			t = 0;
			rst1.afterLast();
			for (;rst1.previous();){
				//System.out.println("engineer sn = "+rst1.getInt(1));
				if (rst1.getInt(1) != 14 && rst1.getInt(1) != 21){ //exclude Mark and Geoff for NAW
					sql_naw = "select NULL,"+rst1.getInt(1)+",3,b.db_date "
							+ "from sym.td_engineers a,sym.time_dimension b "
							+ "where weekend_flag='f' and a.team = 1 and year = '2016' and week%(11-2)="+t+" and a.sn = "+rst1.getInt(1) +" and b.month <= 12";
					i_sql_naw = "insert into sym.coverage (sn,engineersn,coverage,date) "+ sql_naw+";";
					t++;
					System.out.println(i_sql_naw);
				}
			}
			rst1.close();
			stmt1.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void verifyWidget() {
		// TODO Auto-generated method stub
		System.out.println("Verifying this widget...");
		if (this.conn == null){
			System.out.println("Database connection is lost, connecting...");
			this.conn = dbconn.getConn();
		}
	}

	@Override
	public boolean loadWidget() {
		// TODO Auto-generated method stub
		return false;
	}


	
	public void executeWithoutReturn (String sql){

		try {
			stmt1.executeQuery(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public ResultSet executeWithReturn(String sql){
		try {
			rst1 = stmt1.executeQuery(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rst1;
	}


	@Override
	public void setDbConnection(Dbconn _dbconn) {
		// TODO Auto-generated method stub
		this.dbconn = _dbconn;
	}
	
	public void setExceptions(){
		String sql_exp[];
		
	}
	
}
