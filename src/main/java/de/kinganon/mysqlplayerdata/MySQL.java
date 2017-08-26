package de.kinganon.mysqlplayerdata;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

public class MySQL {
    public static Connection connection1;
    public static Connection connection2;
    
    public MySQL(String host, String username1, String passwort1, String datenbank1, String username2, String passwort2, String datenbank2) {
        if (!isConnected(connection1)) {
            try {
                connection1 = DriverManager.getConnection(
                        "jdbc:mysql://" + host + ":" + 3306 + "/" + datenbank1, username1, passwort1);
                System.out.println("\n" + PlayerData.ANSI_GREEN + "MySQL Verbindung 1 hergestellt" + PlayerData.ANSI_RESET);
            } catch (SQLException e) {
                System.out.println("\n" + PlayerData.ANSI_RED + "MySQL Verbindung 1 ist nicht gültig" + PlayerData.ANSI_RESET);
                e.printStackTrace();
                System.exit(1);
            }
            if (!isConnected(connection2)) {
                try {
                    connection2 = DriverManager.getConnection(
                            "jdbc:mysql://" + host + ":" + 3306 + "/" + datenbank2, username2, passwort2);
                    System.out.println("\n" + PlayerData.ANSI_GREEN + "MySQL Verbindung 2 hergestellt\n" + PlayerData.ANSI_RESET);
                    createTable();
                } catch (SQLException e) {
                    System.out.println("\n" + PlayerData.ANSI_RED + "MySQL Verbindung 2 ist nicht gültig" + PlayerData.ANSI_RESET);
                    e.printStackTrace();
                    System.exit(1);
                }
            }
        }
    }
    
    public static boolean isConnected(Connection connection) {
        try {
            if (connection != null && connection.isClosed() != true) {
                return true;
            }
        } catch (SQLException e) {
            return false;
        }
        return false;
    }
    
    public static void update(String qry, Connection connection) {
        if (isConnected(connection)) {
            try {
                connection.createStatement().executeUpdate(qry);
            } catch (SQLException e) {
                e.printStackTrace();
        }
    } else {
            System.out.println("\n" + PlayerData.ANSI_RED + "MySQL Verbindung verloren" + PlayerData.ANSI_RESET);
            System.exit(1);
        }
    }
    
    public static ResultSet getResult(String qry, Connection connection) {
        if (isConnected(connection1)) {
            try {
                return connection.createStatement().executeQuery(qry);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("\n" + PlayerData.ANSI_RED + "MySQL Verbindung verloren" + PlayerData.ANSI_RESET);
            System.exit(1);
        }
        return null;
    }
    
    public static void createTable() {
        if (isConnected(connection2)) {
            update("CREATE TABLE IF NOT EXISTS ChatBlocker (mcUUID VARCHAR(100), time BIGINT(100), need BOOLEAN)", connection2);
        }
        
    }
    
    public static void addUser(String uuid) {
        update("INSERT INTO ChatBlocker(mcUUID, time, need) VALUES ('" + uuid.replace("-", "") + "', '1503764818210',false)", connection2);
    }
    
    public static String[] getUUID(String line, String table) {
        ResultSet rs = getResult("SELECT "+line+" FROM "+table, connection1);
        ArrayList<String> list= new ArrayList<String>();
        try{
        while (rs.next()) {
            list.add(rs.getString(line));
        }
       String[] result = new String[list.size()];
        result = list.toArray(result);
        return result;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
}
