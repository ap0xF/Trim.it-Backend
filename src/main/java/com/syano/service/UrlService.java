package com.syano.service;

import com.syano.*;
import com.syano.model.UrlEntity;
import com.syano.repository.UrlRepository;
import io.grpc.stub.StreamObserver;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import com.google.protobuf.Timestamp;
import org.bson.types.ObjectId;

import java.time.Instant;

@Singleton
public class UrlService extends UrlServiceGrpc.UrlServiceImplBase {

    @Inject
    private UrlRepository urlRepository;

    @Override
    public void createUrl(CreateUrlRequest createUrlRequest, StreamObserver<CreateUrlResponse> responseObserver) {
//        super.createUrl(request, responseObserver);]
//        responseObserver.onNext();
//        responseObserver.onCompleted();

        // user bata longURL ra Expiration Time lini
        Entity entity = Entity.newBuilder()
                .setLongUrl(createUrlRequest.getLongUrl())
//                .setExpirationTime(createUrlRequest.getExpirationTime())
                .build();
//
        UrlEntity urlEntity = new UrlEntity();
        urlEntity.setLongUrl(entity.getLongUrl());
        // expiration time ko lagi ali pachhi kaam garchhu.
//        urlEntity.setExpirationTime(Instant.ofEpochSecond(entity.getExpirationTime().getSeconds(), entity.getExpirationTime().getNanos()));



        // logic to create short url from the given long url


        urlRepository.save(urlEntity);



        CreateUrlResponse createUrlResponse = CreateUrlResponse.newBuilder()
                .setEntity(entity)
                .build();

        responseObserver.onNext(createUrlResponse);
//         Instead of sending out grpc response I need to create the short url here.
        responseObserver.onCompleted();

    }

    @Override
    public void getUrl(GetUrlRequest request, StreamObserver<GetUrlResponse> responseObserver) {
//        super.getUrl(request, responseObserver);

    }

//    @Override
//    public void updateUrl(UpdateUrlRequest request, StreamObserver<UpdateUrlResponse> responseObserver) {
//        super.updateUrl(request, responseObserver);
//    }

//    @Override
//    public void deleteUrl(DeleteUrlRequest request, StreamObserver<DeleteUrlResponse> responseObserver) {
//        super.deleteUrl(request, responseObserver);
//    }
}