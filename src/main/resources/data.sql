insert into SEC_User (email, encryptedPassword, ENABLED)
values ('user@car.com', '$2a$12$53i0JSFITzlrbwdcZ8qrD.3CtNv.OFNqdhIHM3nhinrDnLX54ypQa', 1);

insert into SEC_User (email, encryptedPassword, ENABLED)
values ('admin@car.com', '$2a$12$53i0JSFITzlrbwdcZ8qrD.3CtNv.OFNqdhIHM3nhinrDnLX54ypQa', 2);

insert into sec_role (roleName)
values ('ROLE_USER');
 
insert into sec_role (roleName)
values ('ROLE_ADMIN');
 
insert into user_role (userId, roleId)
values (1, 1);
 
insert into user_role (userId, roleId)
values (2, 2);

insert into user_role (userId, roleId)
values (2, 1);


INSERT INTO manufacturer (manufacturer) VALUES ('Maserati'),('Lexus'),('Mercedes-Benz'),('Hyundai'),('Volkswagen'),('Chevrolet');

INSERT INTO car (manufacturerID, model, year, color, price)

VALUES (1,'Quattroporte',2011,'Silver',15295.00),(2,'LS',2007,'Red',15995.00),(3,'S-Class',2012,'Brown',18995.00);

 