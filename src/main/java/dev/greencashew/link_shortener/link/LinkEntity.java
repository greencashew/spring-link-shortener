package dev.greencashew.link_shortener.link;

import dev.greencashew.link_shortener.link.api.dto.LinkDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Builder
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
class LinkEntity {
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private String id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String targetUrl;

    private LocalDate expirationDate;

    private int visits;

    static LinkEntity fromDto(LinkDto link) {
        return new LinkEntity(
                link.id(),
                link.email(),
                link.targetUrl(),
                link.expirationDate(),
                link.visits()
        );
    }

    LinkDto toDto() {
        return new LinkDto(id, email, targetUrl, expirationDate, visits);
    }
}
