create table clientes (
    id serial not null primary key,
    uuid UUID DEFAULT gen_random_uuid(),
    nome varchar(100) not null,
    cpf varchar(14) not null unique,
    email varchar(100),
    telefone varchar(20),
    data_nascimento date,
    complemento varchar(100),
    bairro varchar(100),
    cep varchar(9),
    numero varchar(20),
    cidade varchar(100),
    uf char(2)
);
