delete
from CURRENCY_EXCHANGE;
delete
from USER;
insert into USER (id, login, password)
values (1, 'a', '1'),
       (2, 'b', '2');

insert into CURRENCY_EXCHANGE
values (1, '2020-03-19', 'EUR', 11, 'Евро', 'USD', 11.98, 'Доллар США', 1),
       (2, '2020-03-19', 'AUD', 1, 'Австралийский доллар', 'USD', 1.13, 'Азербайджанский манат', 2),
       (3, '2020-03-19', 'AMD', 1213, 'Армянских драмов', 'AUD', 39791.61, 'Австралийский доллар', 1);

alter sequence hibernate_sequence restart with 4;

