delete
from USER;
delete
from CURRENCY_EXCHANGE;
insert into USER (id, login, email, password)
values (1, 'Max', 'mu5cool@yandex.ru', '$2y$12$JqTrBjOUdluWhgHQJUp5jOHXsFSJ47tsX9rBw.Up/MvB9tO7RIaj6'),
       (2, 'Alex', 'alex@mail.ru', '$2y$12$eCKFh6FW0YEP2bYcUg8VOuzKaiZZ0QWyrq.8fweaCpOZmc/smAw3O');

insert into CURRENCY_EXCHANGE
values (1, '2020-03-19', 'EUR', 11, 'Евро', 'USD', 11.98, 'Доллар США', 1),
       (2, '2020-03-19', 'AUD', 1, 'Австралийский доллар', 'USD', 1.13, 'Азербайджанский манат', 2),
       (3, '2020-03-19', 'AMD', 1213, 'Армянских драмов', 'AUD', 39791.61, 'Австралийский доллар', 1);

alter sequence hibernate_sequence restart with 4;



