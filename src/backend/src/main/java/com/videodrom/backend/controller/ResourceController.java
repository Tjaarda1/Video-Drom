package com.videodrom.backend.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.videodrom.backend.service.ResourceService;

@RestController
@RequestMapping("/images")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET})
public class ResourceController {
    
@GetMapping(value = "/{imageKey}", produces = MediaType.IMAGE_PNG_VALUE)
public @ResponseBody byte[] getImage(@PathVariable String imageKey) throws IOException {

     byte[] bytes = Files.readAllBytes(Paths.get(ResourceService.UPLOAD_DIR + "/" + imageKey + ".png"));

     return bytes;
    }
}
