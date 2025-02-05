package com.festnode.festnode.service;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.text.IconView;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class CloudinaryService {

    Cloudinary cloudinary;

    public CloudinaryService(){
        Map<String, String> valusMap = new HashMap<>();
        valusMap.put("cloud_name", "diqenilxz");
        valusMap.put("api_key", "784189399539485");
        valusMap.put("api_secret", "bTzpjXtj5ER8B-XaMO226w_ZSKU");
        cloudinary = new Cloudinary(valusMap);
    }

    public Map upload(MultipartFile multipartFile) throws IOException{
        File file = convertToFile(multipartFile);
        Map<String, String> uploadOptions = ObjectUtils.asMap(
                "folder", "festnode"
        );
        Map result = cloudinary.uploader().upload(file, uploadOptions);
        if(!Files.deleteIfExists(file.toPath())){
            throw new IOException("Failed to delete temporary file : " + file.getAbsolutePath());
        }
        return result;
    }

    public Map delete(String id) throws IOException{
        return cloudinary.uploader().destroy(id, ObjectUtils.emptyMap());
    }

    private File convertToFile(MultipartFile multipartFile) throws IOException{
        File file = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        FileOutputStream fOut = new FileOutputStream(file);
        fOut.write(multipartFile.getBytes());
        fOut.close();
        return file;
    }


}
