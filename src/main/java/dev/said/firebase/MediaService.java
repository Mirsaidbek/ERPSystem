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
        return GoogleCredentials.fromStream(IOUtils.toInputStream("{\n" +
                "  \"type\": \"service_account\",\n" +
                "  \"project_id\": \"erpsystm\",\n" +
                "  \"private_key_id\": \"a6bcef565a9e271729ef0ec8e154fc946ff8f488\",\n" +
                "  \"private_key\": \"-----BEGIN PRIVATE KEY-----\\nMIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQCxy+J8T7KaDnQL\\nciOXofYeKm0r/Dyn+4x6tpOHY+Keh9Tx3hfIYmDgCEGfvoHrN2l3WARMpO763Sdv\\nAuTTNjXLVhpYc9RPIpXrX0ZaQg5Ss9EOIW1TlWyWOORWQU7FqSF1dBGCC7H5xzfa\\nLAcTGtof+qif3iR2GJXxhiTTSAqu8cAz3pEqMYH1UF+pJyoy3HO1LRzv8NCfk+d6\\nAL//bzVkeYBncdHWRYDK8+XEDU4MMTietjQ7LMIL8HdL3t8U0JxI1oJDErbVsdZx\\nqrmxndGKnldyO1TSPGUS9I7POMTBviL6EWwweszbrS724XcX03dmj4S7VYbu1HIv\\nvWf0bjI9AgMBAAECggEAKGN34iRanpts6hK2GiJ9HdImdaj5gHFospGaBtX+a2PQ\\n0FbS0T7j4dCOU8L7kVrHlwhEBcMr1In7hMDECWTVqIi5OGGrl4R0SQEeIq/BwDC/\\n6zVVt/iIL8HhwORaCBXJF3zT41ZTyjliqyl4O/NMHJqcxqA4aSlhmnmOEP7bCm1X\\nUuJ5yEO2UmJYgiXAwWkPUGDaomVW+vU61+a8g9LGDSxa/INJ1qddFmQAnlZvX9de\\nrlmBdmsLyao/QJhpHfZIyJb6ucBXRoB4mw++ijdnMtoddmNKjRq3kk6Fk9odQgzY\\nEkOIfJuVsvn488bFEQbGydlizc/C1tNBSlLcWf5o4wKBgQD5IHhYKc0DrD4E1EUM\\nZQN7oweRcbMiHLPCLGIicu8IC+G769zQh0zYueI9JLsqfes42CE5iztU2cUSq8TI\\nM/A1QexjJ15YYmsEtFXDCyofk5GX2qk3fnbIgoOHktLpXNqqpp+bQXvi7I4eNk13\\nOt4C2GNBc7KVi2jFjUNWLNP8YwKBgQC2s5+BzcBiohll9Q92hawKpKryyqZyviDL\\nzmeo3M7VBYfrQtuG5wDG/Rg+lGItw+TRTzB/8jcDhD5g41e3AaCaOHfD33D6knlP\\n+xNM2kNrfjGIdntkPms+2P0AsfYMkh6yw3JRsSrpgwA3uwC/UqPL4Zgyee6/Tfnc\\n8cfO0ojI3wKBgQDM4xp2j7JPofnDqPw8rFsH9NXFlXgYxvhD6pKu+E2EFZmKHvoU\\n+iGGYKIjXTEdSkAJGR+CHvMl3pAIwgHuEWEtu9D43yjlkdOoI9Qgu+7h11C3Ths7\\nS6PPVh9sgaE6UaZ5EN5VJq8HuztKt6hQNbbfKi2/Z7Lo8KWzS3GkzuttNwKBgQCQ\\ncImWMXf7gnPZ61gCN8O/1ldgA158MFv1RIUnNwo911WRZRXxHQe8SbO1JdJIpCpu\\nX9DUFkXv/cjjCFj8UckKo8HrlObY8pv9411q0NOkU+wNGr6Kjd5znVDID+maHpsu\\ncN4cP5jRa8PsS2dX2pmsQfn1Tfi2PYtgZbW1iTSiuQKBgQCQp/XAgSVJ5rqAcl3b\\n9yPKW4FkC3ENdilCow4oGyHrMLGKfIwID7sl0OeRTRDfQYFUFjUgON9YK3J2Cv0B\\nsnRh7ZzX9px1TlKcc1mVWrjP6qRmCd5+3P+qLDfuqxg2MrRhC7BVMXHcLFBlNx+f\\ncCDKZAMn8jxp3vArMuW3J5r9+A==\\n-----END PRIVATE KEY-----\\n\",\n" +
                "  \"client_email\": \"firebase-adminsdk-bvkwu@erpsystm.iam.gserviceaccount.com\",\n" +
                "  \"client_id\": \"103670510066975952719\",\n" +
                "  \"auth_uri\": \"https://accounts.google.com/o/oauth2/auth\",\n" +
                "  \"token_uri\": \"https://oauth2.googleapis.com/token\",\n" +
                "  \"auth_provider_x509_cert_url\": \"https://www.googleapis.com/oauth2/v1/certs\",\n" +
                "  \"client_x509_cert_url\": \"https://www.googleapis.com/robot/v1/metadata/x509/firebase-adminsdk-bvkwu%40erpsystm.iam.gserviceaccount.com\",\n" +
                "  \"universe_domain\": \"googleapis.com\"\n" +
                "}", StandardCharsets.UTF_8));

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
