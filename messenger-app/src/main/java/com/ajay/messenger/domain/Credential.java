package com.ajay.messenger.domain;

import com.ajay.messenger.utils.SHAHashUtil;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by ajay.kg on 28/01/16.
 */
@Getter
@Setter
public class Credential {

    private static List<LoginCredential> loginCredentialList = new LinkedList<LoginCredential>();

    static {

        loginCredentialList.add(new LoginCredential("user", "test"));
        loginCredentialList.add(new LoginCredential("user1", "test1"));
        loginCredentialList.add(new LoginCredential("user2", "test2"));

    }

    public static boolean verifyUser(LoginCredential loginCredential) {

        for(LoginCredential loginCredential1 : loginCredentialList) {
            if(loginCredential.getUser().equals(loginCredential1.getUser())){
                return true;
            }
        }

        return false;
    }

    public static void issueToken(LoginCredential credential, String authSalt) {

        StringBuffer sb = new StringBuffer();
        sb.append(credential.getUser());
        sb.append(credential.getPassword());
        sb.append(authSalt);

        credential.setAuthToken(SHAHashUtil.getSHA256Hash(sb.toString()));
    }

    public static LoginCredential searchUser(String user) {

        for(LoginCredential loginCredential1 : loginCredentialList) {
            if(user.equals(loginCredential1.getUser())){
                return loginCredential1;
            }
        }

        return null;

    }
}
