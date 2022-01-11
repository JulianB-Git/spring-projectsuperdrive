package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialsMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialsService {

    private CredentialsMapper credentialsMapper;
    private EncryptionService encryptionService;
    private AuthenticationService authenticationService;
    private UserService userService;

    public CredentialsService(CredentialsMapper credentialsMapper, EncryptionService encryptionService, AuthenticationService authenticationService, UserService userService) {
        this.credentialsMapper = credentialsMapper;
        this.encryptionService = encryptionService;
        this.authenticationService = authenticationService;
        this.userService = userService;
    }

    public int insertCredentials(Credentials credentials) {
        String encodedKey = getEncodedKey();
        User user = userService.getUser(authenticationService.getLoggedInUserName());

        credentials.setPassword(getEncryptedPassword(credentials.getPassword(), encodedKey));
        credentials.setKey(encodedKey);
        credentials.setUserId(user.getUserId());

        return credentialsMapper.insertCredentials(credentials);
    }

    //Get all saved credentials for the user logged in
    public List<Credentials> getCredentialsForUser() {
        User user = userService.getUser(authenticationService.getLoggedInUserName());
        return credentialsMapper.getCredentialsForUser(user.getUserId());
    }

    public int updateCredentials(Credentials credentials) {
        String encodedKey = getEncodedKey();
        User user = userService.getUser(authenticationService.getLoggedInUserName());

        credentials.setPassword(getEncryptedPassword(credentials.getPassword(), encodedKey));
        credentials.setKey(encodedKey);
        credentials.setUserId(user.getUserId());

        return credentialsMapper.updateCredentials(credentials);
    }

    public String getEncodedKey() {
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        return Base64.getEncoder().encodeToString(key);
    }

    public String getEncryptedPassword(String password, String key) {
        return encryptionService.encryptValue(password, key);
    }

    public int deleteCredentials(Integer credentialsID) {
        return credentialsMapper.deleteCredentials(credentialsID);
    }

    //See if the same credentials for this user already exists
    public boolean credentialsExist(Credentials credentials) {
        List<Credentials> savedUserCredentials = getCredentialsForUser();

        for (Credentials credential: savedUserCredentials) {
            if (credential.getUsername().equals(credentials.getUsername()) && credential.getUrl().equalsIgnoreCase(credentials.getUrl()) ) {
                return true;
            }
        }

        return false;
    }
}
