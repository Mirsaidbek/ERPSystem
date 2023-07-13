package dev.said.controllers;

import dev.said.domains.Document;
import dev.said.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/document")
public class DocumentController {

    private final DocumentService documentService;

    @PostMapping(name = "/uploadFiles", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<List<Document>> uploadDocs(@RequestPart("files") List<MultipartFile> files) {
        return ResponseEntity.ok(
                documentService.saveDocuments(files)
        );
    }

//    @PostMapping(name = "/uploadFile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ResponseEntity<Document> uploadDoc(@RequestPart("file") MultipartFile file) {
//        return ResponseEntity.ok(documentService.saveDocument(file));
//    }


}
