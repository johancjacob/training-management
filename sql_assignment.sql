--table creation
create table products(product_id int primary key,product_name varchar(20),category_id int,supplier_id int,price decimal);
create table categories(category_id int primary key,category_name varchar(20));
create table suppliers(supplier_id int primary key,supplier_name varchar(20),contact_email varchar(20));
create table inventory(inventory_id int primary key,product_id int,quantity int,last_updated date);
create table orders(order_id int primary key,product_id int,order_date date,quantity_ordered int,total_price decimal);

alter table products add foreign key(category_id) references categories(category_id);
alter table products add foreign key(supplier_id) references suppliers(supplier_id);

alter table inventory add foreign key(product_id) references products(product_id);
alter table inventory add unique(product_id);

alter table orders add foreign key(product_id) references products(product_id);


--values insertion
insert into products values(100,'pen',1000,090,20.0);
insert into products values(101,'pencil',1000,091,5.0);
insert into products values(102,'bat',2000,090,500);
insert into products values(103,'racquet',2000,091,850);
insert into products values(104,'ball',2000,090,120);
insert into products values(105,'laptop',3000,091,35000);
insert into products values(106,'television',3000,091,25000);
insert into products values(107,'printer',3000,091,6500);
insert into products values(108,'AC',3000,null,6500);

insert into categories values(1000,'stationery');
insert into categories values(2000,'sports');
insert into categories values(3000,'electronics');

insert into suppliers values(090,'Radisson Enterprises','radisson@gmail.com');
insert into suppliers values(091,'Ali Enterprises','ali21@gmail.com');
insert into suppliers values(092,'Reddy Enterprises','reddy12@gmail.com');

insert into inventory values(1,100,1000,'2024-09-21');
insert into inventory values(2,101,100,'2024-09-21');
insert into inventory values(3,102,102,'2024-08-22');
insert into inventory values(4,103,1000,'2024-09-21');
insert into inventory values(5,104,620,'2023-09-21');
insert into inventory values(6,105,10,'2024-06-03');
insert into inventory values(7,106,230,'2024-05-27');
insert into inventory values(8,107,870,'2023-06-25');

insert into orders values(1001,100,'2024-09-23',10,200);
insert into orders values(1002,100,'2024-09-26',12,240);
insert into orders values(1003,101,'2025-04-01',10,50);
insert into orders values(1004,106,'2024-09-04',2,50000);
insert into orders values(1005,106,'2024-10-28',1,25000);
insert into orders values(1006,107,'2024-05-23',4,26000);

--queries
1.select product_id,product_name,category_name from products join categories on products.category_id=categories.category_id;

2.select product_id,product_name,suppliers.supplier_name from products left join suppliers on products.supplier_id=suppliers.supplier_id;

3.select suppliers.supplier_id,supplier_name,product_id,product_name from products right join suppliers on products.supplier_id=suppliers.supplier_id;

4.select product_id,product_name,products.supplier_id,suppliers.supplier_name from products left join suppliers on products.supplier_id=suppliers.supplier_id
union
select product_id,product_name,suppliers.supplier_id,suppliers.supplier_name from products right join suppliers on products.supplier_id=suppliers.supplier_id;

5.select products.product_id,suppliers.supplier_name,inventory.quantity from products join suppliers on products.supplier_id=suppliers.supplier_id join inventory on products.product_id=inventory.product_id where quantity!=0;

6.select product_name,sum(quantity_ordered)as total_quantity_ordered,sum(total_price) as total_revenue from products join orders on products.product_id=orders.product_id group by products.product_id order by total_revenue desc;

7.select orders.order_id,orders.order_date,orders.quantity_ordered,orders.total_price,products.product_name,categories.category_name,suppliers.supplier_name from orders join products on orders.product_id=products.product_id join categories on products.category_id=categories.category_id join suppliers on products.supplier_id=suppliers.supplier_id;

8.select products.product_name,suppliers.supplier_name,inventory.quantity from products join suppliers on products.supplier_id=suppliers.supplier_id join inventory on products.product_id=inventory.product_id where quantity<10;

9.
  a)select suppliers.supplier_id,suppliers.supplier_name,count(distinct products.category_id) as category_count from products join suppliers on products.supplier_id=suppliers.supplier_id                                                 group by suppliers.supplier_id having category_count>0;
  b)select product_id,product_name from products where product_id not in (select distinct product_id from orders);
  c)select products.category_id,sum(total_price) from orders join products on orders.product_id=products.product_id group by category_id; 
