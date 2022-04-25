package dev.greencashew.link_shortener.controller;

import dev.greencashew.link_shortener.link.api.LinkService;
import dev.greencashew.link_shortener.link.api.dto.LinkDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@AllArgsConstructor
@RequestMapping("/s")
class RedirectController {
    private final LinkService service;

    @GetMapping("/{id}")
    @Operation(description = "Redirect link by it's identifier. This endpoint has to be tested by direct GET request in browser.")
    @ApiResponse(responseCode = "302", description = "User is redirected to expected location.", content = @Content)
    @ApiResponse(responseCode = "404", description = "Shortened link not found.", content = @Content(examples = @ExampleObject(value = "{\"errorMessage\": \"Shortened link link-alias not found.\"}")))
    public void redirectLink(
            @Schema(description = "Identifier/alias to link. Used for redirection.", example = "link-alias", required = true)
            @PathVariable String id, HttpServletResponse httpServletResponse) throws IOException {
        final LinkDto linkDto = service.gatherLinkAndIncrementVisits(id);
        httpServletResponse.sendRedirect(linkDto.targetUrl());
    }
}
