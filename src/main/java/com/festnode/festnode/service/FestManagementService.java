package com.festnode.festnode.service;

import com.festnode.festnode.exception.DuplicateFestException;
import com.festnode.festnode.exception.EntityNotFoundException;
import com.festnode.festnode.model.FestDetails;
import com.festnode.festnode.model.Image;
import com.festnode.festnode.repository.FestDetailsRepository;
import com.festnode.festnode.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class FestManagementService {
    @Autowired
    private FestDetailsRepository festRepo;

    @Autowired
    public CloudinaryService cloudinaryService;

    @Autowired
    public ImageRepository imgRepo;
    public FestDetails addNewFest(String festName, String desc, Image logo) {
        if(festRepo.findById(10001L).isPresent()){
            throw new DuplicateFestException("One fest is already available! You can add only one fest at a time!");
        }else {
            FestDetails fest = new FestDetails();
            fest.setId(10001L);
            fest.setFestName(festName);
            fest.setFestDescription(desc);
            fest.setLogo(logo);
            return festRepo.save(fest);
        }
    }

    public FestDetails getFestDetails(Long festId) {
        return festRepo.findById(festId).orElseThrow(() -> new EntityNotFoundException("Fest Not Found"));
    }

    public String deleteFestDetails() throws IOException {
        String res = "";
        cloudinaryService.delete(festRepo.findById(10001L).get().getLogo().getImageId());
        Long imgageId = festRepo.findById(10001L).get().getLogo().getImgId();
        festRepo.deleteById(10001L);
        if(festRepo.existsById(10001L)){
            res = "Unable to delete the Fest";
        }else{
            imgRepo.deleteById(imgageId);
            res = "Fest Deleted Successfully";
        }
        return res;
    }
}
