package com.urlshortner.service;

import com.urlshortner.dto.AnalyticsResponse;
import com.urlshortner.model.Url;
import com.urlshortner.repository.UrlRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class UrlServiceImpl implements UrlService {

    private final UrlRepository urlRepository;

    public UrlServiceImpl(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    @Override
    public String generateCode() {
        String code;
        do{
            code= UUID.randomUUID().toString().substring(0,6);
        }while (urlRepository.findByShortCode(code).isPresent());
        return code;
    }

    @Override
    public String saveUrl(String url) {
        Optional<Url> existing= urlRepository.findByOriginalUrl(url);

        if (existing.isPresent()){
            return existing.get().getShortCode();
        }
        Url newUrl=new Url();
        newUrl.setOriginalUrl(url);
        String code=generateCode();
        newUrl.setShortCode(code);
        newUrl.setCreatedAt(LocalDateTime.now());
        newUrl.setClickCount(0L);
        urlRepository.save(newUrl);
        return newUrl.getShortCode();
    }

    @Override
    public String GetOriginalUrl(String code) {
        Url url= urlRepository.findByShortCode(code)
                .orElseThrow(()-> new RuntimeException("Url not found"));
        url.setClickCount(url.getClickCount()+1);
        urlRepository.save(url);

        return url.getOriginalUrl();
    }

    @Override
    public AnalyticsResponse getAnalytics(Long urlId) {
        Url url=urlRepository.findById(urlId).orElseThrow(()->new RuntimeException("Url not found"));

        AnalyticsResponse response=new AnalyticsResponse();
        response.setShortCode(url.getShortCode());
        response.setOriginalUrl(url.getOriginalUrl());
        response.setClickCount(url.getClickCount());
        response.setCreatedAt(url.getCreatedAt());
        return response;
    }

}
