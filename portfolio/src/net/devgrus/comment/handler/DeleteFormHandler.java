package net.devgrus.comment.handler;

import net.devgrus.handler.CommandHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Description
 * Donghyun Seo (egaoneko@naver.com)
 * 2015-01-24
 * Copyright ⓒ 2013-2015 Donghyun Seo All rights reserved.
 * version
 */
public class DeleteFormHandler implements CommandHandler {
    @Override
    public String process(HttpServletRequest request, HttpServletResponse response) throws Throwable {
        return "/view/comment/deleteForm.jsp";
    }
}
