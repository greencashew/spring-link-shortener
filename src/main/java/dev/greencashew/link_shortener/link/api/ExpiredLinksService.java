package dev.greencashew.link_shortener.link.api;

import java.time.LocalDate;

public interface ExpiredLinksService {

    void removeExpiredLinks(LocalDate currentDate);
}
