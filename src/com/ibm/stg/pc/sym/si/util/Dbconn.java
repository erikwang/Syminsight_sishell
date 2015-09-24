package com.ibm.stg.pc.sym.si.util;


import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.logicalcobwebs.proxool.ProxoolDataSource;
import org.logicalcobwebs.proxool.ProxoolException;
import org.logicalcobwebs.proxool.configuration.JAXPConfigurator;

import com.mysql.jdbc.Connection;



public class Dbconn{
	private static String dbConfig; 
	private static final Log LOG = LogFactory.getLog(ProxoolDataSource.class);
	//private String STRURL = "jdbc:mysql://ekmcmc.eng.platformlab.ibm.com:3306/sym";
	
	//String dbDriver = "com.mysql.jdbc.Driver";
	//String dbDriver = "org.logicalcobwebs.proxool.ProxoolDriver";
	//String CONNECTUSERNAME = "root";
	//String CONNECTPASSWORD = "toor";
	//String STRURL = "jdbc:mysql://ekmcmc.eng.platformlab.ibm.com:3306/hearthstone";

	
	public Dbconn(String dbConfigMas) {  
	        dbConfig = dbConfigMas;  
	}
	
	public boolean init() {  
        try {  
            JAXPConfigurator.configure(dbConfig, false);  
            return true;  
        } catch (ProxoolException ex) {  
            ex.printStackTrace();  
        }  
        return false;  
    }
	
	public static Connection getConn() {  
        
        Connection temp = null;  
        try {  
        		JAXPConfigurator.configure(dbConfig, false);  
                temp = (Connection) DriverManager.getConnection("proxool.sipool");  
            } catch (ProxoolException ex) {  
                ex.printStackTrace();  
            } catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
        return temp;  
    }  
	
}