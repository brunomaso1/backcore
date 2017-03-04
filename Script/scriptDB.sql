drop database deliverit;

create database deliverit;

create table deliverit.vehiculo (
	id smallint auto_increment primary key,
    descripcion varchar(10)
);

insert into deliverit.vehiculo (descripcion) values ('Automotor');
insert into deliverit.vehiculo (descripcion) values ('Ciclomotor');
insert into deliverit.vehiculo (descripcion) values ('Bicileta');
insert into deliverit.vehiculo (descripcion) values ('Skate');
insert into deliverit.vehiculo (descripcion) values ('Rollers');
insert into deliverit.vehiculo (descripcion) values ('Ninguno');

create table deliverit.estado_viaje (
	id smallint auto_increment primary key,
    descripcion varchar(10)
);

insert into deliverit.estado_viaje (descripcion) values ('Pendiente');
insert into deliverit.estado_viaje (descripcion) values ('Publicado');
insert into deliverit.estado_viaje (descripcion) values ('En proceso');
insert into deliverit.estado_viaje (descripcion) values ('Finalizado');

create table deliverit.direccion (
	id integer auto_increment primary key,
    calle varchar(20) not null,
    nro_puerta smallint not null,
    esquina varchar(20),
    apartamento smallint,
    latitud double,
    longitud double
);

create table deliverit.cliente (
	id integer auto_increment primary key,
    nombre varchar (50),
    direccion integer,
    telefono varchar(9) not null,
    foreign key (direccion) references deliverit.direccion (id)
);

create table deliverit.usuario (
	id integer auto_increment primary key,
    nombre varchar(40) unique not null,
    password varchar(100) not null,
    mail varchar(50),
    telefono varchar(9) not null,
    cuenta_red_pagos integer not null,
    foto blob
);

create table deliverit.restaurant (
	id integer auto_increment primary key,
	usuario integer,
    rut bigint unique not null,
    razon_social varchar(50),
    foreign key (usuario) references deliverit.usuario (id)
);

create table deliverit.ubicacion (
	id smallint primary key,
    latitud double,
    longitud double
);

create table deliverit.delivery (
	id integer auto_increment primary key,
	usuario integer,
    calificacion smallint,
    vehiculo smallint,
	token varchar(200),
    ubicacion smallint,
    foreign key (vehiculo) references deliverit.vehiculo (id),
    foreign key (usuario) references deliverit.usuario (id),
    foreign key (ubicacion) references deliverit.ubicacion(id)
);

create table deliverit.sucursal (
	id smallint auto_increment,
    restaurant integer,
    nombre varchar(20),
    direccion integer,
    primary key (id, restaurant),
    foreign key (restaurant) references deliverit.restaurant (id),
    foreign key (direccion) references deliverit.direccion (id)
);

create table deliverit.viaje (
	id integer auto_increment primary key,
    calificacion smallint,
    precio smallint not null,
    delivery integer,
    sucursal smallint,
    restaurant integer,
    estado smallint,
    foreign key (delivery) references deliverit.delivery (id),
    foreign key (sucursal) references deliverit.sucursal (id),
    foreign key (restaurant) references deliverit.sucursal (restaurant),
    foreign key (estado) references deliverit.estado_viaje (id)
);

create table deliverit.transaccion (
	id integer auto_increment primary key,
    fecha_hora timestamp not null,
    monto double not null,
    viaje integer not null,
    foreign key (viaje) references deliverit.viaje (id)
);

create table deliverit.pedido (
	id integer auto_increment,
    viaje integer,
    detalle varchar(100),
    forma_pago varchar(1),
    cliente integer not null,
    primary key (id, viaje),
    foreign key (viaje) references deliverit.viaje(id),
    foreign key (cliente) references deliverit.cliente(id)
);

create table deliverit.configuracion (
	id integer auto_increment primary key,
    descripcion varchar(20) not null unique,
    valor varchar(200) not null
);

insert into deliverit.configuracion (descripcion, valor) values ('API_KEY_GOOGLE', 'AIzaSyA2NCnQBRCbuXHbNeUY7mW-lvP-v4V3x8A');
insert into deliverit.configuracion (descripcion, valor) values ('SERVIDOR_FIREBASE', 'cGAdEQelHgw:APA91bEeGdm8QyYE0PajHZTmkEHfQeJUZsHJL0luuzx6JD6xUtq4dOuqQd8INNr4O6J4y5ftbrQ5O3pqQSS_m2Ou_LXjyPYfvY44bx8YJqLaY-qN_Sm3JHBpNHeEHU9TnDbBx0u7tyWh');