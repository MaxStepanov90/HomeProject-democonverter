delete from currency_exchange;
delete from usr;
insert into usr (id,login, password ) values (1, 'a', '1');
insert into usr (id,login, password ) values (2, 'b', '2');

insert into currency_exchange values (1,'2020-03-19','EUR',11,'Евро','USD',11.98,'Доллар США',1);
insert into currency_exchange values (2,'2020-03-19','AUD',1,'Австралийский доллар','USD',1.13,'Азербайджанский манат',2);
insert into currency_exchange values (3,'2020-03-19','AMD',1213,'Армянских драмов','AUD',39791.61,'Австралийский доллар',1);