package com.festnode.festnode.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.festnode.festnode.model.Control;
import com.festnode.festnode.model.Department;
import com.festnode.festnode.model.FestDetails;
import com.festnode.festnode.model.Image;
import com.festnode.festnode.service.CloudinaryService;
import com.festnode.festnode.service.DepartmentService;
import com.festnode.festnode.service.FestManagementService;
import com.festnode.festnode.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/secure")
public class AdminController {
    @Autowired
    private FestManagementService festService;

    @Autowired
    public CloudinaryService cloudinaryService;

    @Autowired
    public ImageService imageService;

    @Autowired
    public DepartmentService deptService;

    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    @PostMapping("/controlFest")
    public ResponseEntity<Control> controlFest(){
        Control control = festService.festFest();
        return new ResponseEntity<>(control, HttpStatus.OK);
    }


    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    @PostMapping("/addFest")
    public ResponseEntity<FestDetails> addFest(@RequestParam String festName, @RequestParam String desc, @RequestParam MultipartFile multipartFile) throws IOException {
        BufferedImage buffImg = ImageIO.read(multipartFile.getInputStream());
        Map result = cloudinaryService.upload(multipartFile);
        Image image = new Image((String) result.get("original_filename"),
                (String) result.get("url"),
                (String) result.get("public_id"));

        imageService.saveImg(image);
        FestDetails fest = festService.addNewFest(festName, desc, image);

        return new ResponseEntity<>(fest, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    @PostMapping("/addDepartment")
    public ResponseEntity<Department> addDept(@RequestBody JsonNode node){
        Department department = deptService.createDepartment(node);
        return new ResponseEntity<>(department, HttpStatus.CREATED);
    }


    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    @DeleteMapping("/deleteFest")
    public ResponseEntity<String> deleteFest() throws IOException {
        return new ResponseEntity<>(festService.deleteFestDetails(), HttpStatus.OK);
    }
}
