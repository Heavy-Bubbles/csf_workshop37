package sg.edu.nus.iss.backend.controllers;

import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import jakarta.json.Json;
import jakarta.json.JsonObject;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import sg.edu.nus.iss.backend.repositories.S3Repo;

@Controller
@RequestMapping
public class S3Controller {
    
    @Autowired
    private S3Repo s3Repo;

    @PostMapping(path = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> postFile(@RequestPart MultipartFile file){
        try{
            String contentType = file.getContentType();
            InputStream is = file.getInputStream();
            String id = s3Repo.upload(contentType, is);
            JsonObject resp = Json.createObjectBuilder()
                .add("id", id)
                .add("message", "success")
                .build();
            return ResponseEntity.ok(resp.toString());
        } catch (Exception ex) {
            JsonObject resp = Json.createObjectBuilder()
                .add("message", ex.getMessage())
                .build();
            return ResponseEntity.status(500)
                .body(resp.toString());
        }
    }

    @GetMapping(path = "/file/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getUrl(@PathVariable String id) {
        String url = s3Repo.getUrl(id);
        JsonObject resp = Json.createObjectBuilder()
            .add("url", url)
            .build();
        return ResponseEntity.ok(resp.toString());
    }
}
