package com.ibm.stg.pc.sym.si.widget;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.LinkedList;
import java.sql.Connection;


import com.ibm.stg.pc.sym.si.util.Dbconn;
import com.ibm.stg.pc.sym.si.util.XmlReader;


public class VemkdPerfWidget implements Widget {
	Dbconn dbconn = null;
	Connection conn = null;
	String logurl;
	String defaultlogurl;
	String defaultpmrnum;
	String pmr;
	String dbtable;
	boolean wrflag;
	boolean owrflag;

	//For Windows
	//String _confFile = "conf\\conf.xml";
	
	//For Linux
	String _confFile = "./conf/conf_vemkdgen.xml";
	
	
	public VemkdPerfWidget(){
		//verifyWidget();
		XmlReader xmlReader= new XmlReader();
		dbtable = xmlReader.getFromConf("DB_TABLE",_confFile);
		defaultlogurl = xmlReader.getFromConf("DEFAULT_LOG_URL",_confFile);
		defaultpmrnum = xmlReader.getFromConf("DEFAULT_PMR_NUM",_confFile);
	}

    @Override
    public void runWidget(Connection conn) {
        verifyWidget(conn);
        getInfoFromConsole();
        loadFromLog();
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
        System.out.println("Verifying this widget...with Connection object");
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
					
			
		
	}
	
	public void loadFromLog(){
		String str = null;
        FileReader reader = null;
        String[] perfitem;
        String[] perfvalues =new String[16];
        
        //overwrite - remove previous record first
        if(owrflag == true){
        	System.out.println("[SI] Over-write flag = true, removing previous logs from ["+dbtable+"]" );
        	String sql1 = "DELETE FROM sym.vemkd where PMR='"+pmr+"'";
			Statement stmt1 = null;
			try {
				stmt1 = conn.createStatement();
				stmt1.executeUpdate(sql1);
				
				stmt1.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("[SI] Records have been removed" );
        }
        
		try {
			System.out.println("[SI] Loading VEMKD perf data from log file ["+logurl+"] into ["+dbtable+"]" );
			reader = new FileReader(logurl);
			BufferedReader br = new BufferedReader(reader);
			
			int counter = 0;
			String logdate;
			
			while((str = br.readLine()) != null) {
				perfitem = null;
				//System.out.println("*"+str);
				
				//get values which cover by <>
				Pattern pattern = Pattern.compile("\\<(.*?)\\>");
				
				//get string clips
				perfitem = str.split(" ");
				if (perfitem.length >2){
					logdate = perfitem[0]+" "+perfitem[1];
					if(perfitem.length-1 >= 5	&& perfitem[5].equals("printPerf:")){
						counter++;
						//System.out.print(".");
						Matcher matcher = pattern.matcher(str);
						int i = 0;
						while (matcher.find()){
							perfvalues[i] = matcher.group(1);
							i++;
							printloading(i%4, counter);
						}
						String sql = "INSERT INTO "+dbtable+
	                    "(logdate,importdate,opcode,totalcnt,totaltime,maxtime,mintime,totalfileio,maxfileio,minfileio,"+
	                    "totaliocounter,totalchanopentime,maxchanopentime,minchanopentime,func,PMR)"+
	                    " values('"+logdate+"',SYSDATE(),"+perfvalues[0]+","+perfvalues[1]+","+perfvalues[2]+","+perfvalues[3]+","+perfvalues[4]+","+perfvalues[5]+","+perfvalues[6]+","+perfvalues[7]+","+perfvalues[8]+","+perfvalues[9]+","+perfvalues[10]+","+perfvalues[11]+",'"+perfvalues[12]+"','"+pmr+"')";
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
			}
			br.close();
			reader.close();
			System.out.println("[SI] Total ["+counter+"] lines have been imported.");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("There is an error while loading vemkd performance file, either file is not exist or can't be operated");
			e.printStackTrace();
		}
		System.out.println();
	}

	public void getInfoFromConsole(){
		Scanner sca = new Scanner(System.in);
		System.out.println("1. Please input log directory, default = "+defaultlogurl);
		logurl = sca.nextLine();
		if(logurl.equals("")){
			logurl = defaultlogurl;
		}
		
		while(! checkFileUrl(logurl)){
			logurl = sca.nextLine();
		}
		System.out.println("Log url set to ["+logurl+"]");
		
		System.out.println("2. Please input pmr number, default = ["+defaultpmrnum+"] for general test");
		pmr = sca.nextLine();
		if(pmr.equals("")){
			pmr = defaultpmrnum;
		}
		System.out.println("PMR set to ["+pmr+"]");
		
		System.out.println("3. Do you want to write to database, default = N (N means a parser test)");
		String strwrflag = sca.nextLine();
		strwrflag = strwrflag.toUpperCase();
        wrflag = strwrflag.equals("Y");
		System.out.println("Write flag set to ["+wrflag+"]");
		
		if(wrflag){
			System.out.println("4. Do you want to over-write to database, default = N (no over-write means to append those data to this PMR, it may cause duplicate records)");
			String strowrflag = sca.nextLine();
			strowrflag = strowrflag.toUpperCase();
            owrflag = strowrflag.equals("Y");
			System.out.println("Over-write flag set to ["+wrflag+"]");
		}
	}
	
	public void printloading(int index, int count){
		String[] symbol = {"|","/","-","\\"};
		System.out.print(symbol[index]+" ["+count+"] line(s) have been imported to database\r");
	}
	
	public void getFileList(){
		long a = System.currentTimeMillis();
        
        LinkedList list = new LinkedList();
        File dir = new File("./reports/");
        File[] file = dir.listFiles();
        for (int i = 0; i < file.length; i++) {
            if (file[i].isDirectory())
                list.addLast(file[i]);
            else
                System.out.println(file[i].getAbsolutePath());
        }
        File tmp;
        while (!list.isEmpty()) {
            tmp = (File) list.removeFirst();
            if (tmp.isDirectory()) {
                file = tmp.listFiles();
                if (file == null)
                    continue;
                for (int i = 0; i < file.length; i++) {
                    if (file[i].isDirectory())
                        list.add(file[i]);
                    else
                        System.out.println(file[i].getAbsolutePath());
                }
            } else {
                System.out.println(tmp.getAbsolutePath());
            }
        }
        
        System.out.println(System.currentTimeMillis() - a);
	}
	
	public boolean checkFileUrl(String url){
		try {
			FileReader reader = null;
			reader = new FileReader(url);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("Can't find/open "+url+", please check and enter again.");
			return false;
			//e.printStackTrace();
		}
		System.out.println(url+", verified.");
		return true;
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
}
