package com.nttstory.story.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseResponse <M, D>{
    private int statusCode;
    private M messages;
    private D data;

}
