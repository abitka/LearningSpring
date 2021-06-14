DROP TABLE IF EXISTS books;
-- DROP TABLE IF EXISTS upload_files;

CREATE TABLE books (
    id INT AUTO_INCREMENT PRIMARY KEY,
    author VARCHAR (250) NOT NULL,
    title VARCHAR (250) NOT NULL,
    size INT DEFAULT NULL
);

-- CREATE TABLE uploadFiles (
--     id INT PRIMARY KEY,
--     filename VARCHAR (250) NOT NULL,
--     filepath VARCHAR (250) NOT NULL,
-- );