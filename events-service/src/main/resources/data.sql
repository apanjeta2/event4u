DROP TABLE IF EXISTS category;

CREATE TABLE category (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  name VARCHAR(250) NOT NULL,
);

INSERT INTO category (name) VALUES
  ('movies'),
  ('science'),
  ('IT');