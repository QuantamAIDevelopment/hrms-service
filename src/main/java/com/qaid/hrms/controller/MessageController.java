package com.qaid.hrms.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/message")
@Tag(name = "Message", description = "Message API")
public class MessageController {

    @Operation(
        summary = "Get message",
        description = "Returns a hello world message",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Successful operation",
                content = @Content(
                    mediaType = "text/plain",
                    schema = @Schema(implementation = String.class)
                )
            )
        }
    )
    @GetMapping
    public String getMessage() {
        return "Hello World";
    }
}
