create table cars(
id integer primary key,
brand text not null,
model text not null,
price numeric (7,2)
);

create table drivers(
id integer primary key,
name text not null,
age integer,
license boolean,
carId integer references cars (id)
);



