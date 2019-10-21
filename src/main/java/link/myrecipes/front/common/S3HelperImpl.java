package link.myrecipes.front.common;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Component
@Slf4j
public class S3HelperImpl implements S3Helper {
    private final String S3_DOMAIN = "s3.{region}.amazonaws.com";

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.region.static}")
    private String region;

    private final S3Client s3Client;

    public S3HelperImpl(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    @Override
    public String upload(MultipartFile multipartFile, String path) throws IOException {
        File uploadFile = convert(multipartFile)
                .orElseThrow(() -> new IllegalArgumentException("[MultipartFile -> File] Convert Fail"));

        return upload(uploadFile, path);
    }

    private String upload(File uploadFile, String path) {
        String fileName = path + "/" + uploadFile.getName();
        return putObject(uploadFile, fileName);
    }

    private String putObject(File uploadFile, String fileName) {
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(this.bucket)
                .key(fileName)
                .acl(ObjectCannedACL.PUBLIC_READ)
                .build();
        s3Client.putObject(putObjectRequest, RequestBody.fromFile(uploadFile));
        return makeObjectUrl(fileName);
    }

    private Optional<File> convert(MultipartFile file) throws IOException {
        File convertFile = new File(System.getProperty("java.io.tmpdir")
                + System.getProperty("file.separator")
                + UUID.randomUUID().toString()
                + "." + FilenameUtils.getExtension(file.getOriginalFilename()));
        if (convertFile.createNewFile()) {
            try (FileOutputStream fos = new FileOutputStream(convertFile)) {
                fos.write(file.getBytes());
            }
            return Optional.of(convertFile);
        }

        return Optional.empty();
    }

    private String makeObjectUrl(String fileName) {
        return "https://" + this.S3_DOMAIN.replace("{region}", this.region) + "/" + this.bucket + "/" + fileName;
    }
}
