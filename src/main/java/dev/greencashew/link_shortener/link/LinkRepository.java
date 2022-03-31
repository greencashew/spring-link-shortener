package dev.greencashew.link_shortener.link;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

interface LinkRepository extends CrudRepository<LinkEntity, String> {

    @Query("SELECT e FROM LinkEntity e WHERE e.expirationDate < ?1")
    List<LinkEntity> findLinksBeforeDate(LocalDate localDate);

}
