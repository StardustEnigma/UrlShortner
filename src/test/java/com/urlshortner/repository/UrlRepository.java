package com.urlshortner.repository;

import com.urlshortner.model.Url;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UrlRepository extends JpaRepository<Url,Long> {
    Optional<String> findByShortCode(String code);

    Optional<Url> findByOriginalUrl(String url);
}
