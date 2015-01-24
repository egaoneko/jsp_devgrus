package net.devgrus.comment.handler;

/**
 * Description
 * Donghyun Seo (egaoneko@naver.com)
 * 2015-01-04
 * Copyright ⓒ 2013-2015 Donghyun Seo All rights reserved.
 * version
 */
public class InvalidPasswordException extends Exception {

    public InvalidPasswordException(String message){
        super(message);
    }
}
