DROP DATABASE IF EXISTS MOAPP;
CREATE DATABASE MOAPP DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE MOAPP;

DROP TABLE IF EXISTS mDEPARTMENT;
CREATE TABLE mDEPARTMENT(
	Dnumber INT auto_increment,
    Dname VARCHAR(20) NOT NULL,
    PRIMARY KEY(Dnumber),
    UNIQUE(Dname)
);

DROP TABLE IF EXISTS mUSER;
CREATE TABLE mUSER(
	Id VARCHAR(20),
	Pw VARCHAR(15) NOT NULL,
	Sid CHAR(10) NOT NULL,
	Name VARCHAR(5) NOT NULL,
	Nick VARCHAR(8) NOT NULL,
    Dno INT,
	PRIMARY KEY(ID),
    UNIQUE(Sid), UNIQUE(Nick),
	FOREIGN KEY(Dno) REFERENCES mDEPARTMENT(Dnumber) ON UPDATE CASCADE ON DELETE SET NULL
);

DROP TABLE IF EXISTS mCATEGORY;
CREATE TABLE mCATEGORY(
    Cnumber INT auto_increment,
    Cname VARCHAR(10) NOT NULL,
    PRIMARY KEY(Cnumber),
    UNIQUE(Cname)
);

DROP TABLE IF EXISTS mQUESTION;
CREATE TABLE mQUESTION(
    Qnumber INT auto_increment,
    Title VARCHAR(30) NOT NULL,
    Content VARCHAR(2000) NOT NULL,
    Cno INT,
    Qtime DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    Rating DECIMAL(2,1) DEFAULT 0,
    Good INT DEFAULT 0,
    PRIMARY KEY(Qnumber),
    FOREIGN KEY(Cno) REFERENCES mCATEGORY(Cnumber) ON UPDATE CASCADE ON DELETE SET NULL
);

DROP TABLE IF EXISTS mCOMMENT;
CREATE TABLE mCOMMENT(
    Cnumber INT auto_increment,
    Content VARCHAR(500) NOT NULL,
    Ctime DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    Rating DECIMAL(2,1) DEFAULT 0,
    Good INT DEFAULT 0,
    PRIMARY KEY(Cnumber)
);

DROP TABLE IF EXISTS mFAVOR;
CREATE TABLE mFAVOR(
	Uid VARCHAR(20),
	Cno INT,
    PRIMARY KEY(Uid, Cno),
    FOREIGN KEY(Uid) REFERENCES mUSER(Id) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY(Cno) REFERENCES mCATEGORY(Cnumber) ON UPDATE CASCADE ON DELETE CASCADE
);

DROP TABLE IF EXISTS mASK;
CREATE TABLE mASK(
	Uid VARCHAR(20),
	Qno INT,
    PRIMARY KEY(Uid, Qno),
    FOREIGN KEY(Uid) REFERENCES mUSER(Id) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY(Qno) REFERENCES mQUESTION(Qnumber) ON UPDATE CASCADE ON DELETE CASCADE
);

DROP TABLE IF EXISTS mANSWER;
CREATE TABLE mANSWER(
	Uid VARCHAR(20),
	Cno INT,
    PRIMARY KEY(Uid, Cno),
    FOREIGN KEY(Uid) REFERENCES mUSER(Id) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY(Cno) REFERENCES mCOMMENT(Cnumber) ON UPDATE CASCADE ON DELETE CASCADE
);

DROP TABLE IF EXISTS mREPLY;
CREATE TABLE mREPLY(
	Qno INT,
	Cno INT,
    PRIMARY KEY(Qno, Cno),
    FOREIGN KEY(Qno) REFERENCES mQUESTION(Qnumber) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY(Cno) REFERENCES mCOMMENT(Cnumber) ON UPDATE CASCADE ON DELETE CASCADE
);




