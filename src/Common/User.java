package Common;

/**
 * 사용자 정보 정의
 */
public class User {
    // 사용자 id
    private String id;
    // 사용자 pw
    private String pw;
    // 성별
    private boolean sex;
    // 키
    private double height;
    // 나이
    private int age;
    // 초기 체중
    private double startWeight;
    // 목표 체중
    private double goalWeight;

    // 기본 생성자
    public User() { }

    /**
     * 사용자 객체
     * 
     * @param id          ID
     * @param pw          비밀번호
     * @param sex         성별
     * @param height      키
     * @param age         나이
     * @param startWeight 초기 체중
     * @param goalWeight  목표 체중
     */
    public User(String id, String pw, boolean sex, double height, int age, double startWeight, double goalWeight) {
        this.id = id;
        this.pw = pw;
        this.sex = sex;
        this.height = height;
        this.age = age;
        this.startWeight = startWeight;
        this.goalWeight = goalWeight;
    }

    // getter
    /**
     * 사용자 id getter 메소드
     * 
     * @return 사용자 id 리턴
     */
    public String getID() { return id; }

    /**
     * 사용자 사용자 pw를 리턴 getter 메소드
     * 
     * @return 사용자 pw 리턴
     */
    public String getPW() {
        return pw;
    }

    /**
     * 사용자 성별 리턴 getter 메소드
     * 
     * @return 사용자 성별 리턴(남 : t, 여 : f)
     */
    public Boolean getSex() { return sex; }
    
    /**
     * 사용자 키 리턴 getter 메소드
     * 
     * @return 사용자 키 리턴
     */
    public double getHeight() { return height; }

    /**
     * 사용자 나이 리턴 getter 메소드
     * 
     * @return 사용자 나이 리턴
     */
    public int getAge() { return age; }

    /**
     * 사용자 초기 체중 리턴 getter 메소드
     * 
     * @return 사용자 직업 리턴
     */
    public double getStartWeight() { return startWeight; }

    /**
     * 사용자 목표 체중 getter 메소드
     * 
     * @return 사용자 전공 리턴
     */
    public double getGoalWeight() { return goalWeight; }

    /**
     * 사용자 성별 setter 메소드
     * 
     * @param sex 수정할 사용자 성별
     */
    public void setSex(boolean sex) { this.sex = sex; }

    /**
     * 사용자 키 setter 메소드
     * 
     * @param height 수정할 사용자 키
     */
    public void setHeight(double height) { this.height = height; }

    /**
     * 사용자 나이 setter 메소드
     * 
     * @param age 수정할 사용자 나이
     */
    public void setAge(int age) { this.age = age; }

    /**
     * 사용자 초기 체중 setter 메소드
     * 
     * @param startWeight 수정할 초기 체중
     */
    public void setStartWeight(double startWeight) { this.startWeight = startWeight; }

    /**
     * 사용자 목표 체중 setter 메소드
     * 
     * @param goalWeight 수정할 목표 체중
     */
    public void setGoalWeight(double goalWeight) { this.goalWeight = goalWeight; }

    /**
     * 사용자 정보를 리턴하는 메소드
     */
    @Override
    public String toString() {
        return (
            "User [ID = " + id +
            ", PW = " + pw +
            ", Sex = " + sex +
            ", Age = " + age +
            ", StartWeight = " + startWeight +
            ", GoalWeight = " + goalWeight +
            "]"
        );
    }
}
