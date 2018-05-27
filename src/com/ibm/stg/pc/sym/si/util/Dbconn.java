package com.ibm.stg.pc.sym.si.util;


import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import javax.sql.DataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/*
import org.logicalcobwebs.proxool.ProxoolDataSource;
import org.logicalcobwebs.proxool.ProxoolException;
import org.logicalcobwebs.proxool.configuration.JAXPConfigurator;
*/

import org.apache.commons.dbcp.ConnectionFactory;
import org.apache.commons.dbcp.DriverManagerConnectionFactory;
import org.apache.commons.dbcp.PoolableConnectionFactory;
import org.apache.commons.dbcp.PoolingDataSource;
import org.apache.commons.pool.impl.GenericObjectPool;


import com.mysql.jdbc.Connection;



public class Dbconn{
	private static String dbConfig; 
	//private static final Log LOG = LogFactory.getLog(ProxoolDataSource.class);
	//private String STRURL = "jdbc:mysql://ekmcmc.eng.platformlab.ibm.com:3306/sym";
	//String dbDriver = "com.mysql.jdbc.Driver";
	//String dbDriver = "org.logicalcobwebs.proxool.ProxoolDriver";
	//String CONNECTUSERNAME = "root";
	//String CONNECTPASSWORD = "toor";
	//String STRURL = "jdbc:mysql://ekmcmc.eng.platformlab.ibm.com:3306/hearthstone";


    // JDBC Driver Name & Database URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String JDBC_DB_URL = "jdbc:mysql://35.196.213.79:3306/sym";

    // JDBC Database Credentials
    static final String JDBC_USER = "root";
    static final String JDBC_PASS = "toor";

    private static GenericObjectPool gPool = null;



    public DataSource setUpPool() throws Exception {
        Class.forName(JDBC_DRIVER);

        // Creates an Instance of GenericObjectPool That Holds Our Pool of Connections Object!

        gPool = new GenericObjectPool();
        gPool.setMaxActive(5);
        // Creates a ConnectionFactory Object Which Will Be Use by the Pool to Create the Connection Object!

        ConnectionFactory cf = new DriverManagerConnectionFactory(JDBC_DB_URL, JDBC_USER, JDBC_PASS);
        // Creates a PoolableConnectionFactory That Will Wraps the Connection Object Created by the ConnectionFactory to Add Object Pooling Functionality!
        PoolableConnectionFactory pcf = new PoolableConnectionFactory(cf, gPool, null, null, false, true);
        return new PoolingDataSource(gPool);
    }

    public GenericObjectPool getConnectionPool() {
        return gPool;
    }
        // This Method Is Used To Print The Connection Pool Status
    public void printDbStatus() {
        System.out.println("Max.: " + getConnectionPool().getMaxActive() + "; Active: " + getConnectionPool().getNumActive() + "; Idle: " + getConnectionPool().getNumIdle());
    }




    public Dbconn(String dbConfigMas) {
	        dbConfig = dbConfigMas;  
	}

    public Dbconn() {

    }

/*

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
*/
}