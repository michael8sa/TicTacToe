------------------CONDITIONALS
---1.CASE STATEMENT
---Syntax
SELECT x,    
    CASE WHEN x=1 THEN 'one'
         WHEN x=2 THEN 'two'
         ELSE 'other'
    END
FROM "table_name";

SELECT
    o.orderid,
    o.customerid,
    CASE
        WHEN o.customerid = 1
        THEN 'My 1st customer'
        ELSE 'Not my 1st customer'
    END,
    o.netamount
FROM orders AS o
ORDER BY o.customerid;