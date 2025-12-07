CREATE TABLE products (
                          product_id   BIGINT AUTO_INCREMENT PRIMARY KEY,
                          name         VARCHAR(100) NOT NULL,
                          price        INT NOT NULL,
                          description  TEXT,
                          created_at   DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                          updated_at   DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE product_stock (
                               stock_id     BIGINT AUTO_INCREMENT PRIMARY KEY,
                               product_id   BIGINT NOT NULL,
                               stock        INT NOT NULL,
                               version      INT NOT NULL DEFAULT 0,
                               updated_at   DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

                               CONSTRAINT fk_product_stock_product
                                   FOREIGN KEY (product_id) REFERENCES products(product_id)
                                       ON DELETE CASCADE,

                               UNIQUE KEY uk_product_stock_product (product_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE stock_decrease_log (
                                    log_id      BIGINT AUTO_INCREMENT PRIMARY KEY,
                                    product_id  BIGINT NOT NULL,
                                    stock_id    BIGINT NOT NULL,
                                    quantity    INT NOT NULL,
                                    status      VARCHAR(10) NOT NULL, -- SUCCESS / FAIL
                                    strategy    VARCHAR(20) NOT NULL, -- PESSIMISTIC / OPTIMISTIC / REDIS
                                    elapsed_ms  INT NOT NULL,
                                    error_msg   TEXT,
                                    created_at  DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

                                    CONSTRAINT fk_log_product
                                        FOREIGN KEY (product_id) REFERENCES products(product_id)
                                            ON DELETE CASCADE,

                                    CONSTRAINT fk_log_stock
                                        FOREIGN KEY (stock_id) REFERENCES product_stock(stock_id)
                                            ON DELETE CASCADE,

                                    INDEX idx_log_product (product_id),
                                    INDEX idx_log_created_at (created_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
