package com.practiceq.service;

import com.practiceq.entity.Room;
import com.practiceq.payload.RoomsDto;

public interface RoomsService {

    Room createRoom(RoomsDto roomsDto);
}
