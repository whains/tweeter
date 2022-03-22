package edu.byu.cs.tweeter.server.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.UUID;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.server.dao.FactoryDAO;

public class AuthenticationService {

    public FactoryDAO factoryDAO;

    public AuthenticationService(FactoryDAO factoryDAO) {
        this.factoryDAO = factoryDAO;
    }


    public static String hashPassword(String passwordToHash) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(passwordToHash.getBytes());
            byte[] bytes = md.digest();
            StringBuilder sb = new StringBuilder();
            for (byte aByte : bytes) {
                sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "FAILED TO HASH";
    }


    public AuthToken generateNewToken() {
        String token = UUID.randomUUID().toString();
        long datetime = new Timestamp(System.currentTimeMillis()).getTime();
        String strDatetime = datetime + "";

        return new AuthToken(token, strDatetime);
    }


    public void checkAuthTokens() {
        factoryDAO.getAuthTokenDAO().checkAuthTokens();
    }


}
