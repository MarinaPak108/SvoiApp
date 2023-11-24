Create table authorities(
    authid int not null PRIMARY KEY AUTO_INCREMENT,
    name varchar(255)
);

insert into authorities (name) values ('ROLE_ADMIN'), ('ROLE_MEMBER'), ('ROLE_USER'), ('ROLE_GUEST');

CREATE table data(
    dataid bigint not null primary key auto_increment,
    registered timestamp default current_timestamp not null,
    auth int references authorities(authid),
    login varchar(255) UNIQUE not null,
    pwd varchar(255),
    email varchar(255) UNIQUE not null,
    pin int
);

insert into data (auth, login, pwd, email)
values (2, 'mrna', 'mrna', 'pakmarina108@gmail.com'),
       (3, 'mrn86', 'mrn86', 'pakmarina86@googlemail.com'),
       (4, 'mrnai', 'mrnai', 'ai22marina@gmail.com');
