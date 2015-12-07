
delete from role_permission;
delete from  user_role;
delete from  permissions;
delete from  roles;
delete from  users;

delete from  order_items;
delete from  orders;
delete from  payments;
delete from  customers;
delete from  addresses;
delete from  products;
delete from  categories;

INSERT INTO permissions (id, name) VALUES 
(1, 'MANAGE_CATEGORIES'),
(2, 'MANAGE_PRODUCTS'),
(3, 'MANAGE_ORDERS'),
(4, 'MANAGE_CUSTOMERS'),
(5, 'MANAGE_PAYMENT_SYSTEMS'),
(6, 'MANAGE_USERS'),
(7, 'MANAGE_ROLES'),
(8, 'MANAGE_PERMISSIONS'),
(9, 'MANAGE_SETTINGS')
;

INSERT INTO roles (id, name) VALUES 
(1, 'ROLE_SUPER_ADMIN'),
(2, 'ROLE_ADMIN'),
(3, 'ROLE_CMS_ADMIN'), 
(4, 'ROLE_USER')
;

INSERT INTO users (id, email, password, name) VALUES 
(1, 'superadmin@gmail.com', '$2a$10$p3PHnpoBAnzZiL8mr3gMieMhVVSb585ajC2ZsBB0kwb4KvZKFSdNi', 'Super Admin'),
(2, 'admin@gmail.com', '$2a$10$hKDVYxLefVHV/vtuPhWD3OigtRyOykRLDdUAp80Z1crSoS1lFqaFS', 'Admin'),
(3, 'cms@gmail.com', '$2a$10$/Y0IarSOuH2dz.UOLrZbaus17K6AJF7n15qWr02aWN1MOz5vEKT8y', 'CMSGuy'),
(4, 'siva@gmail.com', '$2a$10$UFEPYW7Rx1qZqdHajzOnB.VBR3rvm7OI7uSix4RadfQiNhkZOi2fi', 'Siva'),
(5, 'user@gmail.com', '$2a$10$ByIUiNaRfBKSV6urZoBBxe4UbJ/sS6u1ZaPORHF9AtNWAuVPVz1by', 'DemoUser')

;

insert into role_permission(role_id, perm_id) values
(1,1),(1,2),(1,3),(1,4),(1,5),(1,6),(1,7),(1,8),(1,9),
(2,1),(2,2),(2,3),(2,4),(2,5),(2,9),
(3,1),(3,2)
;

insert into user_role(user_id, role_id) values
(1,1),
(2,2),
(3,3),
(4,2),(4,3),
(5,4)
;

insert into categories(id, name, disp_order,disabled) values
(1,'Flowers',1,false),
(2,'Toys',2,false),
(3,'Birds',3,false)
;

INSERT INTO products (id,cat_id,sku,name,description,image_url,price,disabled,created_on) VALUES
 (1,1,'P1001','Quilling Toy 1','Quilling Toy 1','1.jpg','430.00',false,now()),
 (2,2,'P1002','Quilling Toy 2','Quilling Toy 2','2.jpg','490.00',false,now()),
 (3,3,'P1003','Quilling Toy 3','Quilling Toy 3','3.jpg','400.00',false,now()),
 (4,1,'P1004','Quilling Toy 4','Quilling Toy 4','4.jpg','430.00',false,now()),
 (5,2,'P1005','Quilling Toy 5','Quilling Toy 5','5.jpg','750.00',false,now()),
 (6,3,'P1006','Quilling Toy 6','Quilling Toy 6','6.jpg','350.00',false,now()),
 (7,1,'P1007','Quilling Toy 7','Quilling Toy 7','7.jpg','220.00',false,now()),
 (8,2,'P1008','Quilling Toy 8','Quilling Toy 8','8.jpg','120.00',false,now()),
 (9,3,'P1009','Quilling Toy 9','Quilling Toy 9','9.jpg','150.00',false,now()),
 (10,1,'P1010','Quilling Toy 10','Quilling Toy 10','10.jpg','460.00',false,now()),
 (11,2,'P1011','Quilling Toy 11','Quilling Toy 11','11.jpg','440.00',false,now()),
 (12,3,'P1012','Quilling Toy 12','Quilling Toy 12','12.jpg','450.00',false,now()),
 (13,1,'P1013','Quilling Toy 13','Quilling Toy 13','13.jpg','470.00',false,now()),
 (14,2,'P1014','Quilling Toy 14','Quilling Toy 14','14.jpg','250.00',false,now()),
 (15,3,'P1015','Quilling Toy 15','Quilling Toy 15','15.jpg','450.00',false,now()),
 (16,1,'P1016','Quilling Toy 16','Quilling Toy 16','16.jpg','150.00',false,now()),
 (17,2,'P1017','Quilling Toy 17','Quilling Toy 17','17.jpg','450.00',false,now()),
 (18,3,'P1018','Quilling Toy 18','Quilling Toy 18','18.jpg','450.00',false,now()),
 (19,1,'P1019','Quilling Toy 19','Quilling Toy 19','19.jpg','400.00',false,now()),
 (20,2,'P1020','Quilling Toy 20','Quilling Toy 20','20.jpg','450.00',false,now()),
 (21,3,'P1021','Quilling Toy 21','Quilling Toy 21','21.jpg','450.00',false,now()),
 (22,1,'P1022','Quilling Toy 22','Quilling Toy 22','22.jpg','550.00',false,now()),
 (23,2,'P1023','Quilling Toy 23','Quilling Toy 23','23.jpg','450.00',false,now()),
 (24,3,'P1024','Quilling Toy 24','Quilling Toy 24','24.jpg','450.00',false,now()),
 (25,1,'P1025','Quilling Toy 25','Quilling Toy 25','25.jpg','250.00',false,now())
;

INSERT INTO customers (id,firstname,lastname,email,phone,password) 
VALUES
  (1,'Siva','K','sivaprasadreddy.k@gmail.com','999999999','$2a$10$UFEPYW7Rx1qZqdHajzOnB.VBR3rvm7OI7uSix4RadfQiNhkZOi2fi'),
  (2,'Ramu','P','ramu@gmail.com','8888888888','$2a$10$UoEimdoV95/jTs2E99ARLO.eUBxYVcDZamedqhkwfPUx9iOMFEfyq')
  ;

insert into addresses (id, address_line1, address_line2, city, state, zip_code, country) values 
(1, 'Hitech City', 'Cyberabad', 'Hyderabad', 'TS', '500088', 'IN');

insert into payments (id, cc_number, cvv, amount) values 
(1, '1111111111111111', '123', 430.00);

insert into orders (id, order_number, cust_id, billing_addr_id, delivery_addr_id, payment_id, status, created_on) values 
(1, '1447737431927', 1, 1, 1, 1, 'NEW', now());

insert into order_items (id, order_id, price, product_id, quantity) values 
(1,1, 430.00, 1, 1);