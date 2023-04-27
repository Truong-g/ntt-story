package com.nttstory.story.projection;


import com.nttstory.story.model.Role;

import java.util.Set;

public interface UsersProjection {
    Long getId();
    String getLastName();
    String getFirstName();
    String getEmail();
    String getAddress();
    String getPhone();
    String getBirthDay();
    String getStatus();
    String getAvatar();
    String getCreatedDate();
    String getUpdatedDate();
}
