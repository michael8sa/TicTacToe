---groupby---

---1.For each product_id transaction nber and Total sales---
SELECT product_id,
       count(sales_id) AS "nber_transactions",
       ROUND(SUM(sale_amount)) AS "Total"        
FROM public.sale_records WHERE product_id IN ('P0', 'P1', 'P2')
GROUP BY product_id
ORDER BY "Total" ASC;

SELECT product_id, sum(sale_amount) AS "total",
       count(sales_id) AS nber_transactions,
       avg(sale_amount) AS avg_price 
FROM public.sale_records
WHERE sale_amount > 100
GROUP BY product_id;


SELECT sales_id, product_id,
       count(*) AS nber_transactions,
       avg(sale_amount) AS avg_price 
FROM public.sale_records
WHERE sale_amount > 100
GROUP BY sales_id, product_id;

SELECT
    continent,
    SUM(population) AS "Population_per_Continent"    
FROM country
GROUP BY continent
ORDER BY "Population_per_Continent";

---CHALLENGE  Database: World, Table: Country---
---1.Show the population per continent---
SELECT
    continent,
    SUM(population) AS "Population_per_Continent"    
FROM country
GROUP BY continent
ORDER BY "Population_per_Continent";

-----DISTINCT------
SELECT * FROM salaries;
SELECT DISTINCT(emp_no) FROM salaries;
---1.What unique titles do we have?
SELECT * FROM titles;
SELECT DISTINCT(title) FROM titles;
SELECT COUNT(DISTINCT(title)) FROM titles;
---2.How many unique birth dates are there?
SELECT DISTINCT (birth_date) FROM employees;
SELECT COUNT(DISTINCT birth_date) FROM employees;
---3.Can I get a list of distinct life expectancy ages Make sure there are no nulls
SELECT * FROM country;
SELECT DISTINCT(lifeexpectancy) FROM country WHERE lifeexpectancy IS NOT NULL ORDER BY lifeexpectancy DESC;

SELECT DISTINCT lifeexpectancy FROM country
WHERE lifeexpectancy IS NOT NULL ORDER BY lifeexpectancy ASC;


---Alternativ---
SELECT
  DISTINCT continent,
  SUM(population) OVER w1 AS"continent population"
FROM country 
WINDOW w1 AS( PARTITION BY continent )

---Alternativ
SELECT
  DISTINCT continent,
  SUM(population) OVER w1 AS"continent population",
  CONCAT( 
      ROUND( 
          ( 
            SUM( population::float4 ) OVER w1 / 
            SUM( population::float4 ) OVER() 
          ) * 100    
      ),'%' ) AS "percentage of population"
FROM country 
WINDOW w1 AS( PARTITION BY continent );