package com.vgr.assignment.controller;

import com.vgr.assignment.dto.ArticleCount;
import com.vgr.assignment.dto.BoxRequest;
import com.vgr.assignment.dto.BoxResponse;
import com.vgr.assignment.service.PackingService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/box")
public class BoxController {

    private final PackingService service;

    public BoxController(PackingService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<BoxResponse> box(@Valid @RequestBody BoxRequest req) {
        Map<Integer,Integer> order = req.items().stream()
                .collect(Collectors.toMap(
                        ArticleCount::type,
                        ArticleCount::count,
                        Integer::sum
                ));

        try {
            return service.chooseBox(order)
                    .map(id -> ResponseEntity.ok(new BoxResponse(id, null)))
                    .orElseGet(() -> ResponseEntity.unprocessableEntity()
                            .body(new BoxResponse(null, "Upphämtning krävs")));
        } catch (IllegalArgumentException | IllegalStateException ex) {
            // Ogiltig inmatning eller konfigurationsfel
            return ResponseEntity
                    .badRequest()
                    .body(new BoxResponse(null, ex.getMessage()));
        }
    }
}