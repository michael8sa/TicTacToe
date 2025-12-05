DROP TABLE IF EXISTS grocery_cus;
CREATE TABLE grocery_cus AS retail_grocery
SELECT * FROM retailcusids
WHERE customerid IN retail_grocery 

SELECT emp_no, first_name, last_name 
FROM 
    employees
WHERE
    emp_no IN(   ---outer query 
              SELECT DISTINCT
               reports_to
              FROM
               employees ---sub query
              );