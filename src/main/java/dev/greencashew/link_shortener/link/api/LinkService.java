package dev.greencashew.link_shortener.link.api;

import dev.greencashew.link_shortener.link.api.dto.LinkDto;
import dev.greencashew.link_shortener.link.api.dto.ManageLinkDto;
import dev.greencashew.link_shortener.link.api.exception.IncorrectAdminVerificationException;
import dev.greencashew.link_shortener.link.api.exception.LinkAlreadyExistsException;
import dev.greencashew.link_shortener.link.api.exception.LinkNotFoundException;

public interface LinkService {
    LinkDto createLink(LinkDto linkDto) throws LinkAlreadyExistsException;

    LinkDto retrieveLink(String id) throws LinkNotFoundException;

    void updateLink(String id, String email, ManageLinkDto linkDto) throws IncorrectAdminVerificationException, LinkNotFoundException;

    void deleteLink(String id, String email) throws LinkNotFoundException;

    LinkDto gatherLinkAndIncrementVisits(String id) throws LinkNotFoundException;
}
