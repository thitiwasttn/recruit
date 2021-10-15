package com.thitiwas.recruit.recruit.service;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@Service
public class FileService {

    public String getFileExtension(MultipartFile file) {
        return FilenameUtils.getExtension(file.getOriginalFilename());
    }

    /**
     * location path<br/>
     * file name<br/>
     * file<br/>
     * */
    public void write(String videoLocation, String fileName, MultipartFile file) throws IOException {

        InputStream initialStream = file.getInputStream();
        byte[] buffer = new byte[initialStream.available()];
        initialStream.read(buffer);

        File targetFile = new File(videoLocation.concat("/").concat(fileName));

        try (OutputStream outStream = new FileOutputStream(targetFile)) {
            outStream.write(buffer);
        }
    }
}
