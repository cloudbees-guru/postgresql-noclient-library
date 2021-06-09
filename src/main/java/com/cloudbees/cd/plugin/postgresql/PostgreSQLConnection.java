package com.cloudbees.cd.plugin.postgresql;

import java.sql.*;

/**
 * Hello world!
 *
 */
public class PostgreSQLConnection {
    private String server;
    private String port;
    private String databaseName;
    private String login;
    private String password;
    private String url_start = "jdbc:postgresql://";

    public void executeQuery(String query) throws Exception {
        Connection con = this.connect();
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(query);
        if (rs.next()) {
            System.out.println(rs.getString(1));
        }
        this.disconnect(con);
    }

    private Connection connect() throws Exception {
        try {
            String url_full = url_start + server + ":" + port + "/" + databaseName;
            return DriverManager.getConnection(url_full, login, password);
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new Exception("Could not connect to database: " + ex.getMessage(), ex);
        }
    }

    private void disconnect(Connection con) throws Exception {
        try {
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new Exception("Could not close connection to database: " + ex.getMessage(), ex);
        }
    }


    public PostgreSQLConnection() {
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUrl_start() {
        return url_start;
    }

    public void setUrl_start(String url_start) {
        this.url_start = url_start;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }
}
