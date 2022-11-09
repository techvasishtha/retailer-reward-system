    CREATE TABLE USERS(
    USER_ID INT PRIMARY KEY,
    USER_NAME VARCHAR(100)
);

CREATE TABLE REWARDS(
    ID INT PRIMARY KEY,
    USER_ID NUMBER(15),
    PRODUCT_NAME VARCHAR(50),
    PRODUCT_QUANTITY NUMBER(10),
    PRODUCT_PRICE NUMBER(30,5),
    REWARD_POINTS NUMBER(15),
    CREATED_DATE DATE

);