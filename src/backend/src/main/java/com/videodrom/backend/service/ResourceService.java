package com.videodrom.backend.service;

import org.springframework.web.multipart.MultipartFile;

public interface ResourceService {

    public static final String UPLOAD_DIR = "./images"; // Change this to your desired upload directory

    public void sendMovieToGoServer(MultipartFile mp4File, String title);

    public void saveJPGThumbnail(MultipartFile jpgFile, String title);

}
