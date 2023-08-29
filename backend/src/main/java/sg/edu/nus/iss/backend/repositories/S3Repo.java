package sg.edu.nus.iss.backend.repositories;

import java.io.InputStream;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

@Repository
public class S3Repo {
    
    @Autowired
    private AmazonS3 s3;

    public String upload(String contentType, InputStream is){
        String id = UUID.randomUUID().toString().substring(0, 8);
        String key = "file/%s".formatted(id);

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(contentType);

        PutObjectRequest putReq = new PutObjectRequest("heavybubbles", key, is, metadata);
        putReq = putReq.withCannedAcl(CannedAccessControlList.PublicRead);

        s3.putObject(putReq);

        return id;
    }

    public String getUrl(String id) {
        String key = "file/%s".formatted(id);
        return s3.getUrl("heavybubbles", key).toExternalForm();
    }
}
