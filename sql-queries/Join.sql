
---INNER JOIN---

SELECT 
	a.emp_no,
	CONCAT(a.first_name, ' ', a.last_name) AS "Name",
	b.salary,
	c.title,
	c.from_date AS "Promoted on"
FROM employees AS a
INNER JOIN salaries AS b ON a.emp_no = b.emp_no ---joining emplyees to salaries
INNER JOIN titles AS c 
ON c.emp_no = a.emp_no
	AND (c.from_date = (b.from_date + INTERVAL '2 days') OR 
	c.from_date = b.from_date
		)
ORDER BY a.emp_no ASC;

---Right Outer Join---Primary table is on the right--
SELECT a."Name" AS "pet_name", a."Kind", b."Name" AS "owner_name"
FROM OWNER AS b
RIGHT JOIN pets AS ALL
ON a."OwnerID" = b."OwnerID"
ORDER BY "pet_name";
---Left Join---

SELECT a."Name", a."Kind", b."Name"
FROM pets AS a
LEFT JOIN owners AS b
ON a."OwnerID" = b."OwnerID"
WHERE LEFT(a."Name", 1) = LEFT(b."Name", 1)
ORDER BY a."Kind";
---Tip. Aliases for columns---
SELECT a."Name" AS "pet_name", a."Kind", b."Name" AS "owner_name"
FROM pets AS a
LEFT JOIN owners AS b
ON a."OwnerID" = b."OwnerID"
WHERE LEFT(a."Name", 1) = LEFT(b."Name", 1)
ORDER BY "pet_name";

---Multiple fields Join---
SELECT * 
FROM procedures_history AS a 
LEFT JOIN procedures_details AS b
ON a."ProcedureType" = b."ProcedureType"
AND a."ProcedureSubCode" = b."ProcedureSubCode"
ORDER BY "Date";

SELECT a."PetID", a."Name", a."Kind", a."Gender", a."Age", a."OwnerID", b."Date", b."ProcedureType", b."ProcedureSubCode", c."Description", c."Price"  
FROM pets AS a
INNER JOIN procedures_history AS b
ON a."PetID" = b."PetID"
LEFT JOIN procedures_details AS c
ON b."ProcedureType" = c."ProcedureType"
AND b."ProcedureSubCode" = c."ProcedureSubCode"
ORDER BY "Date";
---Full Outer Join---
SELECT * FROM pets AS a
FULL OUTER JOIN procedures_history AS b
ON a."PetID" = b."PetID"


---Complex Joins---
SELECT * 
FROM pets AS a
INNER JOIN procedures_history AS b
ON a."PetID" = b."PetID"
LEFT JOIN procedures_details AS c
ON b."ProcedureType" = c."ProcedureType"
AND b."ProcedureSubCode" = c."ProcedureSubCode"
ORDER BY "Date";