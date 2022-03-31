package dev.greencashew.link_shortener.link;

import dev.greencashew.link_shortener.link.api.LinkService;
import dev.greencashew.link_shortener.link.api.dto.LinkDto;
import dev.greencashew.link_shortener.link.api.dto.ManageLinkDto;
import dev.greencashew.link_shortener.link.api.exception.IncorrectAdminVerificationException;
import dev.greencashew.link_shortener.link.api.exception.LinkAlreadyExistsException;
import dev.greencashew.link_shortener.link.api.exception.LinkNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
class LinkServiceImpl implements LinkService {

    private final LinkRepository repository;

    @Override
    @Transactional
    public LinkDto createLink(final LinkDto createLink) throws LinkAlreadyExistsException {
        if (repository.findById(createLink.id()).isPresent()) {
            throw new LinkAlreadyExistsException(createLink.id());
        }

        final LinkEntity linkEntity = LinkEntity.fromDto(createLink);
        repository.save(linkEntity);
        return createLink;
    }

    @Override
    @Transactional
    public LinkDto retrieveLink(final String id) throws LinkNotFoundException {
        return repository.findById(id)
                .map(LinkEntity::toDto)
                .orElseThrow(() -> new LinkNotFoundException(id));
    }

    @Override
    @Transactional
    public void updateLink(final String id, final String email, final ManageLinkDto linkDto) throws IncorrectAdminVerificationException, LinkNotFoundException {
        final LinkEntity linkEntity = repository.findById(id)
                .orElseThrow(() -> new LinkNotFoundException(id));

        if (linkEntity.getEmail().equalsIgnoreCase(email)) {
            linkEntity.setTargetUrl(linkDto.targetUrl());
            linkEntity.setExpirationDate(linkDto.expirationDate());
            linkEntity.setVisits(linkDto.visits());
        } else {
            throw new IncorrectAdminVerificationException(id, email);
        }
    }

    @Override
    @Transactional
    public void deleteLink(final String id, final String email) throws LinkNotFoundException {
        final LinkEntity linkEntity = repository.findById(id)
                .orElseThrow(() -> new LinkNotFoundException(id));

        if (linkEntity.getEmail().equalsIgnoreCase(email)) {
            repository.delete(linkEntity);
        }
    }

    @Override
    @Transactional
    public LinkDto gatherLinkAndIncrementVisits(final String id) throws LinkNotFoundException {
        final LinkEntity linkEntity = repository.findById(id)
                .orElseThrow(() -> new LinkNotFoundException(id));
        linkEntity.setVisits(linkEntity.getVisits() + 1);
        return linkEntity.toDto();
    }
}
