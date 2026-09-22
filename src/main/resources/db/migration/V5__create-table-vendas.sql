create table vendas (
    id serial not null primary key,
    uuid UUID DEFAULT gen_random_uuid(),
    data_hora timestamp not null,
    valor_total decimal(10,2) not null,
    forma_pagamento varchar(20) not null,
    cliente_id integer references clientes(id) on delete set null
);
