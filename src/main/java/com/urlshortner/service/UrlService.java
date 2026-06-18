package com.urlshortner.service;


import com.urlshortner.dto.AnalyticsResponse;
import com.urlshortner.model.Url;

public interface UrlService {

    String generateCode();

    String saveUrl(String url);

    String GetOriginalUrl(String code);

    AnalyticsResponse getAnalytics(Long urlId);
}
