
---Create Table---

DROP TABLE IF EXISTS my_table;
CREATE TABLE my_table 
(
    movie_name VARCHAR(200),---(Character and numbers)
    movie_year INTEGER,
    "country" VARCHAR (100),
    "genre" VARCHAR NOT NULL,
    PRIMARY KEY (movie_name, movie_year)
);

COPY my_table FROM 'D://movies.csv' DELIMITER = ',' CSV HEADER

INSERT INTO my_table VALUES 
(
    'Wall Street: Money never sleep', 2010, 'USA', 'Drama'     
),
(
    'Gladiator', 2000, 'USA', 'Action, Adventure' 
),
(
    'The Lord of the Rings: The Fellowship of the Ring', 2001, 'USA', 'Fantasy, Avanture' 
),
(
    'Inside Job', 2010, 'USA', 'Documentary, Crime'
),
(
    'The Hobbit: The Desolation of Smaug', 2013, 'USA', 'Fantasy, Adventure'
);

---Rename "columns"---
SELECT "emp_no" 
AS "Employee_#", "birth_date" 
AS "Birthday"  
FROM "employees";

---Concat "columns"---
SELECT * FROM "titles"
SELECT concat( emp_no, ' is a ', title) AS "employee_Title" FROM "titles" ---To concat 2 columns and add a string in between
SELECT * FROM "employees"
SELECT concat( emp_no, ' was hired on ', hire_date) AS "employee_hire_date" FROM "employees";
SELECT emp_no,concat( first_name, ' ', last_name) AS "full_name" FROM "employees";

---DATA CONVERSION---
SELECT *
FROM console_games
ORDER BY "Rank" DESC;

SELECT CAST("Year" AS VARCHAR(4))
FROM console_games
ORDER BY "Rank"; 

SELECT "Year":: VARCHAR(4)
FROM console_games
ORDER BY "Rank"; 

SELECT to_date(CAST("Year" AS VARCHAR(4)), 'yyyy')
FROM console_games
ORDER BY "Rank"; 