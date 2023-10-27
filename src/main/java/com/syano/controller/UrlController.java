package com.syano.controller;

import com.syano.model.UrlEntity;
import com.syano.repository.UrlRepository;
import io.micronaut.data.annotation.GeneratedValue;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import jakarta.inject.Inject;

@Controller("/")
public class UrlController {
    @Inject
    private UrlRepository urlRepository;
}
