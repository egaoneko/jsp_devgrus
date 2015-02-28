package net.devgrus.guestbook.handler;

/**
 * Description
 * Donghyun Seo (egaoneko@naver.com)
 * 2015-01-26
 * Copyright ⓒ 2013-2015 Donghyun Seo All rights reserved.
 * version
 */
public class GuestbookException extends Exception {

    public GuestbookException(String msg, Exception cause){
        super(msg, cause);
    }

    public GuestbookException(String msg){
        super(msg);
    }
}
