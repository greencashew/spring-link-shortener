package dev.greencashew.link_shortener.link.api.exception;

public abstract class LinkBusinessException extends RuntimeException {

    public LinkBusinessException(final String message) {
        super(message);
    }

    @Override
    public Throwable fillInStackTrace() {
        return null;
    }
}
