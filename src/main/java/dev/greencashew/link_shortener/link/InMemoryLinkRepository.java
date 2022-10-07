package dev.greencashew.link_shortener.link;

import org.apache.commons.lang3.NotImplementedException;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Component
class InMemoryLinkRepository implements LinkRepository {

    final Map<String, LinkEntity> entityMap;

    InMemoryLinkRepository() {
        this.entityMap = new HashMap<>();
    }

    @Override
    public <S extends LinkEntity> S save(S entity) {
        return (S) entityMap.put(entity.getId(), entity);
    }

    @Override
    public <S extends LinkEntity> Iterable<S> saveAll(Iterable<S> entities) {
        throw new NotImplementedException();
    }

    @Override
    public Optional<LinkEntity> findById(String s) {
        return Optional.ofNullable(entityMap.get(s));
    }

    @Override
    public boolean existsById(String s) {
        throw new NotImplementedException();
    }

    @Override
    public Iterable<LinkEntity> findAll() {
        throw new NotImplementedException();
    }

    @Override
    public Iterable<LinkEntity> findAllById(Iterable<String> strings) {
        throw new NotImplementedException();
    }

    @Override
    public long count() {
        throw new NotImplementedException();
    }

    @Override
    public void deleteById(String s) {
        throw new NotImplementedException();
    }

    @Override
    public void delete(LinkEntity entity) {
        throw new NotImplementedException();
    }

    @Override
    public void deleteAllById(Iterable<? extends String> strings) {
        throw new NotImplementedException();
    }

    @Override
    public void deleteAll(Iterable<? extends LinkEntity> entities) {
        throw new NotImplementedException();
    }

    @Override
    public void deleteAll() {
        throw new NotImplementedException();
    }

    @Override
    public List<LinkEntity> findLinksBeforeDate(final LocalDate localDate) {
        throw new NotImplementedException();
    }

    @Override
    public List<LinkEntity> findAllByVisitsGreaterThan(final int minimumVisits) {
        throw new NotImplementedException();
    }
}
