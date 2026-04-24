CREATE TABLE users (
                       id BIGSERIAL PRIMARY KEY,
                       password VARCHAR(255) NOT NULL,
                       username VARCHAR(255) NOT NULL,
                       email VARCHAR(255) NOT NULL UNIQUE,
                       role VARCHAR(6) NOT NULL
);

CREATE TABLE wallets (
                         id BIGSERIAL PRIMARY KEY,
                         user_id BIGINT NOT NULL,
                         balance_usd NUMERIC(19, 8) NOT NULL DEFAULT 0,

                         CONSTRAINT fk_wallet_user
                             FOREIGN KEY (user_id)
                                 REFERENCES users(id)
                                 ON DELETE CASCADE
);
CREATE TABLE portfolios (
                            id BIGSERIAL PRIMARY KEY,
                            wallet_id BIGINT NOT NULL,
                            symbol VARCHAR(50) NOT NULL,
                            amount NUMERIC(19, 8) NOT NULL,

                            CONSTRAINT fk_portfolio_wallet
                                FOREIGN KEY (wallet_id)
                                    REFERENCES wallets(id)
                                    ON DELETE CASCADE
);
CREATE TABLE limit_orders (
                              id BIGSERIAL PRIMARY KEY,
                              wallet_id BIGINT NOT NULL,
                              target_price NUMERIC(19, 8) NOT NULL,
                              amount NUMERIC(19, 8) NOT NULL,
                              status VARCHAR(50) NOT NULL DEFAULT 'CREATED',
                              type VARCHAR(50) NOT NULL,
                              symbol VARCHAR(50) NOT NULL,

                              CONSTRAINT fk_limit_order_wallet
                                  FOREIGN KEY (wallet_id)
                                      REFERENCES wallets(id)
                                      ON DELETE CASCADE
);
CREATE UNIQUE INDEX idx_wallet_symbol ON portfolios(wallet_id, symbol);