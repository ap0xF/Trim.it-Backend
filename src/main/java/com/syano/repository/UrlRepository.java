package com.syano.repository;

import com.syano.model.UrlEntity;
import io.micronaut.data.jpa.repository.JpaRepository;
import io.micronaut.data.mongodb.annotation.MongoRepository;

import org.bson.types.ObjectId;

import java.util.Optional;

@MongoRepository
public interface UrlRepository extends JpaRepository<UrlEntity, ObjectId> {
    public Optional<UrlEntity> findById(ObjectId id); // because findById expects
    // data type of Integer and ObjectId is a 12 bit data type used in mongodb.

    public UrlEntity findByShortUrl(String shortUrl);

    public void deleteByShortUrl(String shortUrl);

}
