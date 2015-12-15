DROP TABLE api_user_role;
DROP TABLE api_role;
DROP TABLE api_user;

CREATE TABLE api_user(
		id SERIAL UNIQUE,
		username VARCHAR(100) UNIQUE NOT NULL,
		email VARCHAR(100) UNIQUE NOT NULL,
		password VARCHAR(100) NOT NULL,
		enabled INTEGER NOT NULL,
		status VARCHAR(100) NOT NULL,
		position VARCHAR(100),
		approved_by INTEGER,
		approved_date INTEGER,
		created_date DATE,
		updated_date DATE,
		created_by   INTEGER,
		updated_by   INTEGER
);

CREATE TABLE api_role(
		id SERIAL UNIQUE,
		role_name VARCHAR(100)
);

CREATE TABLE api_user_role(
		id SERIAL UNIQUE,
		api_user_id  INTEGER REFERENCES api_user(id),
		api_role_id  INTEGER REFERENCES api_role(id)
);





SELECT * FROM api_user;


SELECT 
		id, username, password, email, enabled, status, position, approved_by, approved_date, created_date, updated_by, updated_date
FROM 
		api_user
WHERE 
		username = 'TOLA'

SELECT 
		api_role.id , api_role.role_name
FROM 
		api_user
LEFT JOIN api_user_role ON api_user_role.id = api_user.id
LEFT JOIN api_role ON api_role.id = api_user_role.id;

