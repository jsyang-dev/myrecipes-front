package io.myrecipes.front.common;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@WebMvcTest(S3Uploader.class)
public class S3UploaderTest {
    @Autowired
    private S3Uploader s3Uploader;

    @Test
    public void S3_업로드_테스트() throws IOException {
        MockMultipartFile mockMultipartFile = new MockMultipartFile("test.txt", "test.txt", "text/plain", "test data".getBytes());
        s3Uploader.upload(mockMultipartFile, "/img");
    }
}