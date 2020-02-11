package com.example.photogallery;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.photogallery.model.Image;
import com.example.photogallery.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Map;

@Service
public class ImageUploader {

    private Cloudinary cloudinary;
    private ImageRepository imageRepository;

    @Autowired
    public ImageUploader(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
        String cloudNameValue = "dmjqrpwe0";
        String apiKeyValue = "291285242221637";
        String apiSecretValue = "UcYSDVjjcsfpguNUj-mHeIWCvrw";
        cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", cloudNameValue,
                "api_key", apiKeyValue,
                "api_secret", apiSecretValue));
    }

    public String uploadFileAndSaveToDb(String path) {
        File file = new File(path);
        Map uploadResult = null;
        try {
            uploadResult = cloudinary.uploader().upload(file, ObjectUtils.emptyMap());
            imageRepository.save(new Image(uploadResult.get("url").toString()));
        } catch (IOException e) {
            // todo
        }
        return uploadResult.get("url").toString();
    }


}
