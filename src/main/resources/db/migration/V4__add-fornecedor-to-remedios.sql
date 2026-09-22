alter table remedios
    add column fornecedor_id integer references fornecedores(id) on delete set null;
