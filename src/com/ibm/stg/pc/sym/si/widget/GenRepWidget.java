package com.ibm.stg.pc.sym.si.widget;

import java.io.IOException;
import java.util.Scanner;

import com.ibm.stg.pc.sym.si.util.XmlReader;
import com.ibm.stg.pc.sym.si.widget.Widget;
import com.mysql.jdbc.Connection;

public class GenRepWidget implements Widget {
	String defaultrepurl;
	String defaultformat;
	String defaultparafileurl;
	String defaultoutputurl;
	
	String repurl;
	String parafile;
	String format;
	String outputurl;


	//For Windows
	//String _confFile = "conf\\conf.xml";
	
	//For Linux
	String _confFile = "./conf/conf_genrep.xml";
	
	
	public GenRepWidget(){
		verifyWidget();
		XmlReader xmlReader= new XmlReader();
		defaultrepurl = xmlReader.getFromConf("DEFAULT_REPORT_FILE_URL",_confFile);
		defaultformat = xmlReader.getFromConf("DEFAULT_FORMAT",_confFile);
		defaultparafileurl = xmlReader.getFromConf("DEFAULT_PARAMETER_FILE_URL",_confFile);
		defaultoutputurl = xmlReader.getFromConf("DEFAULT_OUTPUT_URL",_confFile);
	}
	
	@Override
	public void runWidget() {
		this.getInfoFromConsole();
		this.genReport();
	}

	@Override
	public void getDbConnection(Connection conn) {
		// TODO Auto-generated method stub
		
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

	
	public void genReport(){
		String command = "./ReportEngine/genReport.sh -m runrender -f "+format+" -F "+parafile+" -o "+outputurl+" "+repurl;
		System.out.println("[SI] Report generate command: "+command);
		System.out.println("[SI] Preparing report file...");
		Process runprocess = null;
		try {
			runprocess = Runtime.getRuntime().exec(command);
			runprocess.waitFor();
			System.out.println("[SI]Run generate report command exit with code "+runprocess.exitValue());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			runprocess.destroy();
		}
		
	}

	public void getInfoFromConsole(){
		Scanner sca = new Scanner(System.in);
		System.out.println("1. Please enter the report file name directory, default = "+defaultrepurl);
		repurl = sca.nextLine();
		if(repurl.equals("")){
			repurl = defaultrepurl;
		}
		System.out.println("Report file url set to ["+repurl+"]");
		System.out.println("2. Please enter the output file format PDF|HTML, default = ["+defaultformat+"]");
		format = sca.nextLine();
		if(format.equals("")){
			format = defaultformat;
		}
		System.out.println("Format set to ["+format+"]");
		System.out.println("3. Please enter the parameter file url, default = ["+defaultparafileurl+"]");
		String strwrflag = sca.nextLine();
		if(strwrflag.equals("")){
			parafile = defaultparafileurl;
		}
		System.out.println("Parameter file url set to ["+parafile+"]");
		System.out.println("4. Please enter the output file url, default = ["+defaultoutputurl+"]");
		outputurl = sca.nextLine();
		if(outputurl.equals("")){
			outputurl = defaultoutputurl;
		}
		System.out.println("Output file url set to ["+outputurl+"]");
		
	}

}