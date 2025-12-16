package Server.CSV;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import Common.Exercise;

/**
 * 운동 CSV 파싱
 */
public class ExerciseCSVDAO {
    // 운동명과, 칼로리 맵핑
    private Map<String, Exercise> exerciseMap = new HashMap<>();

    /**
     * 운동 CSV 파일에서 추출
     * 
     * @return 운동명과, 칼로리 맵핑
     */
    public Map<String, Exercise> loadExercises() {
        try (BufferedReader br = new BufferedReader(new FileReader("src/Server/CSV/exercise.csv"))) {
            String line;

            while ((line = br.readLine()) != null) {
                StringTokenizer st = new StringTokenizer(line, ",");

                String name = st.nextToken().trim();
                double kcal = Double.parseDouble(st.nextToken().trim());

                exerciseMap.put(name, new Exercise(name, kcal));
            }
        } 
        catch (Exception e) {
            e.printStackTrace();
        }

        return exerciseMap;
    }

    /**
     * 운동 추출
     * 
     * @param name 운동 명
     * @return 운동 객체
     */
    public Exercise getExercise(String name) {
        return loadExercises().get(name);
    }

    /**
     * 운동의 시간당 소모 칼로리
     * 
     * @return 칼리로
     */
    public Set<String> getExerciseNames() {
        return loadExercises().keySet();
    }
}
