package com.parkit.parkingsystem.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class DataBaseConfig {

    /**
    * Used by the Log4J system.
    */
    private static final Logger LOGGER = LogManager.getLogger("DataBaseConfig");

    /**
     * Open a SQL connection.
     * @return a Connection instance.
     * @throws ClassNotFoundException if the jdbc driver does not exist.
     * @throws SQLException when a error occurs during the SQL driver
     *                      initialization.
     */
    public static Connection getConnection()
            throws ClassNotFoundException, SQLException {
        LOGGER.info("Create DB connection");
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/prod",
                "root",
                "toto55");
    }

    /**
     * Close the SQL connection.
     * @param con a SQL database connection.
     */
    public void closeConnection(final Connection con) {
        if (con != null) {
            try {
                con.close();
                LOGGER.info("Closing DB connection");
            } catch (SQLException e) {
                LOGGER.error("Error while closing connection", e);
            }
        }
    }

    /**
     * Close the prepared statement.
     * @param ps a prepared statement SQL string.
     */
    public void closePreparedStatement(final PreparedStatement ps) {
        if (ps != null) {
            try {
                ps.close();
                LOGGER.info("Closing Prepared Statement");
            } catch (SQLException e) {
                LOGGER.error("Error while closing prepared statement", e);
            }
        }
    }

    /**
     * Close the SQL resource.
     * @param rs a sql resource variable.
     */
    public void closeResultSet(final ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
                LOGGER.info("Closing Result Set");
            } catch (SQLException e) {
                LOGGER.error("Error while closing result set", e);
            }
        }
    }
}
