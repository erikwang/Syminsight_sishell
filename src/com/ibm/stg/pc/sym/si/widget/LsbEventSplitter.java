package com.ibm.stg.pc.sym.si.widget;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import com.ibm.stg.pc.sym.si.util.Dbconn;
import java.sql.Connection;

public class LsbEventSplitter implements Widget {
	
	//Indicate where is the combined lsb event file
	//static String EVENT_LOG_DIR = "/tmp/lsb.event.ori";
	static String EVENT_LOG_DIR = "c:\\tmp\\";
	
	
	@Override
	public void runWidget(Connection conn) {
		// TODO Auto-generated method stub

	}

	@Override
	public void verifyWidget() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean loadWidget() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setDbConnectionPool(Dbconn _dbconn) {
		// TODO Auto-generated method stub

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FileReader reader = null;
		//StringBuffer sb = new StringBuffer();
		
		String str = null;
		
		try {
			reader = new FileReader(EVENT_LOG_DIR+"lsb.events.ori");
			BufferedReader br = new BufferedReader(reader);
			
			try {
				int hashcount = 0;
				while((str = br.readLine()) != null) {
					if(str.startsWith("#")){
						hashcount ++;
					}
				}
				br.close();
				System.out.println("total sub event files will be " + hashcount );
				
				
				//File pieceFile = new File(eventFilePiece);
				FileReader reader2 = new FileReader(EVENT_LOG_DIR+"lsb.events.ori");
				BufferedReader br2 = new BufferedReader(reader2);				
				String eventFilePiece;
				PrintWriter out = new PrintWriter(EVENT_LOG_DIR+"lsb.events");
				while ((str = br2.readLine()) != null){
					if (str.startsWith("#")){
						eventFilePiece = EVENT_LOG_DIR+"lsb.events."+hashcount;
						out = new PrintWriter(eventFilePiece);
						out.println(str);
						
						hashcount --;
						continue;
					}else{
						out.println(str);
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
