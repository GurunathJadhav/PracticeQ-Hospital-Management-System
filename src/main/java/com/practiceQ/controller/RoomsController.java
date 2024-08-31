package com.practiceQ.controller;

import com.practiceQ.entity.Room;
import com.practiceQ.payload.RoomsDto;
import com.practiceQ.service.RoomsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/rooms")
public class RoomsController {
    @Autowired
    private RoomsService roomsService;

    @PostMapping
    public ResponseEntity<?> createRoom(@Valid @RequestBody RoomsDto roomsDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return new ResponseEntity<>(bindingResult
                    .getFieldError()
                    .getDefaultMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        Room room = roomsService.createRoom(roomsDto);
        return new ResponseEntity<>(room,HttpStatus.CREATED);
    }
}
