package Server.CSV;

import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

import Common.Exercise;

// CSV 파싱
public class ExerciseCSVDAO {
    private static final String FILE_PATH = "src/Server/CSV/exercise.csv";

    public ArrayList<Exercise> loadExercises() {
        ArrayList<Exercise> list = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;

            while ((line = br.readLine()) != null) {
                StringTokenizer st = new StringTokenizer(line, ",");

                String name = st.nextToken();
                double kcalPerKg = Double.parseDouble(st.nextToken());

                list.add(new Exercise(name, kcalPerKg));
            }

        } 
        catch (IOException e) {
            System.err.println("운동 CSV 로딩 실패");
            e.printStackTrace();
        }

        return list;
    }
}
