package com.videodrom.backend.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ResourceServiceImpl implements ResourceService{


    @Value("${gateway.go-gmpd-url}")
    private String goGmpdUrl;

    @Value("${gateway.go-gmpd-port}")
    private String goGmpdPort;

    @Override
    public void sendMovieToGoServer(MultipartFile mp4File, String movieKey) {
        RestTemplate restTemplate = new RestTemplate();

        String goServerUrl = "http://" + goGmpdUrl + ":" + goGmpdPort + "/upload";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
       body.add("video", new FileSystemResource(convertToFile(mp4File,movieKey + ".mp4")));
        // body.add("title", "Your Movie Title"); // Replace with your movie title
        // body.add("thumbnail", "Your Thumbnail Content"); // Replace with your thumbnail content

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(goServerUrl, requestEntity, String.class);

            System.out.println(response);
        } catch(HttpClientErrorException httpClientErrorException) {
            System.err.println("Alo");
        }

       // System.out.println(response);
        // You can handle the response if needed
        // String responseBody = response.getBody();
    }
    private File convertToFile(MultipartFile multipartFile, String fileName) {
        File convFile = new File(System.getProperty("java.io.tmpdir")+"/"+fileName);
        try {
            multipartFile.transferTo(convFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return convFile;
    }
    

    @Override
    public void saveJPGThumbnail(MultipartFile jpgFile, String title) {
         if (jpgFile.isEmpty()) {
            throw new IllegalArgumentException("Image file is empty");
        }

        // Generate a unique filename to avoid overwriting existing files
        String fileName = title + ".png";
        Path uploadPath = Paths.get(UPLOAD_DIR).toAbsolutePath().normalize();

        try {
            // Create the upload directory if it doesn't exist
            Files.createDirectories(uploadPath);
            
            // Save the image to the upload directory
            Path targetLocation = uploadPath.resolve(fileName);
            Files.copy(jpgFile.getInputStream(), targetLocation);

        } catch (IOException ex) {
           ex.printStackTrace();
        }
    }
    
}

