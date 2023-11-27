package com.syano.controller;
import com.syano.service.UrlService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;

@Controller("/")
public class UrlController {

    @Inject
    private UrlService urlService;
    private static final Logger LOG = LoggerFactory.getLogger(UrlController.class);
    @Get("/{path}")
    public HttpResponse<?> redirect(String path) {
        String longUrl = urlService.getLongUrl(path);
        try{
            URI uri = URI.create(longUrl);
            return HttpResponse.redirect(uri);
        } catch (Exception e){
            LOG.error("Error while parsing url: {}", longUrl);
            return HttpResponse.badRequest();
        }
    }
}
