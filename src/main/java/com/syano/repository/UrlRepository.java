package com.syano.repository;

import com.syano.model.UrlEntity;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.jpa.repository.JpaRepository;
import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;
import io.micronaut.data.mongodb.annotation.MongoRepository;

import org.bson.types.ObjectId;

import java.util.List;
import java.util.Optional;

@MongoRepository
public interface UrlRepository extends JpaRepository<UrlEntity, ObjectId> {
    Optional<UrlEntity> findById(ObjectId id); // because findById expects

    // data type of Integer and ObjectId is a 12 bit data type used in mongodb.  //need more research on this.
    UrlEntity findByShortUrl(String shortUrl);

    UrlEntity findByLongUrl(String longUrl); // to check if long url already exists in db.

    List<UrlEntity> findByEmail(String email);



    void updateByLongUrl(String longUrl, String shortUrl);

    void deleteByShortUrl(String shortUrl);

    void deleteByEmailAndShortUrl(String email, String shortUrl);

}
