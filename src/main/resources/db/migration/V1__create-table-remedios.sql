create table remedios (
    id serial not null primary key,
    uuid UUID DEFAULT gen_random_uuid(),
    nome varchar(100) not null,
    principio_ativo varchar(100),
    preco decimal(10,2) not null,
    quantidade_estoque integer not null default 0,
    necessita_receita boolean not null default false
);
