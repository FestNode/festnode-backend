package com.festnode.festnode.controller;

import com.festnode.festnode.model.Image;
import com.festnode.festnode.service.CloudinaryService;
import com.festnode.festnode.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/public/cloudinary")
public class CloudinaryController {

    @Autowired
    public CloudinaryService cloudinaryService;

    @Autowired
    public ImageService imageService;


    @PostMapping("/upload")
    public ResponseEntity<String> upload(@RequestParam MultipartFile multipartFile) throws IOException{
        BufferedImage buffImg = ImageIO.read(multipartFile.getInputStream());
        if(buffImg == null){
            return new ResponseEntity<>("Image not valid!", HttpStatus.BAD_REQUEST);
        }
        Map result = cloudinaryService.upload(multipartFile);
        Image image = new Image((String) result.get("original_filename"),
                                (String) result.get("url"),
                                (String) result.get("public_id"));

        imageService.saveImg(image);
        return new ResponseEntity<>("Image Uploaded successfully !", HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        Optional<Image> imageOptional = imageService.getImg(id);
        if (imageOptional.isEmpty()) {
            return new ResponseEntity<>("Image not found!", HttpStatus.NOT_FOUND);
        }
        Image image = imageOptional.get();
        String cloudinaryImageId = image.getImageId();
        try {
            cloudinaryService.delete(cloudinaryImageId);
        } catch (IOException e) {
            return new ResponseEntity<>("Failed to delete image from Cloudinary", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        imageService.deleteImg(id);
        return new ResponseEntity<>("The Image Deleted Successfully !", HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<List<Image>> list(){
        List<Image> list = imageService.getAllImg();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
