create
sequence hibernate_sequence start 100 increment 1;

CREATE TABLE IF NOT EXISTS product
(
    id          bigserial primary key not null,
    title       text                  not null,
    description text                  not null,
    cost        float                 not null,
    img_link    text
);

CREATE TABLE IF NOT EXISTS category
(
    id        bigserial primary key not null,
    title     text                  not null,
    alias     text                  not null,
    parent_id int,
    foreign key (parent_id) references category (id)
);

create index category_parent_id_idx on category (parent_id);


CREATE TABLE IF NOT EXISTS product_category
(
    product_id  bigint not null,
    category_id bigint not null,

    primary key (product_id, category_id),
    foreign key (product_id) references product (id),
    foreign key (category_id) references category (id)
);

INSERT INTO category(id, title, alias)
VALUES (1, 'test_root', 'test_root_alias');

INSERT INTO category(id, title, alias, parent_id)
VALUES (2, 'test2', 'test_alias2', 1),
       (3, 'test3', 'test_alias3', 1),
       (4, 'test4', 'test_alias4', 3),
       (5, 'test5', 'test_alias5', 3);

INSERT INTO product(id, title, description, cost, img_link)
VALUES (1, 'test_product1', 'test description', 0.01, 'assets/img/products/1.jpg'),
       (2, 'test_product2', 'test description', 0.01, 'assets/img/products/1.jpg'),
       (3, 'test_product3', 'test description', 0.01, 'assets/img/products/1.jpg'),
       (4, 'test_product4', 'test description', 0.01, 'assets/img/products/1.jpg'),
       (5, 'test_product5', 'test description', 0.01, 'assets/img/products/1.jpg'),
       (6, 'test_product6', 'test description', 0.01, 'assets/img/products/1.jpg'),
       (7, 'test_product7', 'test description', 0.01, 'assets/img/products/1.jpg'),
       (8, 'test_product8', 'test description', 0.01, 'assets/img/products/1.jpg'),
       (9, 'test_product9', 'test description', 0.01, 'assets/img/products/1.jpg'),
       (10, 'test_product10', 'test description', 0.01, 'assets/img/products/1.jpg'),
       (11, 'test_product11', 'test description', 0.01, 'assets/img/products/1.jpg'),
       (12, 'test_product12', 'test description', 0.01, 'assets/img/products/1.jpg');


INSERT INTO product_category(product_id, category_id)
VALUES (1, 1),
       (2, 2),
       (3, 3),
       (4, 4),
       (5, 5),
       (6, 2),
       (7, 3),
       (8, 4),
       (9, 5),
       (10, 5),
       (11, 4),
       (12, 2);

alter
sequence product_id_seq restart with 100;
alter
sequence category_id_seq restart with 100;