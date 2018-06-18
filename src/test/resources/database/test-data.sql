INSERT INTO account VALUES (1,'MichaelJackson','$2a$10$NFwfvG1w3bJrUmr3OLbRHuIFeSv88zLJlFhZBGctxrv5TSUASaXLC','Michael','Jackson','MichaelJackson@test.com','2011111111',1,'2018-06-10 08:03:01','2018-06-10 10:13:35'),(2,'TaylorSwift','$2a$10$NFwfvG1w3bJrUmr3OLbRHuIFeSv88zLJlFhZBGctxrv5TSUASaXLC','Taylor','Swift','TaylorSwift@test.com','2011111112',1,'2018-06-10 08:03:01','2018-06-10 10:13:35'),(3,'SarahBrightman','$2a$10$p0J/yhl6wmBhgVRW2ES94.Zo3s7r/RLjfwkvP86EyVXHcrEmkBCom','Sarah','Brightman','SarahBrightman@test.com','2011111113',1,'2018-06-10 08:22:24','2018-06-10 10:13:35');

INSERT INTO account_role VALUES (1,1),(2,2),(3,3);

INSERT INTO account_status VALUES (1,'in.use','currently in use'),(2,'suspended','suspended'),(3,'terminated','terminated and not available any more');

INSERT INTO authority VALUES (1,'read','read access'),(2,'write','write access'),(3,'readwrite','read and write access');

INSERT INTO role VALUES (1,'user','registered users'),(2,'admin','administrators'),(3,'operator','operators');

INSERT INTO role_authority VALUES (1,1),(2,3),(3,2);