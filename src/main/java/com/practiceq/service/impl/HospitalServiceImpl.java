package com.practiceq.service.impl;

import com.practiceq.entity.Hospital;
import com.practiceq.repository.HospitalRepository;
import com.practiceq.service.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class HospitalServiceImpl implements HospitalService {

    @Autowired
    private HospitalRepository hospitalRepository;
    @Override
    public Hospital createHospital(Hospital hospital) {
        hospital.setHospitalId(UUID.randomUUID().toString());
        return hospitalRepository.save(hospital);
    }
}
