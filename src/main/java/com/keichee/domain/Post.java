package com.keichee.domain;


import lombok.*;
import org.springframework.lang.NonNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Post {

    private String _id;
    private String title;
    private String content;
}
