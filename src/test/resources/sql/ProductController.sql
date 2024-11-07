INSERT INTO join_category(id, category_name, description, min_price, max_price)
VALUES
(1, 'Cell phones', null, 1000, 20000),
(2, 'Tablets', null, 700, 10000),
(3, 'Computers', null, 1000, 75000),
(4, 'Consoles', null, null, null);

INSERT INTO join_product(id, product_name, product_description, category_id, price, technical_specifications, imei_number, operating_system)
VALUES
(10, 'Iphone 16', 'Iphone 16 - 2024', 1, 19000, null, 'AAAAXXX55', null),
(20, 'Dell notebook', 'Notebook Dell i5', 3, 15000, null, null, 'Windows 11'),
(30, 'Samsung Tab', 'Samsung Tab 8gen', 2, 8000, 'No info', null, null),
(40, 'Tablet Nokia', '', 2, 8000, 'No info', null, null);

