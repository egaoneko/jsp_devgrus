package net.devgrus.guestbook.handler;

/**
 * Description
 * Donghyun Seo (egaoneko@naver.com)
 * 2015-01-26
 * Copyright ⓒ 2013-2015 Donghyun Seo All rights reserved.
 * version
 */
public class GuestbookNotFoundException extends Exception {
    /**
     * Constructs a new exception with the specified detail message.  The
     * cause is not initialized, and may subsequently be initialized by
     * a call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public GuestbookNotFoundException(String message) {
        super(message);
    }
}
