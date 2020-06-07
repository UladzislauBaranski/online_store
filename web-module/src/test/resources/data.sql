INSERT INTO user(surname, name, patronymic, address, telephone, email, role, password, is_exist)
VALUES ('testSurname', 'testName', 'testPatronymic', 'testAddress', 'testTelephone', 'email@gmail.com', 'ADMINISTRATION',
        '$2y$12$8PI0mf9NXsDjxnbYS7xGIuuPLwLlidD5tM241nZPFdhrMKVgt.LNe', '1');

INSERT INTO article(date, title, summary, content, user_id)
VALUES ('2012-12-12', 'testTitle', 'testSummary', 'testContent', 1);

INSERT INTO item(title, unique_number, price, summary)
VALUES ('testTitle', '1', '25.5', 'testSummary');

INSERT INTO order_table(order_number, order_status, number_of_items, total_price, date, user_id)
VALUES ('1', 'NEW', '1', '25.5', '2012-12-12', 1);

INSERT INTO review(review, date, show_status, user_id)
VALUES ('testReview', '2012-12-12', true, 1);
