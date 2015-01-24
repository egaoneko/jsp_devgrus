package net.devgrus.comment.handler;

import net.devgrus.handler.CommandHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Description
 * Donghyun Seo (egaoneko@naver.com)
 * 2015-01-25
 * Copyright ⓒ 2013-2015 Donghyun Seo All rights reserved.
 * version
 */
public class UpdateFormHandler implements CommandHandler {
    @Override
    public String process(HttpServletRequest request, HttpServletResponse response) throws Throwable {

        // 수정할 comment 를 불러오기 위하여 ReadHandler 를 사용
        ReadHandler readHandler = new ReadHandler();
        readHandler.process(request, response);

        return "/view/comment/updateForm.jsp";
    }
}
