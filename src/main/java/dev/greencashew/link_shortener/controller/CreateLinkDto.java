package dev.greencashew.link_shortener.controller;

import dev.greencashew.link_shortener.link.api.dto.LinkDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

record CreateLinkDto(
        @Schema(description = "Identifier/alias to link. Used for redirection.", example = "link-alias", required = true)
        @NotBlank @Size(min = 1, max = 60)
        String id,

        @Schema(description = "User email required for shortened link management (deletion, updating)", example = "test@greencashew.dev", required = true)
        @NotBlank @Email
        String email,

        @Schema(description = "Destination url we would like to ", example = "https://github.com/greencashew/warsztaty-podstawy-springa", required = true)
        @NotBlank
        String targetUrl,

        @Schema(description = "Link expiration time. If would like to have shortened link forever do not fill this field.", example = "2054-06-23", required = false)
        @Future
        LocalDate expirationDate) {

    LinkDto toDto() {
        return new LinkDto(
                id,
                email,
                targetUrl,
                expirationDate,
                0
        );
    }
}
