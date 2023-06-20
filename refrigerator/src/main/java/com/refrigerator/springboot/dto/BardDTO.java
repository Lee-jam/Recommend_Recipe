package com.refrigerator.springboot.dto;

import lombok.Data;

@Data
public class BardDTO {
    private String cook_title;
    private String cook_ingredient;
    private String cook_level;
    private String cook_method;
    private String cook_tip;

    public BardDTO(String cook_title,String cook_ingredient, String cook_level, String cook_method, String cook_tip){
        this.cook_title = cook_title;
        this.cook_ingredient = cook_ingredient;
        this.cook_level = cook_level;
        this.cook_method = cook_method;
        this.cook_tip = cook_tip;
    }
}
