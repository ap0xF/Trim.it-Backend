package com.syano.service;

import com.syano.*;
import com.syano.model.UrlEntity;
import com.syano.repository.UrlRepository;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import io.micronaut.data.exceptions.EmptyResultException;
import io.seruco.encoding.base62.Base62;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.validation.constraints.NotNull;
import org.bson.types.ObjectId;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

@Singleton
public class UrlService extends UrlServiceGrpc.UrlServiceImplBase {

    @Inject
    private UrlRepository urlRepository;

    @Override
    public void createUrl(CreateUrlRequest createUrlRequest, StreamObserver<CreateUrlResponse> responseObserver) {

//        user bata longURL ra Expiration Time lini
//        Check if the given longUrl is already in db, then update the shorturl of the corressponding document.
        if(!createUrlRequest.getLongUrl().isEmpty()){
            Entity entity = Entity.newBuilder()
                    .setLongUrl(createUrlRequest.getLongUrl())
//                .setExpirationTime(createUrlRequest.getExpirationTime())
                    .build();
            UrlEntity urlEntity = new UrlEntity();
            urlEntity.setLongUrl(entity.getLongUrl());
//            System.out.println(entity.getLongUrl());

//            expiration time ko lagi ali pachhi kaam garchhu.
//        urlEntity.setExpirationTime(Instant.ofEpochSecond(entity.getExpirationTime().getSeconds(), entity.getExpirationTime().getNanos()));


//            logic to create short url from the given long url
//            To-Do:
//            - url validation

//          1. generating hash of long url using sha-256
            byte[] hash = new byte[0]; // creating empty array of byte.
            StringBuilder hexOfHash = new StringBuilder();
            String short_url = "";
            if(!urlEntity.getLongUrl().isEmpty()){
                String long_url = urlEntity.getLongUrl();
//            System.out.println(long_url);
                try{
                    MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
                    hash = messageDigest.digest(long_url.getBytes(StandardCharsets.UTF_8));
                } catch (Exception e){
                    throw new RuntimeException(e);
                }

                for(byte b: hash){
                    hexOfHash.append(String.format("%02x", b));
                }

//                 implement base62 encoding to the hex value.
                Base62 base62 = Base62.createInstance();
                String base = base62.encode(hexOfHash.toString().getBytes()).toString();

                short_url = base.substring(3);
            }
//             adding shortUrl to db
            urlEntity.setShortUrl(short_url);

            urlRepository.save(urlEntity);

//             creating entity to send back to user
            Entity entityToBeSent = Entity.newBuilder()
                    .setLongUrl(urlEntity.getLongUrl())
                    .setShortUrl(urlEntity.getShortUrl())
//                .setExpirationTime(Timestamp.newBuilder().setSeconds(urlEntity.getExpirationTime().getEpochSecond()).setNanos(urlEntity.getExpirationTime().getNano()).build())
                    .build();

//             sending back response through grpc.
            CreateUrlResponse createUrlResponse = CreateUrlResponse.newBuilder()
                    .setEntity(entityToBeSent)
                    .build();

            responseObserver.onNext(createUrlResponse);
            responseObserver.onCompleted();
        }

        else {
            Status status = Status.INTERNAL.withDescription("Empty URL provided");
            responseObserver.onError(status.asRuntimeException());
        }
    }

    @Override
    public void getUrl(GetUrlRequest getUrlRequest, StreamObserver<GetUrlResponse> responseObserver) {
//         user le short url browser ma halesi teslai resolve garera long url dini kaam yesko ho.
//        System.out.println(getUrlRequest.getShortUrl());
        if(!getUrlRequest.getShortUrl().isEmpty()){
            Entity entity = Entity.newBuilder()
                    .setShortUrl(getUrlRequest.getShortUrl())
                    .build();

            String longUrl = "";

            try{
                UrlEntity urlEntity = urlRepository.findByShortUrl(entity.getShortUrl());
                longUrl =  urlEntity.getLongUrl();
                GetUrlResponse getUrlResponse = GetUrlResponse.newBuilder()
                        .setLongUrl(longUrl)
                        .build();

                responseObserver.onNext(getUrlResponse);
                responseObserver.onCompleted();
            } catch (EmptyResultException emptyResultException){
                Status status = Status.NOT_FOUND.withDescription("Long URL not found in database");
                responseObserver.onError(status.asRuntimeException());
            }
        }

        else{
            Status status = Status.INTERNAL.withDescription("Empty URL provided");
            responseObserver.onError(status.asRuntimeException());
        }
    }

    public String getLongUrl(String shortUrl){
        try{
            return urlRepository.findByShortUrl(shortUrl).getLongUrl();
        } catch (EmptyResultException emptyResultException){
            return "Long URL not found in database";
        }
    }

    @Override
    public void updateUrl(UpdateUrlRequest request, StreamObserver<UpdateUrlResponse> responseObserver) {
//        IDK what updating url mean yet.
    }

    @Override
    public void deleteUrl(DeleteUrlRequest deleteUrlRequest, StreamObserver<DeleteUrlResponse> responseObserver) {
        if(!deleteUrlRequest.getShortUrl().isEmpty()){
            try{
                if(!urlRepository.findByShortUrl(deleteUrlRequest.getShortUrl()).getLongUrl().isEmpty()){
                    try{
                        urlRepository.deleteByShortUrl(deleteUrlRequest.getShortUrl());
                        responseObserver.onNext(DeleteUrlResponse.newBuilder().setStatus("Data Deleted Successfully").build());
                        responseObserver.onCompleted();
                    } catch (Exception e){
                        Status status = Status.UNKNOWN.withDescription("Some unknown error occurred while trying to delete data");
                        responseObserver.onError(status.asRuntimeException());
                    }
                }

            }
            catch (EmptyResultException e){
                Status status = Status.NOT_FOUND.withDescription("Given url is not present in Database");
                responseObserver.onError(status.asRuntimeException());
            }
        }
        else{
            Status status = Status.INTERNAL.withDescription("Empty URL provided");
            responseObserver.onError(status.asRuntimeException());
        }
    }
}