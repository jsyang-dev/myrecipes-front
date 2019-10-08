package io.myrecipes.front.common;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface S3Helper {
    String upload(MultipartFile multipartFile, String path) throws IOException;
}
