insert into tb_categoria(id, nome)
values
	(gen_random_uuid(), 'Família'),
	(gen_random_uuid(), 'Trabalho'),
	(gen_random_uuid(), 'Amigos'),
	(gen_random_uuid(), 'Estudo'),
	(gen_random_uuid(), 'Lazer'),
	(gen_random_uuid(), 'Outros');
	
select * from tb_categoria
order by nome;

