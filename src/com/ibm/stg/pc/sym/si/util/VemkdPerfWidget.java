package com.ibm.stg.pc.sym.si.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ibm.stg.pc.sym.si.widget.Widget;
import com.mysql.jdbc.Connection;

public class VemkdPerfWidget implements Widget {
	Connection conn = null;
	String logurl;
	String pmr;
	String dbtable;
	boolean wrflag;
	
	VemkdPerfWidget(){
		verifyWidget();
		XmlReader xmlReader= new XmlReader();
		dbtable = xmlReader.getFromConf("DBTABLE","src\\conf.xml");
	}
	
	@Override
	public void runWidget() {
		doSomeTest();
	}

	@Override
	public void verifyWidget() {
		// TODO Auto-generated method stub
		System.out.println("Verifying this widget...");
		System.out.println("Load From Database: This script will log vemkd performance logs into database");
	}

	@Override
	public boolean loadWidget() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void getDbConnection(Connection conn1) {
		// TODO Auto-generated method stub
		this.conn = conn1;
		
	}
	
	public void doSomeTest(){
		// TODO Auto-generated method stub
				String sql1 = "SELECT * FROM sym.vemkd where func = 'chanPoll'";
				Statement stmt1 = null;
				ResultSet rst1 = null;
				String para1;
				String para2;
				String para3;

				try {
					stmt1 = conn.createStatement();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					rst1 = stmt1.executeQuery(sql1);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

					try {
						for (;rst1.next();){
							para1 = rst1.getString(1);
							para2 = rst1.getString(2);
							para3 = rst1.getString(3);
							System.out.println("[OUTPUT]"+para1+" "+para2+" "+para3);
						}
						rst1.close();
						stmt1.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				//	allcards.showAllcards();
				//	allcardshash.showAllcards();
		
	}
	
	public void loadFromLog(){
		String str = null;
        FileReader reader = null;
        String[] perfitem;
        String[] perfvalues =new String[16];
		try {
			reader = new FileReader(logurl);
			BufferedReader br = new BufferedReader(reader);
			while((str = br.readLine()) != null) {
				Pattern pattern = Pattern.compile("\\<(.*?)\\>");
				perfitem = str.split(" ");
				if(perfitem.length >= 5	&& perfitem[5].equals("printPerf:")){
					System.out.print(".");
					Matcher matcher = pattern.matcher(str);
					int i = 0;
					while (matcher.find()){
						perfvalues[i] = matcher.group(1);
						i++;
						//System.out.println(matcher.group(1));
					}
					String sql = "INSERT INTO "+dbtable+
                    "(logdate,opcode,totalcnt,totaltime,maxtime,mintime,totalfileio,maxfileio,minfileio,"+
                    "totaliocounter,totalchanopentime,maxchanopentime,minchanopentime,func,PMR)"+
                    " values( SYSDATE(),"+perfvalues[0]+","+perfvalues[1]+","+perfvalues[2]+","+perfvalues[3]+","+perfvalues[4]+","+perfvalues[5]+","+perfvalues[6]+","+perfvalues[7]+","+perfvalues[8]+","+perfvalues[9]+","+perfvalues[10]+","+perfvalues[11]+",'"+perfvalues[12]+"','"+pmr+"')";
					//System.out.println(sql);
					if (wrflag){
						try{
							Statement stmt2 = conn.createStatement();
							stmt2.executeUpdate(sql);
							stmt2.close();
						}catch(SQLException e){
							e.printStackTrace();
						}	
					}
				}
			}
			br.close();
			reader.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("There is an error while loading vemkd performance file, either file is not exist or can't be operated");
			e.printStackTrace();
		}
		System.out.println();
		System.out.println("Job finished");
	}

	public void getInfoFromConsole(){
		Scanner sca = new Scanner(System.in);
		System.out.println("1. Please input log directory, default = c:\\vemkd.log");
		logurl = sca.nextLine();
		if(logurl.equals("")){
			logurl = "c:\\vemkd.log";
		}
		System.out.println("Log url set to ["+logurl+"]");
		
		System.out.println("2. Please input pmr number, default = PMR9999 for general test");
		pmr = sca.nextLine();
		if(pmr.equals("")){
			pmr = "PMR9999";
		}
		System.out.println("PMR set to ["+pmr+"]");
		
		System.out.println("3. Do you want to write to database, default = N");
		String strwrflag = sca.nextLine();
		if(strwrflag.equals("y")){
			wrflag = true;
		}else{
			wrflag = false;
		}
		System.out.println("Write flag set to ["+wrflag+"]");
		
		loadFromLog();
	}
}
