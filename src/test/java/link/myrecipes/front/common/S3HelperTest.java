package link.myrecipes.front.common;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.endsWith;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = "spring.config.location="
        + "classpath:/application.yml,"
        + "classpath:/aws.yml"
)
public class S3HelperTest {
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.region.static}")
    private String region;

    @Autowired
    private S3Helper s3Helper;

    @Test
    public void Should_URL_리턴_When_S3_업로드_성공() throws IOException {
        MockMultipartFile mockMultipartFile = new MockMultipartFile("test.txt", "test.txt", "text/plain", "test data".getBytes());
        String path = "img";

        final String imageUrl = this.s3Helper.upload(mockMultipartFile, path);

        assertThat(imageUrl, startsWith("https://s3." + this.region + ".amazonaws.com/" + this.bucket + "/" + path + "/"));
        assertThat(imageUrl, endsWith(".txt"));
    }
}