package com.festnode.festnode.service;
import com.festnode.festnode.model.Image;
import com.festnode.festnode.repository.ImageRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ImageService {
    @Autowired
    private ImageRepository imageRepo;

    public List<Image> getAllImg(){
        return imageRepo.findAll();
    }

    public Optional<Image> getImg(Long id){
        return imageRepo.findById(id);
    }

    public void saveImg(Image img){
        imageRepo.save(img);
    }

    public void deleteImg(Long id){
        imageRepo.deleteById(id);
    }

    public boolean checkImg(Long id){
        return imageRepo.existsById(id);
    }

}
