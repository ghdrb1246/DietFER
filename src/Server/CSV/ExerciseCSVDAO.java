package Server.CSV;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import Common.Exercise;

// CSV 파싱
public class ExerciseCSVDAO {
    private Map<String, Exercise> exerciseMap = new HashMap<>();

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

    // 운동 명
    public Exercise getExercise(String name) {
        return loadExercises().get(name);
    }

    // 운동의 시간당 소모 칼로리
    public Set<String> getExerciseNames() {
        return loadExercises().keySet();
    }
}
