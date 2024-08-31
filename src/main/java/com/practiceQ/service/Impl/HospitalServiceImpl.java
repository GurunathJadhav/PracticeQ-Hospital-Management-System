package com.practiceQ.service.Impl;

import com.practiceQ.entity.Hospital;
import com.practiceQ.repository.HospitalRepository;
import com.practiceQ.service.HospitalService;
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
