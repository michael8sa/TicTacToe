---ORDER BY---
SELECT * FROM employees
ORDER BY first_name ASC, last_name DESC;

SELECT * FROM employees
ORDER BY birth_date DESC, hire_date DESC;

SELECT * FROM employees;
ORDER BY LENGTH(first_name) DESC, LENGTH(last_name) DESC;