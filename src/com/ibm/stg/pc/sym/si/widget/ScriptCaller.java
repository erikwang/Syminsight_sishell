package com.ibm.stg.pc.sym.si.widget;

import org.python.util.PythonInterpreter;
import com.ibm.stg.pc.sym.si.util.Dbconn;

public class ScriptCaller implements Widget {

	@Override
	public void runWidget() {
		// TODO Auto-generated method stub
		System.out.println("I am going to call a script");
		String binurl = "/scratch/supsym/erikwang/temp/lsfcheck/";
		String command = "LSF_IO_Checker.py";
		System.out.println("[SI] A py script going to be called: "+binurl+command);
		
		Process runprocess = null;
		try {
			//runprocess = Runtime.getRuntime().exec("python "+binurl+command);
			//runprocess.waitFor();
			PythonInterpreter interpreter = new PythonInterpreter();  
			interpreter.execfile(binurl+command);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			System.out.println("Script run completed.");
			runprocess.destroy();
		}
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
	public void setDbConnection(Dbconn _dbconn) {
		// TODO Auto-generated method stub

	}

}
