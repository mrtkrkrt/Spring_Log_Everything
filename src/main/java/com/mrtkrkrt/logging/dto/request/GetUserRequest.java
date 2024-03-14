package com.mrtkrkrt.logging.dto.request;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetUserRequest implements Serializable {
    private String id;
}
