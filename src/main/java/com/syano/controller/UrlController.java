package com.syano.controller;

import com.syano.model.UrlEntity;
import com.syano.repository.UrlRepository;
import io.micronaut.data.annotation.GeneratedValue;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import jakarta.inject.Inject;
import org.bson.types.ObjectId;

import java.util.Optional;

@Controller("/")
public class UrlController {
    @Inject
    private UrlRepository urlRepository;

}
