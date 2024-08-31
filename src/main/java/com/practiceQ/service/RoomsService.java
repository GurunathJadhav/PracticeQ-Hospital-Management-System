package com.practiceQ.service;

import com.practiceQ.entity.Room;
import com.practiceQ.payload.RoomsDto;

public interface RoomsService {

    Room createRoom(RoomsDto roomsDto);
}
