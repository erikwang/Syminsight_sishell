package com.ibm.stg.pc.sym.si.widget;

import com.ibm.stg.pc.sym.si.util.Dbconn;
import com.mysql.jdbc.Connection;

public interface Widget {
	void runWidget();
	void verifyWidget();
	boolean loadWidget();
	void getDbConnection(Dbconn dbconn);
}