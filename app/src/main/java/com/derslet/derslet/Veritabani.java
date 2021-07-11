package com.derslet.derslet;

import java.sql.Connection;
import java.sql.DriverManager;

public class Veritabani {
    private Connection connection;

    // private final String host = "ssprojectinstance.csv2nbvvgbcb.us-east-2.rds.amazonaws.com"  // For Amazon Postgresql
    private final String host = "35.234.91.196";  // For Google Cloud Postgresql
    private final String database = "derslet";
    private final int port = 5432;
    private final String user = "postgres";
    private final String pass = "tarikeren1104";
    private String url = "jdbc:postgresql://%s:%d/%s";
    private boolean status;

    public Veritabani() {
        this.url = String.format(this.url, this.host, this.port, this.database);
        connect();
        //this.disconnect();
        System.out.println("Baglanti Durumu:" + status);
    }

    private void connect() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Class.forName("org.postgresql.Driver");
                    connection = DriverManager.getConnection(url, user, pass);
                    status = true;
                    System.out.println("Baglanti:" + status);
                } catch (Exception e) {
                    status = false;
                    System.out.print(e.getMessage());
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (Exception e) {
            e.printStackTrace();
            this.status = false;
        }
    }

    public Connection getExtraConnection(){
        Connection c = null;
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection(url, user, pass);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return c;
    }
}
