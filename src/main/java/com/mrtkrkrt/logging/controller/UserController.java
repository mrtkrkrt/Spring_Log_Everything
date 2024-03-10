package com.mrtkrkrt.logging.controller;

import com.mrtkrkrt.logging.dto.request.GetUserRequest;
import com.mrtkrkrt.logging.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@RestController
public class UserController {

    // TODO log tracing sistemi geliştirilecek
    // TODO error case leri için ayrı log basılıp error lar handle edilecek

    @PostMapping("/getUserById")
    public ResponseEntity<User> getUser(@RequestBody(required = false) GetUserRequest request,
                                        @RequestHeader(value = "Authorization", required = false) String token) {
        return ResponseEntity.ok(new User("mrtkrkrt", "password"));
    }
}
