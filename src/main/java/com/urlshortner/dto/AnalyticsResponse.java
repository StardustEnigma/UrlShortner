package com.urlshortner.dto;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AnalyticsResponse {

    String shortCode;

    String originalUrl;

    Long clickCount;

    LocalDateTime createdAt;


}
