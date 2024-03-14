package com.mrtkrkrt.logging.client;

import com.mrtkrkrt.logging.dto.request.GetUserInformationRequest;
import com.mrtkrkrt.logging.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "user-information-service", url = "http://localhost:8081")
public interface UserInformationClient {

    @PostMapping("/api/v1/user/getUserById")
    public ResponseEntity<User> getUser(GetUserInformationRequest request);
}
