package com.refrigerator.springboot.entity;

import com.refrigerator.springboot.dto.RecipeApiDTO;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Size;


@Table(name = "recipe_api")
@Data
@Entity
public class RecipeAPI {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String rep_nm;
    private String info_eng;
    @Size(max=500)
    private String rcp_part;
    @Size(max=1000)
    private String manual;

    public static RecipeAPI updateData(RecipeApiDTO dto){
        RecipeAPI recipeAPI = new RecipeAPI();

        recipeAPI.setRep_nm(dto.getRep_nm());
        recipeAPI.setInfo_eng(dto.getInfo_eng());
        recipeAPI.setRcp_part(dto.getRcp_part());
        recipeAPI.setManual(dto.getManual());

        return recipeAPI;
    }
}
