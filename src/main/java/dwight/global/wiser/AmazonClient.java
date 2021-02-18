package dwight.global.wiser;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.AccessControlList;
import com.amazonaws.services.s3.model.GroupGrantee;
import com.amazonaws.services.s3.model.Permission;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

@Service
public class AmazonClient {
    private AmazonS3 s3;

    @Value("${amazonProperties.endPointUrl}")
    private String endPointUrl;

    @Value("${amazonProperties.bucketName}")
    private String bucketName;

    // Most important method of the class
    // Calls on all other methods
    public String uploadFile(MultipartFile multipartFile) {
        String fileUrl = "";
        try {
            // Gets Multipart file and converts it to File class
            File file = convertFile(multipartFile);
            // Generates a unique filename
            String filename = generateFileName(multipartFile);
            // Generates the URL where the file will be stored
            fileUrl = endPointUrl + "/" + bucketName + "/" + filename;
            // Uploads the file to S3
            uploadFileTos3bucket(filename, file);
            // Deletes file from local memory.
            file.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileUrl;
    }

    //Uploads File to S3 Bucket and sets access permissions.
    private void uploadFileTos3bucket(String fileName, File file) {
        // Gives all users access to the file
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, fileName, file);
        AccessControlList acl = new AccessControlList();
        acl.grantPermission(GroupGrantee.AllUsers, Permission.Read); //all users or authenticated
        putObjectRequest.setAccessControlList(acl);

        // Puts object into Bucket
        s3.putObject(putObjectRequest);
    }

    //Converts Multipart file to File class.
    private File convertFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

    // Generates unique filenames so that the same file can be uploaded more than once
    // Adds a timestamp and replaces spaces with underscores
    private String generateFileName(MultipartFile multiPart) {
        return new Date().getTime() + "-" + multiPart.getOriginalFilename().replace(" ", "_");
    }

    // Deletes file from Bucket ith fileName
    public String deleteFileFromS3Bucket(String fileUrl) {
        //Takes filename from fileUrl
        String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
        //Deletes object
        s3.deleteObject(bucketName, fileName);
        return "Deleted.";
    }

    // PostConstruct annotations makes this method run after the class has been constructed
    // Necessary to allow attributes to be initialized.
    // Method creates the AmazonS3 client Object with access and secret keys.
    @PostConstruct
    private void initializeAmazon() {
        this.s3 = AmazonS3ClientBuilder.standard().withRegion(Regions.US_EAST_2).build();
    }
}
