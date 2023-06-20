package com.refrigerator.springboot.service;

import com.refrigerator.springboot.dto.BardDTO;
import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@PropertySource("classpath:config.properties")
public class RandomService {
    @Value("${bardAPI.url}")
    private String url;
    public BardDTO recipeCreate(){
        String title = "테스트";
        String ingredient = "테스트";
        String level = "테스트";
        String method = "테스트";
        String tip = "테스트";
        System.out.println(url);
        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
            HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);
            ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity,String.class);
            String answer = response.getBody();

            JSONObject object = (JSONObject) JSONValue.parse(answer);
            String objectAnswer = ((String) object.get("answer"));
            List<String> answerList = new ArrayList<>(Arrays.asList((objectAnswer.split("\\n\\*\\*"))));
            System.out.println(objectAnswer);
            System.out.println(answerList);

            for(int i = 0 ; i<answerList.size();i++){
                if(i == 0 || i == 1){
                    answerList.set(i,answerList.get(i).replaceAll("\\*",""));
                    if(i==0){
                        if(answerList.get(i).contains(":")){
                            title = answerList.get(i).split(":")[1].trim();
                        }else{
                            title = answerList.get(i).trim();
                        }
                    }
                    else{
                        if(answerList.get(i).contains(":")){
                            level = answerList.get(i).split(":")[1].trim();
                        }else{
                            level = answerList.get(i).trim();
                        }
                    }
                }
                else if(i==2 || i==4) {
                    answerList.set(i,answerList.get(i).replaceAll("\\*\\*","").replaceAll("\\n\\n",""));
                    if(i==2){
                        if(answerList.get(i).contains(":")) {
                            ingredient = answerList.get(i).split(":")[1].trim();
                        }else {
                            ingredient = answerList.get(i).trim();
                        }
                    }else{
                        if(answerList.get(i).contains(":")) {
                            tip = answerList.get(i).split(":")[1].trim();
                        }else {
                            tip = answerList.get(i).trim();
                        }
                    }

                }else{
                    answerList.set(i,answerList.get(i).replaceAll("\\*\\*","").replaceAll("\\n\\n",""));
                    if(answerList.get(i).contains(":")) {
                        method = answerList.get(i).split(":")[1].trim();
                    }else {
                        method = answerList.get(i).trim();
                    }
                }
            }

            System.out.println(answerList);
            BardDTO bardDTO = new BardDTO(title,ingredient,level,method,tip);
            return bardDTO;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

}
