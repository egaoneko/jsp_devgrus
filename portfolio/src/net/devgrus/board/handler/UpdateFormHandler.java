package net.devgrus.board.handler;

import net.devgrus.handler.CommandHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Description
 * Donghyun Seo (egaoneko@naver.com)
 * 2015-01-22
 * Copyright ⓒ 2013-2015 Donghyun Seo All rights reserved.
 * version
 */
public class UpdateFormHandler implements CommandHandler {
    @Override
    public String process(HttpServletRequest request, HttpServletResponse response) throws Throwable {

        ReadHandler readHandler = new ReadHandler();    // 수정을 위해 Article 을 속성에 저장하기 위하여
        readHandler.process(request, response);         // ReadHandler 를 이용

        return "/view/board/updateForm.jsp";
    }
}
