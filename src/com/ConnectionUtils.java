package com;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionUtils {

	    static{
	          try {
	                 Class.forName("com.mysql.jdbc.Driver").newInstance();;
	          } catch (Exception ex) {
	          }
	    }

	    public static Connection getConnection() throws Exception
	    {
	           return getDBConnection();
	    }

	     public static Connection getDBConnection() throws Exception
	      {   
	    	  
	          Connection conn = null;
	          String URLPath="jdbc:mysql://localhost/nutrition?user=root&password=dade$321";
	          try{ 
	            conn = DriverManager.getConnection(URLPath);
	          }
	          catch(Exception e)
	          {
	              e.printStackTrace();
	          }

	          return conn;
	      }
	}