DELIMITER //
DROP TRIGGER IF EXISTS ins_users//
CREATE TRIGGER ins_users BEFORE INSERT ON mUSER FOR EACH ROW
BEGIN
	SET NEW.Id = IF(length(NEW.Id)>=4, NEW.Id, null);
	SET NEW.Pw = IF(length(NEW.Pw)>=4, NEW.Pw, null);
    SET NEW.Sid = IF(length(NEW.Sid)=10, NEW.Sid, null);
    SET NEW.Name = IF(length(NEW.Name)>=2, NEW.Name, null);
    SET NEW.Nick = IF(length(NEW.Nick)>=2, NEW.Nick, null);
END//
DROP TRIGGER IF EXISTS upd_users//
CREATE TRIGGER upd_users BEFORE UPDATE ON mUSER FOR EACH ROW
BEGIN
	SET NEW.Id = IF(length(NEW.Id)>=4, NEW.Id, OLD.Id);
	SET NEW.Pw = IF(length(NEW.Pw)>=4, NEW.Pw, OLD.Pw);
    SET NEW.Sid = IF(length(NEW.Sid)=10, OLD.Sid, OLD.Sid);
    SET NEW.Name = IF(length(NEW.Name)>=2, NEW.Name, OLD.Name);
    SET NEW.Nick = IF(length(NEW.Nick)>=2, NEW.Nick, OLD.Nick);
END//

DROP TRIGGER IF EXISTS ins_category//
CREATE TRIGGER ins_category BEFORE INSERT ON mCATEGORY FOR EACH ROW
BEGIN
	SET NEW.Cname = IF(length(NEW.Cname)>=1, NEW.Cname, null);
END//
DROP TRIGGER IF EXISTS upd_category//
CREATE TRIGGER upd_category BEFORE UPDATE ON mCATEGORY FOR EACH ROW
BEGIN
	SET NEW.Cname = IF(length(NEW.Cname)>=1, NEW.Cname, null);
END//

DROP TRIGGER IF EXISTS ins_question//
CREATE TRIGGER ins_question BEFORE INSERT ON mQUESTION FOR EACH ROW
BEGIN
	SET NEW.Title = IF(length(NEW.Title)>=5, NEW.Title, null);
    SET NEW.Content = IF(length(NEW.Content)>=1, NEW.Content, null);
    SET NEW.Rating = IF(NEW.Rating>10, 10, NEW.Rating);
    SET NEW.Rating = IF(NEW.Rating<0, 0, NEW.Rating);
    SET NEW.Good = IF(NEW.Good<0, 0, NEW.Good);
END//
DROP TRIGGER IF EXISTS upd_question//
CREATE TRIGGER upd_question BEFORE UPDATE ON mQUESTION FOR EACH ROW
BEGIN
	SET NEW.Title = IF(length(NEW.Title)>=5, NEW.Title, OLD.Title);
    SET NEW.Content = IF(length(NEW.Content)>=1, NEW.Content, OLD.Content);
    SET NEW.Qtime = IF(NEW.Qtime = OLD.Qtime, NEW.Qtime, OLD.Qtime);
    SET NEW.Rating = IF(NEW.Rating>10, 10, NEW.Rating);
    SET NEW.Rating = IF(NEW.Rating<0, OLD.Rating, NEW.Rating);
    SET NEW.Good = IF(NEW.Good<0, OLD.Good, NEW.Good);
END//

DROP TRIGGER IF EXISTS ins_comment//
CREATE TRIGGER ins_comment BEFORE INSERT ON mCOMMENT FOR EACH ROW
BEGIN
    SET NEW.Content = IF(length(NEW.Content)>=1, NEW.Content, null);
    SET NEW.Rating = IF(NEW.Rating>10, 10, NEW.Rating);
    SET NEW.Rating = IF(NEW.Rating<0, 0, NEW.Rating);
    SET NEW.Good = IF(NEW.Good<0, 0, NEW.Good);
