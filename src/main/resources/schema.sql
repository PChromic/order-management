CREATE TABLE IF NOT EXISTS `Order` (
    id         INTEGER  PRIMARY KEY AUTO_INCREMENT,
     client_id VARCHAR(15) NOT NULL,
     request_id INTEGER  NOT NULL,
      name VARCHAR(15) NOT NULL,
      quantity INTEGER(15) NOT NULL,
      price DOUBLE(15) NOT NULL,

);