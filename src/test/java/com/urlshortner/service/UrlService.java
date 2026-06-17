package com.urlshortner.service;

import com.urlshortner.Dto.ResonseUrlDto;
import com.urlshortner.model.Url;


public interface UrlService {

    String generateCode();

    String saveUrl(Url url);

    String GetOriginalUrl(String code);
}