END//
DROP TRIGGER IF EXISTS upd_comment//
CREATE TRIGGER upd_comment BEFORE UPDATE ON mCOMMENT FOR EACH ROW
BEGIN
    SET NEW.Content = IF(length(NEW.Content)>=1, NEW.Content, OLD.Content);
    SET NEW.Ctime = IF(NEW.Ctime = OLD.Ctime, NEW.Ctime, OLD.Ctime);
    SET NEW.Rating = IF(NEW.Rating>10, 10, NEW.Rating);
    SET NEW.Rating = IF(NEW.Rating<0, OLD.Rating, NEW.Rating);
    SET NEW.Good = IF(NEW.Good<0, OLD.Good, NEW.Good);
END//
DELIMITER ;



DROP VIEW IF EXISTS Favor_Qlist;
CREATE VIEW Favor_Qlist AS
	SELECT U.id, C.Uid, Qnumber, Title, Good, Cname, Qtime FROM (SELECT Id, Cno FROM mUSER JOIN mFAVOR ON Id=Uid) U, (SELECT Uid, Qnumber, Title, Good, Cno, Cname, Qtime FROM mCATEGORY JOIN (SELECT Uid, Qnumber, Title, Good, Cno, Qtime FROM mQUESTION JOIN mASK ON Qno=Qnumber) Q ON Cno=Cnumber) C WHERE U.Cno=C.Cno;
SELECT Uid, Qnumber, Title, Good, Cname, Qtime FROM Favor_Qlist WHERE Id='test1';

DROP VIEW IF EXISTS NEW_Qlist;
CREATE VIEW NEW_Qlist AS
	SELECT Uid, Qnumber, Title, Good, Cname, Qtime FROM (SELECT Uid, Qnumber, Title, Good, Cno, Qtime FROM mQUESTION JOIN mASK ON Qno=Qnumber ORDER BY Qtime DESC) Q JOIN mCATEGORY ON Cno=Cnumber;
SELECT * FROM NEW_Qlist;

DROP VIEW IF EXISTS HOT_Qlist;
CREATE VIEW HOT_Qlist AS
	SELECT Uid, Qnumber, Title, Good, Cname, Cnt, Qtime FROM mCATEGORY JOIN (SELECT Uid, Qnumber, Title, Cno, Good, Cnt, Qtime FROM mASK JOIN (SELECT Qnumber, Title, Cno, Good, Cnt, Qtime FROM mQUESTION JOIN (SELECT Qno, COUNT(*) AS Cnt FROM mREPLY GROUP BY Qno ORDER BY COUNT(*) DESC) R ON Qnumber=Qno ORDER BY R.cnt DESC) Q ON Qno=Qnumber) A ON Cno=Cnumber ORDER BY Cnt DESC;
SELECT * FROM HOT_Qlist;




SELECT * FROM mUSER;
INSERT INTO mUSER(Id, Pw, Sid, Name, Nick) VALUES ('admin', '1234', '0000000000', '관리자', '관리자');
INSERT INTO mUSER(Id, Pw, Sid, Name, Nick) VALUES ('jwjw', '1111', '2013105046', '박재운', '박재운');
INSERT INTO mUSER(Id, Pw, Sid, Name, Nick) VALUES ('bkbk', '2222', '2016110679', '정보경', '정보경');
INSERT INTO mUSER(Id, Pw, Sid, Name, Nick) VALUES ('sisi', '3333', '2013105006', '황선익', '황선익');
INSERT INTO mUSER(Id, Pw, Sid, Name, Nick) VALUES ('grgr', '4444', '2013097088', '김규래', '김규래');
INSERT INTO mUSER(Id, Pw, Sid, Name, Nick) VALUES ('test1', '0000', '1231231231', 'test1', '테스트1');
INSERT INTO mUSER(Id, Pw, Sid, Name, Nick) VALUES ('test2', '0000', '2342342342', 'test2', '테스트2');

