package com.refrigerator.springboot.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class BlogDTO {
    private String title;
    private String snippet;
    private String link;
    private String cse_img;

    @Builder
    public BlogDTO(String title, String snippet, String link, String cse_img){
        this.title=title;
        this.snippet=snippet;
        this.link=link;
        this.cse_img=cse_img;
    }
}
