package edu.byu.cs.tweeter.server.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Base64;

import edu.byu.cs.tweeter.model.net.request.RegisterRequest;
import edu.byu.cs.tweeter.model.net.response.RegisterResponse;
import edu.byu.cs.tweeter.server.dao.FactoryDAO;
import edu.byu.cs.tweeter.server.dao.UserDAO;

public class RegisterService extends AuthenticationService {

    public RegisterService(FactoryDAO factoryDAO) {
        super(factoryDAO);
    }

    public RegisterResponse register(RegisterRequest registerRequest) throws Exception {
        if (registerRequest == null) {
            throw new RuntimeException("[BadRequest400] 400");
        }

        UserDAO userDAO = factoryDAO.getUserDAO();

        registerRequest.setImage(uploadImage(registerRequest));
        registerRequest.setPassword(hashPassword(registerRequest.getPassword()));
        RegisterResponse registerResponse = userDAO.register(registerRequest);

        checkAuthTokens();
        return registerResponse;
    }

    private String uploadImage(RegisterRequest registerRequest) throws Exception {
        String username = registerRequest.getUsername();
        String imageByteArray = registerRequest.getImage();

        byte[] decodedImageByteArray = Base64.getDecoder().decode(imageByteArray);
        InputStream inputStream = new ByteArrayInputStream(decodedImageByteArray);
        ObjectMetadata meta = new ObjectMetadata();

        AmazonS3 s3 = AmazonS3ClientBuilder
                .standard()
                .withRegion("us-west-1")
                .build();

        String bucketName = "cs340-activity-cli";
        String fileName = username + ".png";

        meta.setContentLength(decodedImageByteArray.length);
        meta.setContentType("image/png");

        try{
            s3.putObject(new PutObjectRequest(bucketName, fileName, inputStream, meta)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
            inputStream.close();
        }
        catch (Exception e) {
            throw new Exception("Error while uploading profile image" + e.toString());
        }

        return "https://" + bucketName + ".s3-us-west-1.amazonaws.com/" + fileName;
    }
}
