package com.example.awsimageupload.dataStore;

import com.example.awsimageupload.profile.UserProfile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class FakeUserProfileDataStore {

    private static final List<UserProfile> USER_PROFILES = new ArrayList<>();
    //we are not using a database, so we are going to hard code some user info in here
    static{
        USER_PROFILES.add(new UserProfile(UUID.randomUUID(), "JanetJones", null));
        USER_PROFILES.add(new UserProfile(UUID.randomUUID(), "AntonioJunior", null));
    }
    public List<UserProfile> getUserProfiles(){
        return USER_PROFILES;
    }
}
