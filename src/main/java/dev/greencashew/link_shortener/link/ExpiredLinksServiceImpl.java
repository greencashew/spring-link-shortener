package dev.greencashew.link_shortener.link;

import dev.greencashew.link_shortener.link.api.ExpiredLinksService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Slf4j
@Service
@AllArgsConstructor
class ExpiredLinksServiceImpl implements ExpiredLinksService {

    private final LinkRepository repository;

    @Override
    @Transactional
    public void removeExpiredLinks(final LocalDate currentDate) {
        final var expiredLinks = repository.findLinksBeforeDate(currentDate);
        repository.deleteAll(expiredLinks);
        log.info((long) expiredLinks.size() + " items with time expiration before " + currentDate + " has been deleted.");
    }
}
