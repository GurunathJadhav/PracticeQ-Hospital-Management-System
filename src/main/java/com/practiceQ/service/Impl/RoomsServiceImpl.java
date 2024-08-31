package com.practiceQ.service.Impl;

import com.practiceQ.entity.Room;
import com.practiceQ.exception.PatientNotFoundException;

import com.practiceQ.exception.StaffNotFoundException;
import com.practiceQ.repository.PatientRepository;
import com.practiceQ.payload.RoomsDto;

import com.practiceQ.repository.RoomRepository;
import com.practiceQ.repository.StaffRepository;
import com.practiceQ.service.RoomsService;
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
