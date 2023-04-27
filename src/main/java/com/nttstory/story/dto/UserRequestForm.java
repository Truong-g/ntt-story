package com.nttstory.story.dto;

import com.nttstory.story.annotation.ValueOfEnum;
import com.nttstory.story.model.User;
import com.nttstory.story.model.UserStatusEnum;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestForm extends User {
    private MultipartFile avatarForm;
    @NotNull
    @ValueOfEnum(enumClass = UserStatusEnum.class)
    private String statusForm;
    private String roleForm;
}
