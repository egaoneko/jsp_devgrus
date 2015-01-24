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
public interface CommandHandler {
    public String process(HttpServletRequest request, HttpServletResponse response) throws Throwable;
}
