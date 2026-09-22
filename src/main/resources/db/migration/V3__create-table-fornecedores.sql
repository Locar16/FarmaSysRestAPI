create table fornecedores (
    id serial not null primary key,
    uuid UUID DEFAULT gen_random_uuid(),
    razao_social varchar(150) not null,
    cnpj varchar(18) not null unique,
    email varchar(100),
    telefone varchar(20),
    complemento varchar(100),
    bairro varchar(100),
    cep varchar(9),
    numero varchar(20),
    cidade varchar(100),
    uf char(2)
);
