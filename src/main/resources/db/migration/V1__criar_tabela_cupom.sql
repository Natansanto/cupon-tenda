CREATE TABLE cupom (
    id UUID PRIMARY KEY,
    codigo VARCHAR(6) NOT NULL,
    descricao VARCHAR(1000) NOT NULL,
    valor_desconto DECIMAL(19, 4) NOT NULL,
    data_expiracao TIMESTAMP NOT NULL,
    status VARCHAR(20) NOT NULL,
    publicado BOOLEAN NOT NULL,
    resgatado BOOLEAN NOT NULL
);
