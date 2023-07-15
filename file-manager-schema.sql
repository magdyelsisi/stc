create table file (id  bigserial not null, binary_data oid not null, item_id int8 not null, primary key (id));
create table item (id  bigserial not null, name varchar(255) not null, type varchar(255) not null, parent_item_id int8, permission_group_id int8, primary key (id));
create table permission (id  bigserial not null, permission_level varchar(255) not null, user_email varchar(255) not null, group_id int8 not null, primary key (id));
create table permission_group (id  bigserial not null, name varchar(255) not null, primary key (id));
alter table file add constraint FK5gx9qw46gc6mr17noimfp2o4y foreign key (item_id) references item;
alter table item add constraint FKa3rmlgvinyx0de6jvogft00r8 foreign key (parent_item_id) references item;
alter table item add constraint FKjtcinrue6wu7cvno03243s3jy foreign key (permission_group_id) references permission_group;
alter table permission add constraint FKqp7umovkuakff1jilk6dp9l1x foreign key (group_id) references permission_group;