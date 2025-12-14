package Server.API;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URLEncoder;
import java.util.ArrayList;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import Common.FoodNutrition;

public class FoodNutritionSystem {
    // TODO : 인증키 확인
    private static final String SERVICE_KEY = "인증키";
    private static final String API_URL = "https://apis.data.go.kr/1471000/FoodNtrCpntDbInfo02/getFoodNtrCpntDbInq02";

    /**
     * 식품명으로 영양정보 조회
     */
    public ArrayList<FoodNutrition> fetchFoodNutrition(String foodName) {
        StringBuilder sb = new StringBuilder();
        try {
            // URL 구성
            StringBuilder urlBuilder = new StringBuilder(API_URL);
            urlBuilder.append("?serviceKey=").append(SERVICE_KEY);
            urlBuilder.append("&type=json");
            urlBuilder.append("&pageNo=1");
            urlBuilder.append("&numOfRows=10");
            urlBuilder.append("&FOOD_NM_KR=").append(URLEncoder.encode(foodName, "UTF-8"));

            HttpURLConnection conn = (HttpURLConnection) URI.create(urlBuilder.toString()).toURL().openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

            String line;
            while ((line = br.readLine()) != null)
                sb.append(line);

            br.close();
            conn.disconnect();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return parseFoodNutrition(sb.toString());
    }

    /**
     * JSON 파싱
     */
    private ArrayList<FoodNutrition> parseFoodNutrition(String json) {
        ArrayList<FoodNutrition> list = new ArrayList<>();

        JsonObject root = JsonParser.parseString(json).getAsJsonObject();
        JsonObject body = root.getAsJsonObject("body");
        JsonArray items = body.getAsJsonArray("items");

        for (int i = 0; i < items.size(); i++) {
            JsonObject obj = items.get(i).getAsJsonObject();

            String name = getString(obj, "FOOD_NM_KR");
            double kcal = getDouble(obj, "AMT_NUM1");
            double carb = getDouble(obj, "AMT_NUM3");
            double protein = getDouble(obj, "AMT_NUM4");
            double fat = getDouble(obj, "AMT_NUM6");

            list.add(new FoodNutrition(name, kcal, carb, protein, fat));
        }

        return list;
    }

    // 음식명 추출
    private String getString(JsonObject obj, String key) {
        return obj.has(key) && !obj.get(key).isJsonNull()
                ? obj.get(key).getAsString()
                : "";
    }

    // 탄, 단, 지 추출
    private double getDouble(JsonObject obj, String key) {
        try {
            if (!obj.has(key) || obj.get(key).getAsString().isBlank())
                return 0.0;
            return Double.parseDouble(obj.get(key).getAsString().replace(",", ""));
        } catch (Exception e) {
            return 0.0;
        }
    }

    // 테스트 용
    public static void main(String[] args) {
        FoodNutritionSystem f = new FoodNutritionSystem();
        ArrayList<FoodNutrition> fList = new ArrayList<>();
        try {
            fList = f.fetchFoodNutrition("고구마");
            for (FoodNutrition fn : fList) {
                System.out.println(fn.toString());
            }
        } 
        catch (Exception e) {
            System.out.println("API 조회 중 예의 발생");
            e.printStackTrace();
        }
    }
}