package Server.API.TestAPI;

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
            String serviceKey = "인증키";

            // 검색할 음식명 ? 대품류
            String foodName = URLEncoder.encode("고구마", StandardCharsets.UTF_8.toString());

            String apiURL = "https://apis.data.go.kr/1471000/FoodNtrCpntDbInfo02/getFoodNtrCpntDbInq02"
                    + "?serviceKey=" + serviceKey
                    + "&pageNo=1"
                    + "&type=json"
                    + "&FOOD_CAT1_NM=" + foodName;

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


/* 

{
  "header": {
    "resultCode": "00",
    "resultMsg": "NORMAL SERVICE."
  },
  "body": {
    "pageNo": 1,
    "totalCount": 2807,
    "numOfRows": 10,
    "items": [
        {
            ..
        },
        {
            "NUM": "436",
            "FOOD_CD": "D114-640080000-0001",
            "FOOD_NM_KR": "샐러드_닭가슴살",
            "DB_GRP_CM": "D",
            "DB_GRP_NM": "음식",
            "DB_CLASS_CM": "01",
            "DB_CLASS_NM": "품목대표",
            "FOOD_OR_CD": "1",
            "FOOD_OR_NM": "가정식(분석 함량)",
            "FOOD_CAT1_CD": "14",
            "FOOD_CAT1_NM": "생채·무침류",
            "FOOD_REF_CD": "14640",
            "FOOD_REF_NM": "샐러드",
            "FOOD_CAT2_CD": "1464008",
            "FOOD_CAT2_NM": "닭가슴살",
            "FOOD_CAT3_CD": "146400800",
            "FOOD_CAT3_NM": "해당없음",
            "FOOD_CAT4_CD": "00",
            "FOOD_CAT4_NM": "해당없음",
            "SERVING_SIZE": "100g",
            "AMT_NUM1": "135.000",
            "AMT_NUM2": "77.00",
            "AMT_NUM3": "7.17",
            "AMT_NUM4": "9.49",
            "AMT_NUM5": "0.95",
            "AMT_NUM6": "5.36",
            "AMT_NUM7": "3.25",
            "AMT_NUM8": "3.10",
            "AMT_NUM9": "41.00",
            "AMT_NUM10": "0.47",
            "AMT_NUM11": "115.000",
            "AMT_NUM12": "255.000",
            "AMT_NUM13": "88.00",
            "AMT_NUM14": "13.00",
            "AMT_NUM15": "",
            "AMT_NUM16": "4.00",
            "AMT_NUM17": "108.000",
            "AMT_NUM18": "0.08",
            "AMT_NUM19": "0.11",
            "AMT_NUM20": "0.51",
            "AMT_NUM21": "0.12",
            "AMT_NUM22": "0.00",
            "AMT_NUM23": "10.85",
            "AMT_NUM24": "1.21",
            "AMT_NUM25": "0.03",
            "AMT_NUM26": "",
            "AMT_NUM27": "",
            "AMT_NUM28": "",
            "AMT_NUM29": "",
            "AMT_NUM30": "0.16",
            "AMT_NUM31": "30.17",
            "AMT_NUM32": "",
            "AMT_NUM33": "",
            "AMT_NUM34": "",
            "AMT_NUM35": "",
            "AMT_NUM36": "",
            "AMT_NUM37": "",
            "AMT_NUM38": "4.29",
            "AMT_NUM39": "",
            "AMT_NUM40": "",
            "AMT_NUM41": "",
            "AMT_NUM42": "",
            "AMT_NUM43": "0.01",
            "AMT_NUM44": "",
            "AMT_NUM45": "",
            "AMT_NUM46": "",
            "AMT_NUM47": "",
            "AMT_NUM48": "",
            "AMT_NUM49": "",
            "AMT_NUM50": "",
            "AMT_NUM51": "",
            "AMT_NUM52": "1.67",
            "AMT_NUM53": "",
            "AMT_NUM54": "0.00",
            "AMT_NUM55": "",
            "AMT_NUM56": "",
            "AMT_NUM57": "0.22",
            "AMT_NUM58": "0.00",
            "AMT_NUM59": "",
            "AMT_NUM60": "1.35",
            "AMT_NUM61": "",
            "AMT_NUM62": "",
            "AMT_NUM63": "30.00",
            "AMT_NUM64": "10.00",
            "AMT_NUM65": "",
            "AMT_NUM66": "",
            "AMT_NUM67": "0.00",
            "AMT_NUM68": "",
            "AMT_NUM69": "0.00",
            "AMT_NUM70": "",
            "AMT_NUM71": "",
            "AMT_NUM72": "10.00",
            "AMT_NUM73": "",
            "AMT_NUM74": "3.33",
            "AMT_NUM75": "",
            "AMT_NUM76": "10.00",
            "AMT_NUM77": "50.00",
            "AMT_NUM78": "90.00",
            "AMT_NUM79": "",
            "AMT_NUM80": "10.00",
            "AMT_NUM81": "270.000",
            "AMT_NUM82": "",
            "AMT_NUM83": "0.00",
            "AMT_NUM84": "30.00",
            "AMT_NUM85": "0.47",
            "AMT_NUM86": "",
            "AMT_NUM87": "",
            "AMT_NUM88": "10.00",
            "AMT_NUM89": "10.00",
            "AMT_NUM90": "0.00",
            "AMT_NUM91": "",
            "AMT_NUM92": "",
            "AMT_NUM93": "2,450.000",
            "AMT_NUM94": "10.00",
            "AMT_NUM95": "10.00",
            "AMT_NUM96": "10.00",
            "AMT_NUM97": "",
            "AMT_NUM98": "30.00",
            "AMT_NUM99": "10.00",
            "AMT_NUM100": "",
            "AMT_NUM101": "10.00",
            "AMT_NUM102": "",
            "AMT_NUM103": "30.00",
            "AMT_NUM104": "820.000",
            "AMT_NUM105": "",
            "AMT_NUM106": "",
            "AMT_NUM107": "",
            "AMT_NUM108": "",
            "AMT_NUM109": "",
            "AMT_NUM110": "150.000",
            "AMT_NUM111": "38.81",
            "AMT_NUM112": "0.38",
            "AMT_NUM113": "",
            "AMT_NUM114": "",
            "AMT_NUM115": "5.02",
            "AMT_NUM116": "0.69",
            "AMT_NUM117": "",
            "AMT_NUM118": "",
            "AMT_NUM119": "",
            "AMT_NUM120": "6,681.800",
            "AMT_NUM121": "",
            "AMT_NUM122": "",
            "AMT_NUM123": "1,289.730",
            "AMT_NUM124": "322.890",
            "AMT_NUM125": "473.530",
            "AMT_NUM126": "532.590",
            "AMT_NUM127": "112.930",
            "AMT_NUM128": "331.100",
            "AMT_NUM129": "322.670",
            "AMT_NUM130": "36.83",
            "AMT_NUM131": "590.310",
            "AMT_NUM132": "714.310",
            "AMT_NUM133": "340.130",
            "AMT_NUM134": "304.560",
            "AMT_NUM135": "",
            "AMT_NUM136": "281.550",
            "AMT_NUM137": "",
            "AMT_NUM138": "231.990",
            "AMT_NUM139": "310.620",
            "AMT_NUM140": "308.200",
            "AMT_NUM141": "177.860",
            "AMT_NUM142": "",
            "AMT_NUM143": "",
            "AMT_NUM144": "",
            "AMT_NUM145": "",
            "AMT_NUM146": "",
            "AMT_NUM147": "",
            "AMT_NUM148": "",
            "AMT_NUM149": "",
            "AMT_NUM150": "",
            "AMT_NUM151": "",
            "AMT_NUM152": "",
            "AMT_NUM153": "",
            "AMT_NUM154": "",
            "AMT_NUM155": "",
            "AMT_NUM156": "",
            "AMT_NUM157": "",
            "SUB_REF_CM": "71",
            "SUB_REF_NAME": "식품의약품안전처",
            "NUTRI_AMOUNT_SERVING": "",
            "Z10500": "150.000g",
            "DISH_ONE_SERVING": null,
            "ITEM_REPORT_NO": "",
            "MAKER_NM": null,
            "IMP_MANUFAC_NM": "",
            "SELLER_MANUFAC_NM": "",
            "IMP_YN": "",
            "NATION_CM": "",
            "NATION_NM": "",
            "CRT_MTH_CD": "1",
            "CRT_MTH_NM": "분석",
            "RESEARCH_YMD": "2019-12-31",
            "UPDATE_DATE": "2025-01-23"
        },
        {
            ..
        }
    ]
  }
}   
*/