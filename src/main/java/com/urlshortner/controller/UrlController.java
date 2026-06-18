package com.urlshortner.controller;

import com.urlshortner.dto.AnalyticsResponse;
import com.urlshortner.dto.UrlRequest;

import com.urlshortner.model.Url;
import com.urlshortner.service.UrlService;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api")
public class UrlController {
    private final UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @PostMapping("/url")
    ResponseEntity<?> getShortCode(
            @Valid @RequestBody UrlRequest request) {
        String code = urlService.saveUrl(request.getOriginalUrl());

        return ResponseEntity.ok(code);
    }

    @GetMapping("/{code}")
    ResponseEntity<Void> redirect(@PathVariable String code) {
        String originalUrl = urlService.GetOriginalUrl(code);
        System.out.println(originalUrl);
        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(originalUrl)).build();
    }

    @GetMapping("/analytics/{id}")
    ResponseEntity<?> getAnalytics(@PathVariable Long id){
        AnalyticsResponse response=urlService.getAnalytics(id);
        return  ResponseEntity.ok(response);
    }

}