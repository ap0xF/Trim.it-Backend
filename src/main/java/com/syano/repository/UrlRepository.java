package com.syano.repository;

import com.syano.model.UrlEntity;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;

@Repository
public interface UrlRepository extends JpaRepository<UrlEntity, Integer> {

}
