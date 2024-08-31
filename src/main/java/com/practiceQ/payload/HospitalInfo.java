package com.practiceQ.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HospitalInfo {

    private String hospitalName;

    private String state;

    private String zipCode;

    private Long phoneNumber;
}
