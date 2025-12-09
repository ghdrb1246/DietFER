import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class FoodNutritionAPITest {
    public static void main(String[] args) {
        try {
            // https://apis.data.go.kr/1471000/FoodNtrCpntDbInfo02/getFoodNtrCpntDbInq02
            // ?serviceKey=3Qv2YGfcxrLyWAmXThvn5VH9u3jXTXOmkYhZO929mraVTTAa5OMp1qNx%2FYfeRv1XVOJfK1PyN%2BjZrO2lJaNpRA%3D%3D
            // &pageNo=1
            // &type=json
            // &FOOD_CAT1_NM=%EA%B0%90%EC%9E%90
            String serviceKey = "3Qv2YGfcxrLyWAmXThvn5VH9u3jXTXOmkYhZO929mraVTTAa5OMp1qNx%2FYfeRv1XVOJfK1PyN%2BjZrO2lJaNpRA%3D%3D";

            // 검색할 음식명
            String foodName = URLEncoder.encode("고구마", StandardCharsets.UTF_8.toString());

            String apiURL =
                    "https://apis.data.go.kr/1471000/FoodNtrCpntDbInfo02/getFoodNtrCpntDbInq02"
                            + "?serviceKey=" + serviceKey
                            + "&pageNo=1"
                            + "&type=json"
                            + "&FOOD_NM_KR=" + foodName;

            URL url = new URI(apiURL).toURL();
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            // 헤더 설정 (일부 API에서 중요)
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);

            int responseCode = conn.getResponseCode();
            System.out.println("Response Code : " + responseCode);

            BufferedReader br;

            // 200~299 정상 응답
            if (responseCode >= 200 && responseCode < 300) {
                br = new BufferedReader(
                        new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
            }
            // 오류 응답(400, 500, 502 포함)
            else {
                br = new BufferedReader(
                        new InputStreamReader(conn.getErrorStream(), StandardCharsets.UTF_8));
            }

            StringBuilder response = new StringBuilder();
            String line;

            while ((line = br.readLine()) != null) {
                response.append(line);
            }

            br.close();
            conn.disconnect();

            System.out.println("===== API 응답 =====");
            System.out.println(response.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
