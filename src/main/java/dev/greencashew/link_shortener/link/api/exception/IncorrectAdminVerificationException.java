package dev.greencashew.link_shortener.link.api.exception;

public final class IncorrectAdminVerificationException extends LinkBusinessException {
    public IncorrectAdminVerificationException(final String id, final String email) {
        super("Link " + id + " is not related with email: " + email);
    }
}
