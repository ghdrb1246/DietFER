-- users          ─ 사용자 정보
-- workouts       ─ 운동 기록
-- weights        ─ 체중 기록
-- meals          ─ 식단 기록

CREATE TABLE users (
    user_id        VARCHAR(50) PRIMARY KEY,
    password       VARCHAR(100) NOT NULL,
    sex            BOOLEAN NOT NULL,   -- true: 남, false: 여
    height         DOUBLE NOT NULL,
    age            INT NOT NULL,
    start_weight   DOUBLE NOT NULL,
    goal_weight    DOUBLE NOT NULL,
    created_at     TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE workouts (
    workout_id     INT AUTO_INCREMENT PRIMARY KEY,
    user_id        VARCHAR(50) NOT NULL,
    workout_time   TIMESTAMP NOT NULL,
    exercise_name  VARCHAR(100) NOT NULL,
    minutes        DOUBLE NOT NULL,
    kcal           DOUBLE NOT NULL,

    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

CREATE TABLE weights (
    weight_id   INT AUTO_INCREMENT PRIMARY KEY,
    user_id     VARCHAR(50) NOT NULL,
    record_time TIMESTAMP NOT NULL,
    weight      DOUBLE NOT NULL,

    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

CREATE TABLE meals (
    meal_id        INT AUTO_INCREMENT PRIMARY KEY,
    user_id        VARCHAR(50) NOT NULL,
    meal_time      TIMESTAMP NOT NULL,
    food_name      VARCHAR(100) NOT NULL,
    food_type      VARCHAR(20) NOT NULL, -- 아침/점심/저녁/간식
    gram           DOUBLE NOT NULL,
    kcal           DOUBLE NOT NULL,
    carbohydrate   DOUBLE NOT NULL,
    protein        DOUBLE NOT NULL,
    fat            DOUBLE NOT NULL,

    FOREIGN KEY (user_id) REFERENCES users(user_id)
);