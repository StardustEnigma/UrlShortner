package com.urlshortner.service;

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

        urlRepository.save(newUrl);
        return newUrl.getShortCode();
    }

    @Override
    public String GetOriginalUrl(String code) {
        Url url= urlRepository.findByShortCode(code)
                .orElseThrow(()-> new RuntimeException("Url not found"));

        return url.getOriginalUrl();
    }
}
