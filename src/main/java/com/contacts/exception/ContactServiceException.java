package com.contacts.exception;

/**
 * Created by SAMarkov on 9/14/2016.
 */
public class ContactServiceException extends Exception{

    public ContactServiceException(String message, Exception e) {
        super(message, e);
    }

    public ContactServiceException(String s) {
        super(s);
    }
}
