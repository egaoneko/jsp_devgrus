package jdbc.loader;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import java.util.StringTokenizer;

/**
 * Description
 * Donghyun Seo (egaoneko@naver.com)
 * 2015-01-18
 * Copyright ⓒ 2013-2015 Donghyun Seo All rights reserved.
 * version
 */
public class DBCPInit extends HttpServlet{

    public void init (ServletConfig config) throws ServletException {
        try {
            String drivers = config.getInitParameter("jdbcdriver");
            StringTokenizer st = new StringTokenizer(drivers, ",");
            while (st.hasMoreTokens()){
                String jdbcDriver = st.nextToken();
                Class.forName(jdbcDriver);
            }

            Class.forName("org.apache.commons.dbcp.PoolingDriver");
        } catch (Exception ex) {
            throw new ServletException(ex);
        }
    }
}
