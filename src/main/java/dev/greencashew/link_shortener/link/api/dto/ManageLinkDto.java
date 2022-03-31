package dev.greencashew.link_shortener.link.api.dto;

import java.time.LocalDate;

public record ManageLinkDto(String targetUrl,
                            LocalDate expirationDate,
                            int visits) {
}
