package dev.said.service;

import dev.said.config.security.SessionUser;
import dev.said.domains.Document;
import dev.said.firebase.MediaService;
import dev.said.repository.DocumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

import static java.util.UUID.randomUUID;

@Service
@RequiredArgsConstructor
public class DocumentService {
    private final DocumentRepository documentRepository;
    private final SessionUser sessionUser;
    private final MediaService mediaService;

    public List<Document> saveDocuments(List<MultipartFile> files) {
        if (sessionUser.id() == -1) {
            throw new RuntimeException("User not found");
        }
        if (files.isEmpty()) {
            throw new RuntimeException("File not found");
        }
        List<Document> documents = new ArrayList<>();
        files.forEach(
                file -> {
                    Document document = documentRepository.save(
                            Document.childBuilder()
                                    .createdBy(sessionUser.id())
                                    .originalName(file.getOriginalFilename())
                                    .generatedName(randomUUID() + file.getOriginalFilename())
                                    .extension(StringUtils.getFilenameExtension(file.getOriginalFilename()))
                                    .mimeType(file.getContentType())
                                    .size(file.getSize())
                                    .path(mediaService.upload(file))
                                    .build()
                    );
                    documents.add(document);
                }
        );
        return documents;
    }

    public Document saveDocument(MultipartFile file) {
        if (sessionUser.id() == -1) {
            throw new RuntimeException("User not found");
        }
        if (file.isEmpty()) {
            throw new RuntimeException("File not found");
        }


        Document doc = Document.childBuilder()
                .createdBy(sessionUser.id())
                .originalName(file.getOriginalFilename())
                .generatedName(randomUUID() + file.getOriginalFilename())
                .extension(StringUtils.getFilenameExtension(file.getOriginalFilename()))
                .mimeType(file.getContentType())
                .size(file.getSize())
                .path(mediaService.upload(file))
                .build();

        System.out.println("doc = " + doc);
        return documentRepository.save(doc);
    }

//    public List<Document> getAllDocsBySessionUser() {
//        if (sessionUser.id() == -1) {
//            throw new RuntimeException("User not found");
//        }
//        return documentRepository.findAllByCreatedBy(sessionUser.id());
//    }
}
