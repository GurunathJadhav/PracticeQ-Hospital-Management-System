package com.practiceQ.payload;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceDto {

    private String serviceId;

    @NotEmpty(message = " service Name must not be empty")
    private String serviceName;

    private Double cost;
    @NotEmpty(message = " Invoice Id must not be empty")
    private String invoiceId;
}