package com.ad.dao;

import com.ad.models.User;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class UserDao extends BaseDao  {
    public static final String USER_DIR ="users";

    public Set<User> getUsers() {
        Set<User> userSet = new HashSet<>();
        List<String> allFiles = getAllFileNames(USER_DIR);
        for(String file: allFiles) {
            User user = getDataFromFS(USER_DIR + File.separator + file, User.class);
            userSet.add(user);
        }
        return userSet;
    }
}
