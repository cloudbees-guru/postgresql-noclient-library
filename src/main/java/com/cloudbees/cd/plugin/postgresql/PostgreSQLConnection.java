package com.cloudbees.cd.plugin.postgresql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
    private final String separator = "\n|;";

    private List<String> getQueries(String cd_parameter) {
        if (cd_parameter == null) {
            return Collections.emptyList();
        } else {
            return Arrays.asList(cd_parameter.split(separator));
        }
    }

    public void executeQuery(String query) throws Exception {
        List<String> queries = this.getQueries(query);
        Connection con = this.connect();
        Statement st = con.createStatement();
        con.setAutoCommit(false);

        try {
            for (String q : queries) {
                st.addBatch(q);
            }
            st.executeBatch();
            con.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Error while executing the queries: " + e.getMessage(), e);
        } finally {
            this.disconnect(con);
        }
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
