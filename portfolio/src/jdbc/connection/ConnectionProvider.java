package jdbc.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Description
 * Donghyun Seo (egaoneko@naver.com)
 * 2015-01-18
 * Copyright ⓒ 2013-2015 Donghyun Seo All rights reserved.
 * version
 */
public class ConnectionProvider {

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:apache:commons:dbcp:/portfolio");
    }
}
