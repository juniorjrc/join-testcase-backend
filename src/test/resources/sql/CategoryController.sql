INSERT INTO join_category(id, category_name, description, min_price, max_price)
VALUES
(1, 'Cell phones', null, 1000, 20000),
(2, 'Tablets', null, 700, 10000),
(3, 'Computers', null, 1000, 75000),
(4, 'Consoles', null, null, null);

INSERT INTO join_product(id, product_name, product_description, category_id, price)
VALUES
(1, 'Dell Notebook', '', 3, 7500);