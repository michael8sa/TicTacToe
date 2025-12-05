---AND/OR---
SELECT * FROM public.customers
SELECT firstname, lastname FROM public.customers WHERE gender = 'F' AND state = 'OR' OR gender = 'F' AND state = 'NY'
SELECT firstname, lastname FROM public.customers WHERE (state = 'OR' OR state = 'NY') AND gender = 'F'
SELECT COUNT(customerid) FROM public.customers WHERE gender = 'F' AND state = 'OR' OR gender = 'F' AND state = 'NY'
---NOT---
SELECT firstname, lastname, age FROM public.customers
SELECT COUNT(customerid) FROM public.customers WHERE NOT age = 55 AND NOT age = 54;

---COMPARAISON---
SELECT customerid, income, age FROM public.customers WHERE age > 44 AND income = 100000
SELECT COUNT(customerid) FROM public.customers WHERE age > 44 AND income = 100000

---4.All rows with word Credit---
SELECT *
FROM consumer_complaints 
WHERE UPPER(product_name) LIKE '%CREDIT%';

--5.All rows with word late in the issue field---
SELECT *
FROM consumer_complaints 
WHERE UPPER("Issue") LIKE 'LATE%';

---LIKE---
/*
LIKE '%X'= Filed that end with X
LIKE '%X%'= Field that have X anywhere in the value
LIKE '_XY%'=Field that have X and Y as 2nd and 3rd character and anything after 
LIKE '%88%'=Field that have 88 anywhere in the value
LIKE 'X_%_%'=Finds any values that start with X and are at least 3 character in length
LIKE '2__3'= Finds any values in a 5 digits number that start with 2 and end with 3
*/
SELECT *
FROM consumer_complaints 
WHERE LOWER("product_name") LIKE 'credit _________';

SELECT *
FROM consumer_complaints 
WHERE LOWER("Tags") LIKE '%older%';

SELECT "Zip Code"
FROM consumer_complaints 
WHERE LOWER("Zip Code") LIKE '____';--Only zip code with 4 character
	
SELECT "Zip Code"
FROM consumer_complaints 
WHERE LOWER("Zip Code") LIKE '6__1';--Only zip code with 4 character and that start with 6 and end with 1

---

SELECT "Company", "Issue"
FROM consumer_complaints
WHERE UPPER("Issue") LIKE '%LATE%';

SELECT "product_name", "Zip Code"
FROM consumer_complaints
WHERE UPPER("Zip Code") LIKE '1____';

---BETWEEN + AND---

SELECT * FROM students 
SELECT "name", age FROM student WHERE age BETWEEN 30 AND 35;
SELECT "name", age FROM student WHERE age >= 30 AND age <= 35;

SELECT * FROM students 
WHERE birth BETWEEN DATE_TRUNC('year', DATE '1989-12-31') 
AND DATE_TRUNC('year', DATE '1990-12-31');