SELECT * FROM mCATEGORY;
INSERT INTO mCATEGORY(Cname) VALUES ('알고리즘');
INSERT INTO mCATEGORY(Cname) VALUES ('자료구조');
INSERT INTO mCATEGORY(Cname) VALUES ('네트워크');
INSERT INTO mCATEGORY(Cname) VALUES ('컴퓨터구조');
INSERT INTO mCATEGORY(Cname) VALUES ('자바');
INSERT INTO mCATEGORY(Cname) VALUES ('운영체제');
INSERT INTO mCATEGORY(Cname) VALUES ('시스템');
INSERT INTO mCATEGORY(Cname) VALUES ('데이터베이스');
INSERT INTO mCATEGORY(Cname) VALUES ('인공지능');
INSERT INTO mCATEGORY(Cname) VALUES ('빅데이터');
INSERT INTO mCATEGORY(Cname) VALUES ('웹');
INSERT INTO mCATEGORY(Cname) VALUES ('보안');
INSERT INTO mCATEGORY(Cname) VALUES ('로봇');
INSERT INTO mCATEGORY(Cname) VALUES ('소프트웨어설계');
INSERT INTO mCATEGORY(Cname) VALUES ('미디어아트');
INSERT INTO mCATEGORY(Cname) VALUES ('병렬컴퓨팅');

SELECT * FROM mQUESTION;
INSERT INTO mQUESTION(Title, Cno, Content) VALUES ('test1', 5, '1');
INSERT INTO mQUESTION(Title, Cno, Content) VALUES ('test2', 2, 'asdasdasfafasfaseqwzxvnadlfdsjfkljqporiqwoepqw');
INSERT INTO mQUESTION(Title, Cno, Content) VALUES ('test3', 4, '가나다라마바사아자ㅏ차카타파');
INSERT INTO mQUESTION(Title, Cno, Content) VALUES ('test4', 10, 'asdasfasfasafasfasfasf팜나람나갑작ㅂ잗ㅂㅈ');
INSERT INTO mQUESTION(Title, Cno, Content) VALUES ('test5', 1, 'ㅠㅠ');

SELECT * FROM mCOMMENT;
INSERT INTO mCOMMENT(Content) VALUES ('test');
INSERT INTO mCOMMENT(Content) VALUES ('test');
INSERT INTO mCOMMENT(Content) VALUES ('test');
INSERT INTO mCOMMENT(Content) VALUES ('test');
INSERT INTO mCOMMENT(Content) VALUES ('test');
INSERT INTO mCOMMENT(Content) VALUES ('test');
INSERT INTO mCOMMENT(Content) VALUES ('test');


SELECT * FROM mFAVOR;
INSERT INTO mFAVOR(Uid, Cno) VALUES ('test1', 2);
INSERT INTO mFAVOR(Uid, Cno) VALUES ('test1', 3);
INSERT INTO mFAVOR(Uid, Cno) VALUES ('test1', 4);
INSERT INTO mFAVOR(Uid, Cno) VALUES ('test1', 5);
INSERT INTO mFAVOR(Uid, Cno) VALUES ('test2', 10);

SELECT * FROM mASK;
INSERT INTO mASK(Uid, Qno) VALUES ('test1', 1);
INSERT INTO mASK(Uid, Qno) VALUES ('test1', 2);
INSERT INTO mASK(Uid, Qno) VALUES ('test2', 3);
INSERT INTO mASK(Uid, Qno) VALUES ('test1', 4);
INSERT INTO mASK(Uid, Qno) VALUES ('test1', 5);

SELECT * FROM mREPLY;
INSERT INTO mREPLY(Qno, Cno) VALUES (1, 1);
INSERT INTO mREPLY(Qno, Cno) VALUES (1, 2);
INSERT INTO mREPLY(Qno, Cno) VALUES (1, 3);
INSERT INTO mREPLY(Qno, Cno) VALUES (1, 4);
INSERT INTO mREPLY(Qno, Cno) VALUES (2, 5);
INSERT INTO mREPLY(Qno, Cno) VALUES (2, 6);

