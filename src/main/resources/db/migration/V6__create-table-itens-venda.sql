create table itens_venda (
    id serial not null primary key,
    venda_id integer not null references vendas(id) on delete cascade,
    remedio_id integer not null references remedios(id),
    quantidade integer not null check (quantidade > 0),
    valor_unitario decimal(10,2) not null
);
