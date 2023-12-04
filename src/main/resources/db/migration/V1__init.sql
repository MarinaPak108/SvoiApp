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
values (2, 'mrna', '$2a$10$RencK/MIa/HIfe.nMEpvkeeB4..ar3bI.yWtqDzNz15sdiSfvDifu', 'pakmarina108@gmail.com'),
       (3, 'mrn86', '$2a$10$6NynXlLDqF9c43ifhK01I.v.8m1NG2T08gbRSH5s1GN/7or/jwJTS', 'pakmarina86@googlemail.com'),
       (4, 'mrnai', '$2a$10$k9E8mxsIrQ7H0xuvVxjmw.8d1s7aLaorGhMEork6xmQz2BYhnhomq', 'ai22marina@gmail.com');
