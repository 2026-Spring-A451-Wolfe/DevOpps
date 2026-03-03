 /**************************************************************************
 * Filename: DatabaseUtil.java
 * Project: Infrastructure Reporting & Tracking System
 * Description: Provides centralized database connection management and utility
 *              methods for interacting with the application’s data source.
 * Author: Sophina Nichols
 * Date Last Modified: 03/03/2026
 **************************************************************************/

package com.example.web.util;

import org.postgresql.ds.PGSimpleDataSource;
import javax.sql.DataSource;

public class DatabaseUtil {

    private static PGSimpleDataSource dataSource;

    static {
        dataSource = new PGSimpleDataSource();
        dataSource.setServerNames(new String[]{"localhost"});
        dataSource.setPortNumbers(new int[]{5432});
        dataSource.setDatabaseName("nola_db");
        dataSource.setUser("postgres");
        dataSource.setPassword("");
    }

    public static DataSource getDataSource() {
        return dataSource;
    }
}