package com.ibm.stg.pc.sym.si.widget;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.ibm.stg.pc.sym.si.util.Dbconn;
import java.sql.Connection;

public class DataInitialWidget implements Widget {

	Dbconn dbconn = null;
	Connection conn = null;
	Statement stmt1 = null;
	ResultSet rst1 = null;
	
	
	@Override
	public void runWidget(Connection conn) {
		
		verifyWidget();
		
		System.out.println();
		System.out.println("Welcome to coverage data initialize widget.");
		
		final int TEAMSN = 1;
		final int NUM_OF_PEOPLE_FOR_NAW;
		final int NUM_OF_PEOPLE_FOR_HOTLINE = 9;
		final int COVERAGE_OFFICE_HOUR = 1;
		final int COVERAGE_HOTLINE_WEEKDAY = 8;
		final int COVERAGE_HOTLINE_WEEKEND = 9;
		final String YEAR = "2017";
		
		
		
		
		// TODO Auto-generated method stub
		String sql_everyoffice = "insert into sym.coverage (engineersn,coverage,date)"
				+ " select a.sn,"+COVERAGE_OFFICE_HOUR+",b.db_date from sym.td_engineers a,sym.time_dimension b "
				+ "where weekend_flag='f' and holiday_flag='f' and a.team = "+TEAMSN+" and year ="+YEAR;
		
		
		
		String sql_getEngineerSn = "select sn from sym.td_engineers "
				+ "where estatus = 1 and team ="+TEAMSN+" and chotline=1 "
				+ "order by firstname";
		
		try {
			stmt1 = this.conn.createStatement();
			rst1 = stmt1.executeQuery(sql_getEngineerSn);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		System.out.println("Note: You need to run those SQL on MYSQL manually!!!");
		System.out.println();
		
		String sql_hotline1, sql_hotline2;
		String i_sql_hotline1, i_sql_hotline2;
		String sql_naw, i_sql_naw;
		System.out.println("delete from sym.coverage where date >= '2017-01-01'");
		
		try {
			int t = 0;
			for (;rst1.next();){
				sql_hotline1 = "select NULL,"+rst1.getInt(1)+","+COVERAGE_HOTLINE_WEEKDAY+",b.db_date "
						+ "from sym.td_engineers a,sym.time_dimension b "
						+ "where weekend_flag='f' and a.team = "+TEAMSN+" and year = "+YEAR+" and week%"+NUM_OF_PEOPLE_FOR_HOTLINE+"="+t+" and a.sn = "+rst1.getInt(1) +" and b.month <= 12";
				
				sql_hotline2 = "select NULL,"+rst1.getInt(1)+","+COVERAGE_HOTLINE_WEEKEND+",b.db_date "
						+ "from sym.td_engineers a,sym.time_dimension b "
						+ "where weekend_flag='t' and a.team = "+TEAMSN+" and year = "+YEAR+" and week%"+NUM_OF_PEOPLE_FOR_HOTLINE+"="+t+" and a.sn = "+rst1.getInt(1) +" and b.month <= 12";
							
				
				
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
							+ "where weekend_flag='f' and a.team = 1 and year = '"+YEAR+"' and week%(11-2)="+t+" and a.sn = "+rst1.getInt(1) +" and b.month <= 12";
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
		System.out.println("Load to Database: This script will load vemkd performance logs into database");
		if (this.conn == null){
			System.out.println("Database connection is lost, connecting...");
			setDbConnection(conn);
		}
	}

	public void verifyWidget(Connection conn) {
		// TODO Auto-generated method stub
		System.out.println("Verifying this widget...");
		System.out.println("Load to Database: This script will load vemkd performance logs into database");
		if (this.conn == null){
			System.out.println("Database connection is lost, connecting...");
			setDbConnection(conn);
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
	public void setDbConnectionPool(Dbconn _dbconn) {
		// TODO Auto-generated method stub
		this.dbconn = _dbconn;
	}

	public void setDbConnection(Connection conn) {
		// TODO Auto-generated method stub
		this.conn = conn;
	}

	public void setExceptions(){
		String sql_exp[];
		
	}
	
	
	public static void main(){
		
	}
}
