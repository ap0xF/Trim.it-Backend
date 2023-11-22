package com.syano.repository;

import com.syano.model.AuthEntity;
import com.syano.model.UrlEntity;
import io.micronaut.data.jpa.repository.JpaRepository;
import io.micronaut.data.mongodb.annotation.MongoRepository;
import org.bson.types.ObjectId;

import java.util.Optional;

@MongoRepository
public interface AuthRepository extends JpaRepository<AuthEntity, ObjectId> {
    AuthEntity findByEmail(String email);
}
