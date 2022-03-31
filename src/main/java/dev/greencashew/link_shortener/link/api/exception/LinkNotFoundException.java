package dev.greencashew.link_shortener.link.api.exception;

public final class LinkNotFoundException extends LinkBusinessException {

    public LinkNotFoundException(final String url) {
        super("Shortened link " + url + " not found.");
    }
}
