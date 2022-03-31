package dev.greencashew.link_shortener.scheduler;

import dev.greencashew.link_shortener.link.api.ExpiredLinksService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

import static java.time.LocalDate.now;

@Slf4j
@Component
@AllArgsConstructor
class RemoveExpiredLinksCron {

    private final ExpiredLinksService service;

    @Scheduled(cron = "${expired.links.cron}")
    public void removeExpiredLinks() {
        LocalDate today = now();
        log.info("STARTED JOB removeExpiredLinks for date " + today);
        service.removeExpiredLinks(today);
        log.info("ENDED JOB removeExpiredLinks for date " + today);
    }
}
