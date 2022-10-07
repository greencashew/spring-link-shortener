package dev.greencashew.link_shortener.link;

import dev.greencashew.link_shortener.link.api.LinkService;
import dev.greencashew.link_shortener.link.api.dto.LinkDto;
import dev.greencashew.link_shortener.link.api.exception.LinkAlreadyExistsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LinkServiceImplTest {

    private LinkService service;

    @BeforeEach
    void setUp() {
        var repository = new InMemoryLinkRepository();
        service = new LinkServiceImpl(repository);
    }

    @DisplayName("Should save link in case of link creation")
    @ParameterizedTest(name = "{index} => id={0}, email={1}, target={2}")
    @CsvSource(textBlock = """
             id, email, target
             id2, email2, target2
             id3, email2, target2
             id4, email4,
             id5, , target5
            """)
    void shouldSaveAndRetrieveLink(String id, String email, String target) {
        //given
        final LinkDto linkDto = new LinkDto(id, email, target, null, 0);

        //when
        final LinkDto resultLink = service.createLink(linkDto);
        final LinkDto retrievedLink = service.retrieveLink(resultLink.id());

        //then
        assertEquals(resultLink.id(), linkDto.id());
        assertEquals(retrievedLink, linkDto);
    }

    @Test
    @DisplayName("Should throw link not found exception when link with same id already added")
    void shouldThrowLinkNotFoundExceptionWhenLinkWithSameIdAlreadyAdded() {
        //given
        String duplicatedId = "id";
        service.createLink(new LinkDto(duplicatedId, "emal", "target", null, 0));

        //when //then
        assertThrows(LinkAlreadyExistsException.class, () -> service.createLink(new LinkDto(duplicatedId, "another email", "another target", null, 0)));
    }

}