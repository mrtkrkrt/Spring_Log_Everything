package com.mrtkrkrt.logging.service;

import com.mrtkrkrt.logging.client.UserInformationClient;
import com.mrtkrkrt.logging.dto.request.GetUserInformationRequest;
import com.mrtkrkrt.logging.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserInformationClient userInformationClient;

    public User getUser(String id) {
        return userInformationClient.getUser(GetUserInformationRequest.builder().id(id).build()).getBody();
    }
}
