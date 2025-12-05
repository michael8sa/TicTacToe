---Aggregate functions--- AVG(), COUNT, MIN(), MAX(), SUM()

SELECT COUNT(emp_no) FROM "employees"
SELECT * FROM public.salaries             
SELECT MAX(salary) FROM public.salaries
SELECT emp_no FROM public.salaries WHERE salary = 158220
SELECT SUM(salary) FROM public.salaries
SELECT first_name, last_name FROM public.employees WHERE "first_name" = 'Mayumi' AND "last_name" = 'Schueller';