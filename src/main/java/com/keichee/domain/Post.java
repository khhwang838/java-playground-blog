package com.keichee.domain;


import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.lang.NonNull;

import java.time.ZonedDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Post {

    private String id;
    private String title;
    private String content;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
}
