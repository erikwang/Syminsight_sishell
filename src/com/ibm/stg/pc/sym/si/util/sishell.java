package com.ibm.stg.pc.sym.si.util;



import java.util.Scanner;



import com.mysql.jdbc.Connection;
import com.ibm.stg.pc.sym.si.widget.*;

public class sishell {
	static Dbconn db;
	static VemkdPerfWidget vpwidget;
	static GenRepWidget genrep;
	
	public static void main(String[] args) {
		Scanner sca = new Scanner(System.in);
		Connection conn = null;
		
		String dbtable;
		//Add ./conf.xml as Linux
		loadConfig("./conf/conf.xml");
		
		//For windows
		//loadConfig("conf\\conf.xml");
		
		
		System.out.println("Hello SI 0.9 alpha!");
		try {
			//For Linux
			db = new Dbconn("./conf/proxool.xml");
			
			//For Windows
			//db = new Dbconn("conf\\proxool.xml");
			
			//conn = db.getConn();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		initializeModules();
		
		String name = null;
		while(true){
			if(name == null){
				System.out.println("> Who are you?");
				name = sca.nextLine();
				if(name.equals("")){
					name = "Guest";
				}
			}
			System.out.println(name+", please enter your command / ? for help");
			ShowHelp();
			String command = sca.nextLine();
			switch(command){
				case "?":
					ShowHelp();
					break;
				case "cdb":
					break;
				case "ll":
					System.out.println("Entering Load Log From Vemkd log file");
					//vpwidget.getDbConnection(db);
					vpwidget.runWidget();
					//loadFromLog("c:\\test1.log","PMR8888","sym.vemkd",false);
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
					break;
				case "gr":
					System.out.println("Entering reporting generator");
					genrep.runWidget();
					break;
				case "by":
					System.out.println("bye - thank you for using this tool");
					return;
					
				default:
					System.out.println("Unknown command, please try again. ? for help");
			}
		}
	}
	
	public static void loadConfig(String _confFile){
		System.out.println("Now loading any configuration from conf.xml...");
		//XmlReader xmlReader= new XmlReader();
		//String dbtable = xmlReader.getFromConf("DBTABLE",_confFile);
		//String defaultlogurl = xmlReader.getFromConf("DEFAULT_LOG_URL",_confFile);
	}
	
	public static void initializeModules(){
		vpwidget = new VemkdPerfWidget();
		genrep = new GenRepWidget();
	}
	
	public static void ShowHelp(){
		System.out.println("Available command: \nll - Load Log  \ngr - Generate Report  \nby - quit");
	}
}