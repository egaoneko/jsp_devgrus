package net.devgrus.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Description
 * Donghyun Seo (egaoneko@naver.com)
 * 2015-01-22
 * Copyright ⓒ 2013-2015 Donghyun Seo All rights reserved.
 * version
 */
public class NullHandler implements CommandHandler {
    @Override
    public String process(HttpServletRequest request,
                          HttpServletResponse response) throws Throwable {
        return "/view/nullCommand.jsp";
    }
}
