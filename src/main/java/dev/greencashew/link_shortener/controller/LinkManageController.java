package dev.greencashew.link_shortener.controller;

import dev.greencashew.link_shortener.configuration.web.exception_handling.ExceptionResponse;
import dev.greencashew.link_shortener.link.api.LinkService;
import dev.greencashew.link_shortener.link.api.dto.LinkDto;
import dev.greencashew.link_shortener.link.api.dto.ManageLinkDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;

@RestController
@Validated
@AllArgsConstructor
@RequestMapping("/links")
class LinkManageController {
    private final LinkService service;

    @Operation(description = "Create shortened link")
    @ApiResponse(responseCode = "201", description = "Shortened link.", content = @Content(examples = @ExampleObject(value = """
              {
              "id": "link-alias",
              "targetUrl": "https://github.com/greencashew/warsztaty-podstawy-springa",
              "expirationDate": "2054-06-23",
              "visits": 0,
              "shortenedLink": "http://localhost:8080/s/link-alias"
            }""")))
    @ApiResponse(responseCode = "409", description = "Link with same identifier already exists.", content = @Content(examples = @ExampleObject(value = "{\"errorMessage\": \"Link with id: link-alias already exists.\"}")))
    @PostMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    LinkDto createLink(@RequestBody @Valid CreateLinkDto link) {
        return service.createLink(link.toDto());
    }

    @Operation(description = "Retrieve link by it's identifier.")
    @ApiResponse(responseCode = "200", description = "Data related to shortened link.", content = @Content(examples = @ExampleObject(value = """
            {
              "id": "link-alias",
              "targetUrl": "https://github.com/greencashew/warsztaty-podstawy-springa",
              "expirationDate": "2054-06-23",
              "visits": 0,
              "shortenedLink": "http://localhost:8080/s/link-alias"
            }""")))
    @ApiResponse(responseCode = "404", description = "Shortened link not found.", content = @Content(examples = @ExampleObject(value = "{\"errorMessage\": \"Shortened link link-alias not found.\"}")))
    @GetMapping("/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    LinkDto retrieveLink(
            @Schema(description = "Identifier/alias to link. Used for redirection.", example = "link-alias", required = true)
            @PathVariable String id
    ) {
        return service.retrieveLink(id);
    }

    @Operation(description = "Update shortened link")
    @ApiResponse(responseCode = "204", description = "Confirmation that shortened link has been updated.", content = @Content)
    @ApiResponse(responseCode = "404", description = "Shortened link link-alias not found.", content = @Content(examples = @ExampleObject(value = "{\"errorMessage\": \"Shortened link link-alias not found.\"}")))
    @ApiResponse(responseCode = "403", description = "Incorrect email address to link identifier.", content = @Content(examples = @ExampleObject(value = "{\"errorMessage\": \"Link link-alias is not related with email: youremail@example.com\"}")))
    @PatchMapping("/{id}")
    @ResponseBody
    ResponseEntity<?> updateLink(
            @Schema(description = "Identifier/alias to link. Used for redirection.", example = "link-alias", required = true)
            @PathVariable String id,

            @Schema(description = "User email required for shortened link management (deletion, updating)", example = "test@greencashew.dev", required = true)
            @Valid @Email @RequestHeader String email,

            @Valid @RequestBody ManageLinkDto manageLinkDto
    ) {
        service.updateLink(id, email, manageLinkDto);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Update shortened link")
    @ApiResponse(responseCode = "204", description = "Confirmation that shortened link has been deleted.", content = @Content)
    @ApiResponse(responseCode = "404", description = "Shortened link link-alias not found.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class), examples = @ExampleObject(value = "{ \"errorMessage\": \"Shortened link link-alias not found.\" }")))
    @ApiResponse(responseCode = "403", description = "Incorrect email address to link identifier.", content = @Content(examples = @ExampleObject(value = "{\"errorMessage\": \"Link link-alias is not related with email: youremail@example.com\"}")))
    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteLink(
            @Schema(description = "Identifier/alias to link. Used for redirection.", example = "link-alias", required = true)
            @PathVariable String id,

            @Schema(description = "User email required for shortened link management (deletion, updating)", example = "test@greencashew.dev", required = true)
            @Valid @Email @RequestHeader String email
    ) {
        service.deleteLink(id, email);
        return ResponseEntity.noContent().build();
    }
}
