create database reg;

use reg;

create table registration (
    username varchar(255),
    email    varchar(255),
    password varchar(255),
    cnfmpass varchar(255),

    primary key(email)
);
insert into registration values(
    'Administrator', 'sujatadev97@gmail.com', 't13', 't13'
);
