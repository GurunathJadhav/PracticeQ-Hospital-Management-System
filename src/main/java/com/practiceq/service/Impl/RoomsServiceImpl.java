package com.practiceq.service.Impl;

import com.practiceq.entity.Room;
import com.practiceq.exception.PatientNotFoundException;

import com.practiceq.exception.StaffNotFoundException;
import com.practiceq.repository.PatientRepository;
import com.practiceq.payload.RoomsDto;

import com.practiceq.repository.RoomRepository;
import com.practiceq.repository.StaffRepository;
import com.practiceq.service.RoomsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RoomsServiceImpl implements RoomsService {

    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private StaffRepository staffRepository;
    @Autowired
    private PatientRepository patientRepository;

    @Override
    public Room createRoom(RoomsDto roomsDto) {
        Room room = new Room();
        room.setRoomId(UUID.randomUUID().toString());
        room.setAdmissionDate(roomsDto.getAdmissionDate());
        room.setTotalBeds(roomsDto.getTotalBeds());
        room.setStaff(staffRepository.findById(roomsDto.getStaffId())
                .orElseThrow(()-> new StaffNotFoundException("Staff not found")));
        room.setPatient(patientRepository.findById(roomsDto.getPatientId())
                .orElseThrow(()-> new PatientNotFoundException("Patient not found found for id "+roomsDto.getPatientId())));

        return roomRepository.save(room);
    }
}
