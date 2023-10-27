package com.syano.service;

import com.syano.*;
import io.grpc.stub.StreamObserver;
import jakarta.inject.Singleton;

@Singleton
public class UrlService extends UrlServiceGrpc.UrlServiceImplBase {
    @Override
    public void createUrl(CreateUrlRequest request, StreamObserver<CreateUrlResponse> responseObserver) {
//        super.createUrl(request, responseObserver);]
//        responseObserver.onNext();
//        responseObserver.onCompleted();
        UrlEntity urlEntity = UrlEntity.newBuilder().setLongUrl(request.getLongUrl()).build();

        CreateUrlResponse createUrlResponse = CreateUrlResponse.newBuilder()
                .setUrlEntity(urlEntity)
                .build();
        System.out.println("Hello World");
        responseObserver.onNext(createUrlResponse);
        responseObserver.onCompleted();

    }

    @Override
    public void getUrl(GetUrlRequest request, StreamObserver<GetUrlResponse> responseObserver) {
        super.getUrl(request, responseObserver);
    }

    @Override
    public void updateUrl(UpdateUrlRequest request, StreamObserver<UpdateUrlResponse> responseObserver) {
        super.updateUrl(request, responseObserver);
    }

    @Override
    public void deleteUrl(DeleteUrlRequest request, StreamObserver<DeleteUrlResponse> responseObserver) {
        super.deleteUrl(request, responseObserver);
    }
}
