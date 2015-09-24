package com.ibm.stg.pc.sym.si.util;

import java.util.Scanner;

import com.mysql.jdbc.Connection;
import com.ibm.stg.pc.sym.si.widget.*;

public class sishell {
	static Dbconn db;
	public static void main(String[] args) {
		Scanner sca = new Scanner(System.in);
		Connection conn = null;
		
		String dbtable;
		loadConfig("/conf.xml");
		
		
		// TODO Auto-generated method stub
		System.out.println("Hello SI!");
		try {
			db = new Dbconn("/proxool.xml");
			conn = db.getConn();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while(true){
			System.out.println("> Please enter your command / ? for help");
			String name = sca.nextLine();
			switch(name){
				case "?":
					ShowHelp();
					break;
				case "cdb":
					break;
				case "l":
					VemkdPerfWidget vpwidget = new VemkdPerfWidget();
					vpwidget.getDbConnection(conn);
					vpwidget.runWidget();
					break;
				case "ll":
					System.out.println("Entering Load Log From Vemkd log file");
					VemkdPerfWidget vpwidget2 = new VemkdPerfWidget();
					vpwidget2.getDbConnection(conn);
					vpwidget2.getInfoFromConsole();
					//loadFromLog("c:\\test1.log","PMR8888","sym.vemkd",false);
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
	}
	
	public static void ShowHelp(){
		System.out.println("Available command: ll - Load log | l - general database access test | by - quit");
	}
}