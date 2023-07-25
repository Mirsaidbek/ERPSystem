package dev.said.firebase;

import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.UUID;

import static dev.said.firebase.MediaUtils.*;


@Service
public class MediaService {


    public String uploadByPath(String path, String fileUniqueId) throws IOException {
        StorageOptions
                .newBuilder()
                .setCredentials(getCredentials())
                .build()
                .getService()
                .createFrom(getBlobInfo(fileUniqueId, null), Paths.get(path));

        return String.format(DOWNLOAD_URL, URLEncoder.encode(fileUniqueId, StandardCharsets.UTF_8));
    }

    @SneakyThrows
    public String upload(MultipartFile file) {
        String fileName = UUID.randomUUID() + file.getOriginalFilename();
        StorageOptions
                .newBuilder()
                .setCredentials(getCredentials())
                .build()
                .getService()
                .create(getBlobInfo(fileName, file.getContentType()), file.getBytes());
        return String.format(DOWNLOAD_URL, URLEncoder.encode(fileName, StandardCharsets.UTF_8));
    }

    public String uploadByInputStream(InputStream inputStream, String fileUniqueId) throws IOException {
        StorageOptions
                .newBuilder()
                .setCredentials(getCredentials())
                .build()
                .getService()
                .createFrom(getBlobInfo(fileUniqueId, "image/jpeg"),
                        inputStream,
                        Storage.BlobWriteOption.detectContentType());
        return String.format(DOWNLOAD_URL, URLEncoder.encode(fileUniqueId, StandardCharsets.UTF_8));
    }

    private BlobInfo getBlobInfo(String fileUniqueId, String contentType) {
        return BlobInfo
                .newBuilder(BlobId.of(BUCKET_NAME, fileUniqueId))
                .setContentType(Objects.isNull(contentType) ? "media" : contentType)
                .build();
    }

    @SneakyThrows
    private Credentials getCredentials() {
        File file = new File(FIREBASE_TOKEN_PATH);
        return GoogleCredentials.fromStream(new FileInputStream(file));
    }

    private Credentials getCustomCredentials() throws IOException {
        return GoogleCredentials.fromStream(IOUtils.toInputStream("Your firebase credentials", StandardCharsets.UTF_8));

    }

    public Object delete(String path) {
        try {
            String fileUniqueId = this.getFileNameFromPath(path);
            this.deleteFile(fileUniqueId);
            return ResponseEntity.ok();
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e);
        }
    }

    public boolean deleteFile(String fileUniqueId) {
        return StorageOptions
                .newBuilder()
                .setCredentials(getCredentials())
                .build()
                .getService()
                .delete(BlobId.of(BUCKET_NAME, fileUniqueId));
    }

    private String getExtension(String fileUniqueId) {
        return fileUniqueId.substring(fileUniqueId.lastIndexOf("."));
    }

    private String getFileNameFromPath(String path) {
        return path.substring(path.lastIndexOf("/") + 1, path.lastIndexOf("?"));
    }

}
