package com.mrtkrkrt.logging.controller;

import com.mrtkrkrt.logging.dto.request.GetUserRequest;
import com.mrtkrkrt.logging.model.User;
import com.mrtkrkrt.logging.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    @PostMapping("/getUserById")
    public ResponseEntity<User> getUser(@RequestBody GetUserRequest request,
                                        @RequestHeader(name = "x-session-token") String token) {
        return ResponseEntity.ok(userService.getUser(request.getId()));
    }
}
