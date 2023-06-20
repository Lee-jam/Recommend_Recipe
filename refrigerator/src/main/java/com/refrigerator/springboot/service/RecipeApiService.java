package com.refrigerator.springboot.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.refrigerator.springboot.dto.BlogDTO;
import com.refrigerator.springboot.dto.RecipeApiDTO;
import com.refrigerator.springboot.dto.VideoDTO;
import com.refrigerator.springboot.entity.RecipeAPI;
import com.refrigerator.springboot.repository.RecipeApiRepository;
import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@PropertySource("classpath:config.properties")
public class RecipeApiService {

    final RecipeApiRepository recipeApiRepository;

    @Value("${open_food.api.key}")
    private String foodApiKey;
    @Value("${google_search.api.key}")
    private String googleSearchKey;
    @Value("${google_search.api.cx}")
    private String googleSearchCx;

    public void recipeApiSave(){
        String regexPattern = "●[가-힣]+\n";
        String regexPattern2 = "●[가-힣]+:+\n";
        String regexPattern3 = "●[가-힣]+:";
        String regexPattern4 = "•[가-힣]+:";
        String regexPattern5 = "\\[[0-9]+|[가-힣]+\\]"; //특수 문자는 \\로 이스케이프 처리
        String result ="";

        String url = "http://openapi.foodsafetykorea.go.kr/api/"+foodApiKey+"/COOKRCP01/json/1/100";
        JSONArray jsonRow;
        try {
            URL api_url = new URL(url);
            HttpURLConnection urlConnection = (HttpURLConnection) api_url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("Content-type", "application/json; charset=UTF-8");

            BufferedReader bf = new BufferedReader(new InputStreamReader(api_url.openConnection().getInputStream(),"UTF-8"));
            result = bf.readLine();

            JSONParser jsonParser =new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(result);

            JSONObject jsonRCP = (JSONObject) jsonObject.get("COOKRCP01");
            jsonRow = (JSONArray) jsonRCP.get("row");
            for(int i = 0 ; i<jsonRow.size();i++){
                JSONObject object = (JSONObject) jsonRow.get(i);
                String RCP_NM = (String) object.get("RCP_NM"); //메뉴명
                String temp_nm = RCP_NM.replace(" ","");
                String RCP_PARTS_DTLS = (String) object.get("RCP_PARTS_DTLS"); //재료정보
                RCP_PARTS_DTLS = RCP_PARTS_DTLS.replace(" ","");
                //● 이 포함된 데이터 가공
                if(RCP_PARTS_DTLS.contains("●")){
                    RCP_PARTS_DTLS = RCP_PARTS_DTLS.replaceAll(regexPattern2,"");
                    RCP_PARTS_DTLS = RCP_PARTS_DTLS.replaceAll(regexPattern3,"");
                    RCP_PARTS_DTLS = RCP_PARTS_DTLS.replaceAll(regexPattern,"");
                }
                else if(RCP_PARTS_DTLS.contains("•")){
                    RCP_PARTS_DTLS = RCP_PARTS_DTLS.replaceAll(regexPattern4,"");
                }
                else if(RCP_PARTS_DTLS.contains("[")){
                    RCP_PARTS_DTLS = RCP_PARTS_DTLS.replaceAll(regexPattern5,"");
                }
                if(RCP_PARTS_DTLS.contains("주재료")){
                    RCP_PARTS_DTLS = RCP_PARTS_DTLS.replace("주재료","");
                }
                else if(RCP_PARTS_DTLS.contains("재료")){
                    RCP_PARTS_DTLS = RCP_PARTS_DTLS.replace("재료","");
                }
                if(RCP_PARTS_DTLS.contains("오렌지당근펀치")){
                    RCP_PARTS_DTLS = RCP_PARTS_DTLS.replace("오렌지당근펀치","");
                }
                RCP_PARTS_DTLS = RCP_PARTS_DTLS.replace("\n","");
                //포함된 레시피 명 삭제
                RCP_PARTS_DTLS = RCP_PARTS_DTLS.replace(temp_nm,"");


                String INFO_ENG = (String) object.get("INFO_ENG"); //칼로리
                String MANUAL="";
                for(int j = 1 ; j<=20 ; j++){
                    if(j<10){
                        MANUAL+=((String)object.get("MANUAL0"+j))+"+";
                    }else{
                        MANUAL+=((String)object.get("MANUAL"+j))+"+";
                    }
                }

                RecipeApiDTO recipeApiDTO = new RecipeApiDTO(RCP_NM,INFO_ENG,RCP_PARTS_DTLS,MANUAL);

                RecipeAPI recipeAPI = RecipeAPI.updateData(recipeApiDTO);
                recipeApiRepository.save(recipeAPI);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<RecipeApiDTO> getRecipeData(){
        List<RecipeAPI> recipeAPILists =recipeApiRepository.findAll();
        List<RecipeApiDTO> recipeApiDTOLists = new ArrayList<>();
        for(RecipeAPI recipeAPIList : recipeAPILists){
            RecipeApiDTO recipeApiDTO = new RecipeApiDTO(recipeAPIList.getRep_nm(),recipeAPIList.getInfo_eng(),recipeAPIList.getRcp_part(),recipeAPIList.getManual());
            recipeApiDTOLists.add(recipeApiDTO);
        }
        return recipeApiDTOLists;
    }
    public List<BlogDTO> searchBlogRecipe(String recipeName){
        List<BlogDTO> blogDTOList = new ArrayList<>();
        RestTemplate restTemplate = new RestTemplate();

        recipeName = recipeName.replace(" ","");
        String Url = "https://www.googleapis.com/customsearch/v1?key="+googleSearchKey+"&cx="+googleSearchCx+"&q="+recipeName+"레시피";
        ResponseEntity<String> response = restTemplate.getForEntity(Url,String.class);

        try {
            String resp = response.getBody();

            Object blogJson = JSONValue.parse(resp);
            JSONObject jsonObject = (JSONObject) blogJson;
            JSONArray blogArr = (JSONArray) jsonObject.get("items");
            for(int i=0; i<blogArr.size();i++){
                JSONObject object = (JSONObject) blogArr.get(i);
                //블로그 이름
                String title = (String) object.get("title");
                //스니펫
                String snippet = (String) object.get("snippet");
                //링크
                String link = (String) object.get("link");
                //이미지
                JSONObject pagemap = (JSONObject) object.get("pagemap");
                String cse_img_src;
                if(pagemap==null){
                    cse_img_src = "/img/noImg.png";
                }
                else{
                    JSONArray cse_img = (JSONArray) pagemap.get("cse_thumbnail");
                    if(cse_img==null){
                        cse_img_src = "/img/noImg.png";
                    }else {
                        cse_img_src = (String) ((JSONObject) cse_img.get(0)).get("src");
                        if(cse_img_src==null){
                            cse_img_src = "/img/noImg.png";
                        }
                    }
                }
                BlogDTO blogDTO = new BlogDTO(title,snippet,link,cse_img_src);

                blogDTOList.add(blogDTO);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return blogDTOList;
    }

    public List<VideoDTO> searchvideoRecipe(String recipeName) {
        List<VideoDTO> videoDTOList = new ArrayList<>();
        RestTemplate restTemplate = new RestTemplate();
        String Url = "https://www.googleapis.com/youtube/v3/search?maxResults=20&part=snippet&key="+googleSearchKey+"&q="+recipeName+"레시피";
        ResponseEntity<String> response = restTemplate.getForEntity(Url,String.class);
        try {
            String resp = response.getBody();

            Object blogJson = JSONValue.parse(resp);
            JSONObject jsonObject = (JSONObject) blogJson;
            JSONArray blogArr = (JSONArray) jsonObject.get("items");
            for(int i=0; i<blogArr.size();i++){
                JSONObject object = (JSONObject) blogArr.get(i);
                //videoId
                JSONObject id = (JSONObject) object.get("id");
                String videoId = (String) id.get("videoId");
                //스니펫/타이틀
                JSONObject snippet = (JSONObject) object.get("snippet");
                String title = (String) snippet.get("title");
                //스니펫/썸네일/썸네일url
                JSONObject thumbnails = (JSONObject) snippet.get("thumbnails");
                JSONObject high = (JSONObject) thumbnails.get("high");
                String high_url = (String) high.get("url");
                VideoDTO videoDTO = new VideoDTO(title,videoId,high_url);

                videoDTOList.add(videoDTO);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return videoDTOList;
    }

    public RecipeApiDTO recipeDetails(String recipeName) {
        RecipeAPI recipeAPI = recipeApiRepository.getRecipefindByName(recipeName);
        String manual=recipeAPI.getManual();
        manual = manual.replaceAll("\\+","\\n");

        RecipeApiDTO recipeApiDTO = new RecipeApiDTO(recipeAPI.getRep_nm(),recipeAPI.getInfo_eng(),recipeAPI.getRcp_part(),recipeAPI.getManual());
        return recipeApiDTO;
    }

//    public List<BlogDTO> searchBlogRecipe(String recipeName){
////        https://www.googleapis.com/customsearch/v1?key=AIzaSyBZLPQK4Kx0sTV4KTAPd_4upJbO1I2UUyI&cx=a3b4bb7e3ad21457b&q=김치찌개 레시피
//        recipeName = recipeName.replace(" ","");
//        String Url = "https://www.googleapis.com/customsearch/v1?key="+googleSearchKey+"&cx="+googleSearchCx+"&q="+recipeName+"레시피";
//
//        List<BlogDTO> blogDTOList = new ArrayList<>();
//        String blog_result;
//        try {
//            URL blog_url =new URL(Url);
//            HttpURLConnection urlConnection = (HttpURLConnection) blog_url.openConnection();
//            urlConnection.setRequestMethod("GET");
//            urlConnection.setRequestProperty("Content-type","application/json");
//            urlConnection.setRequestProperty("Accept","application/json");
//            urlConnection.setDoInput(true);
//            System.out.println(blog_url);
//            //공백이 있으면 400에러 뜸...
//            BufferedReader bf = new BufferedReader(new InputStreamReader(blog_url.openConnection().getInputStream()));
//            blog_result = bf.readLine();
//            System.out.println(blog_result);
//            JSONParser jsonParser =new JSONParser();
//            JSONObject blogJson = (JSONObject) jsonParser.parse(blog_result);
//
//            JSONArray blogArr = (JSONArray) blogJson.get("items");
//            for(int i=0; i<blogArr.size();i++){
//                JSONObject object = (JSONObject) blogArr.get(i);
//                //블로그 이름
//                String title = (String) object.get("title");
//                //스니펫
//                String snippet = (String) object.get("snippet");
//                JSONObject pagemap = (JSONObject) object.get("pagemap");
//                String cse_img = (String) pagemap.get("cse_image");
//                System.out.println(title);
//                System.out.println(snippet);
//                System.out.println(cse_img);
//                BlogDTO blogDTO = new BlogDTO(title,snippet,cse_img);
//
//                blogDTOList.add(blogDTO);
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return blogDTOList;
//    }
}
