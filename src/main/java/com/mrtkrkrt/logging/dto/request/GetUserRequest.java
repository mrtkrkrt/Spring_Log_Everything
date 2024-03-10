package com.mrtkrkrt.logging.dto.request;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetUserRequest implements Serializable {
    private Long id;
}
