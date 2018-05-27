package com.ibm.stg.pc.sym.si.widget;

import com.ibm.stg.pc.sym.si.util.Dbconn;
import java.sql.Connection;

public interface Widget {
	void runWidget(Connection conn);
	void verifyWidget();
	boolean loadWidget();
	void setDbConnectionPool(Dbconn _dbconn);
}