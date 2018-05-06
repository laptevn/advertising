package com.laptevn.advertising.initialization;

public class ParseException extends RuntimeException {
    private static final long serialVersionUID = -1495157660655738681L;

    public ParseException(String message) {
        super(message);
    }

    public ParseException(String message, Throwable cause) {
        super(message, cause);
    }
}