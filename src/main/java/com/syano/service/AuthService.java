package com.syano.service;

import com.auth.*;
import com.syano.model.AuthEntity;
import com.syano.repository.AuthRepository;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import io.micronaut.data.exceptions.EmptyResultException;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
@Singleton
public class AuthService extends AuthServiceGrpc.AuthServiceImplBase{
    @Inject
    private AuthRepository authRepository;

    @Override
    public void login(LoginRequest loginRequest, StreamObserver<LoginResponse> responseObserver) {
        String email = loginRequest.getEmail();
        try{
            AuthEntity entity = authRepository.findByEmail(email);
            if(entity.getEmail().equals(email)){
                LoginResponse loginResponse = LoginResponse.newBuilder()
                        .setHash(entity.getPassword())
                        .build();
                responseObserver.onNext(loginResponse);
                responseObserver.onCompleted();
            }
        } catch (EmptyResultException e){
            responseObserver.onError(Status.NOT_FOUND.withDescription("Given Email does not have an account, please signup first.").asRuntimeException());
            responseObserver.onCompleted();
        } catch (Exception e){
            responseObserver.onError(Status.INTERNAL.withDescription(e.getMessage()).asRuntimeException());
            responseObserver.onCompleted();
        }
    }

    @Override
    public void signup(SignupRequest signupRequest, StreamObserver<SignupResponse> responseObserver) {
        String email = signupRequest.getEmail();
        String passwordHash = signupRequest.getPasswordHash();
        String firstName = signupRequest.getFirstName();
        String lastName = signupRequest.getLastName();
        if(!email.isEmpty() && !passwordHash.isEmpty() && !firstName.isEmpty() && !lastName.isEmpty()){
            try {
                if (authRepository.findByEmail(email) != null) {
                    throw new IllegalArgumentException("Email already taken, please use another email");
                }
            } catch (IllegalArgumentException e) {
                responseObserver.onError(Status.ALREADY_EXISTS.withDescription(e.getMessage()).asRuntimeException());
            } catch (EmptyResultException e){
                AuthEntity authEntity = new AuthEntity();
                authEntity.setEmail(email);
                authEntity.setPassword(passwordHash);
                authEntity.setFirstName(firstName);
                authEntity.setLastName(lastName);
                authRepository.save(authEntity);

                SignupResponse signupResponse = SignupResponse.newBuilder()
                        .setSuccess(true)
                        .setMessage("Signup Successful")
                        .build();
                responseObserver.onNext(signupResponse);
                responseObserver.onCompleted();
            } catch (Exception e) {
                responseObserver.onError(Status.INTERNAL.withDescription(e.getMessage()).asRuntimeException());
            }

        } else {
            responseObserver.onError(Status.INVALID_ARGUMENT.withDescription("Email, Password, First Name and Last Name cannot be empty").asRuntimeException());
        }
    }

}
