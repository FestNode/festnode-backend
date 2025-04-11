package com.festnode.festnode.controller;

import com.festnode.festnode.model.Control;
import com.festnode.festnode.model.FestDetails;
import com.festnode.festnode.service.FestManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public")
public class FestController {

    @Autowired
    private FestManagementService festService;

    @GetMapping("/festStatus")
    public ResponseEntity<Boolean> checkFestStatus(){
        Control control = festService.getFestStatus();
        return new ResponseEntity<>(control.isActive(), HttpStatus.OK);
    }

    @GetMapping("/getFestDetails")
    public ResponseEntity<FestDetails> getFest(){
        return new ResponseEntity<>(festService.getFestDetails(10001L), HttpStatus.OK);
    }
}
