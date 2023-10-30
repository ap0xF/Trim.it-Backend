package com.syano.model;

import com.google.protobuf.Timestamp;
import io.micronaut.data.annotation.*;

import io.micronaut.data.model.query.QueryModel;
import jakarta.persistence.GenerationType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import org.bson.types.ObjectId;
import org.hibernate.boot.registry.selector.spi.StrategyCreator;

import java.time.Instant;
import org.bson.*;

@MappedEntity
public class UrlEntity {
    @Id
    private ObjectId id;

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    @NotNull
    private String longUrl;
    @NotNull
    private String shortUrl;
    @DateCreated
    private Instant createdAt;
    @DateUpdated
    private Instant updatedAt;
    private int clickCount;
//    private Timestamp expirationTime;


    public String getLongUrl() {
        return longUrl;
    }

    public void setLongUrl(String longUrl) {
        this.longUrl = longUrl;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public int getClickCount() {
        return clickCount;
    }

    public void setClickCount(int clickCount) {
        this.clickCount = clickCount;
    }


}

