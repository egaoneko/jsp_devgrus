package net.devgrus.comment.service;

/**
 * Description
 * Donghyun Seo (egaoneko@naver.com)
 * 2015-01-04
 * Copyright ⓒ 2013-2015 Donghyun Seo All rights reserved.
 * version
 */
public class LastChildAleadyExistsException extends Exception {

    public LastChildAleadyExistsException(String message){
        super(message);
    }
}
