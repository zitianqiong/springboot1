package pers.zitianqiong;

import com.taosdata.jdbc.TSDBDriver;
import pers.zitianqiong.domain.User;

import java.sql.*;
import java.util.Properties;

/**
 * <p></p>
 *
 * @author 丛吉钰
 */
public class TaosTest {
	public static void main(String[] args) throws Exception{
		Connection conn = getConn();
		Statement stmt = conn.createStatement();
// create database
//		stmt.executeUpdate("create database if not exists db");
// use database
//		stmt.executeUpdate("use test");
//		stmt.executeUpdate("use db");
// create table
//		stmt.executeUpdate("create table if not exists tb (ts timestamp, temperature int, humidity float)");
		// insert data
		int affectedRows  = stmt.executeUpdate("insert into d10000 using meters tags('Beijng.Chaoyang',2) values(now, 23, 10.3, 3.1)");
//		int affectedRows = stmt.executeUpdate("insert into tb values(now, 23, 10.3) (now + 1s, 20, 9.3)");
		System.out.println("insert " + affectedRows + " rows.");
		// query data
		ResultSet resultSet = stmt.executeQuery("select * from d10000");
		Timestamp ts = null;
		int temperature = 0;
		float humidity = 0;
		while(resultSet.next()){
			ts = resultSet.getTimestamp(1);
			temperature = resultSet.getInt(2);
			System.out.printf("%s, %d\n", ts, temperature);
		}
		conn.close(); // put back to conneciton pool
	}

	public static Connection getConn() throws Exception{
		Class.forName("com.taosdata.jdbc.TSDBDriver");
		// Class.forName("com.taosdata.jdbc.rs.RestfulDriver");
		String jdbcUrl = "jdbc:TAOS://taosnode1:6030/idea?user=root&password=taosdata";
		// String jdbcUrl = "jdbc:TAOS-RS://taosdemo.com:6041/test?user=root&password=taosdata";
		Properties connProps = new Properties();
		connProps.setProperty(TSDBDriver.PROPERTY_KEY_CHARSET, "UTF-8");
		connProps.setProperty(TSDBDriver.PROPERTY_KEY_TIME_ZONE, "UTC-8");
		Connection conn = DriverManager.getConnection(jdbcUrl, connProps);
		return conn;
	}


}
