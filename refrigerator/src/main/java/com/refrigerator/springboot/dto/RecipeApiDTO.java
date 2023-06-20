package com.refrigerator.springboot.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RecipeApiDTO {
    private String rep_nm; //레시피 명
    private String info_eng; //칼로리
    private String rcp_part; //재료
    private String manual; //요리 방법

    public RecipeApiDTO(String rep_nm, String info_eng, String rcp_part, String manual){
        this.rep_nm=rep_nm;
        this.info_eng=info_eng;
        this.rcp_part=rcp_part;
        this.manual=manual;

    }
}
