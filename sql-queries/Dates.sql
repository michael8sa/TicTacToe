

---b."Date", c."Price"
FROM pets AS a
INNER JOIN procedures_history AS b
ON a."PetID" = b."PetID"
LEFT JOIN procedures_details AS c
ON b."ProcedureType" = c."ProcedureType"
AND b."ProcedureSubCode" = c."ProcedureSubCode"
WHERE(EXTRACT(MONTH FROM DATE(b."Date")) = 1); 

SELECT "Date",
	EXTRACT (YEAR FROM "Date") AS YEAR,
	EXTRACT (MONTH FROM "Date") AS MONTH,
	EXTRACT (DAY FROM "Date") AS DAY
FROM
	procedures_history;

 ----------------Dates--------------------
SELECT * FROM console_dates
SELECT *, to_char("first_retail_availability", 'YYYY/MM/DD') 
- to_char("discontinued", 'YYYY/MM/DD') AS days_existed
FROM console_dates;

---

SELECT emp_no,
EXTRACT(YEAR FROM AGE(birth_date)) AS "age" 
FROM employees WHERE (EXTRACT(YEAR FROM AGE(birth_date)) > 60);

SELECT AGE(birth_date), * FROM employees
WHERE (
   EXTRACT (YEAR FROM AGE(birth_date))
) > 60;

---


