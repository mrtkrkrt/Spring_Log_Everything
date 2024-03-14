package com.mrtkrkrt.logging.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetUserInformationRequest {
    private String id;
}
