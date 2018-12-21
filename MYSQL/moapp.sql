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
	SELECT Qnumber, Id, Uid, Title, Content, Ccnt, Good, Cname, Qtime FROM (SELECT Qno, COUNT(*) AS Ccnt FROM mREPLY GROUP BY Qno) R RIGHT JOIN (SELECT U.id, C.Uid, Qnumber, Content, Title, Good, Cname, Qtime FROM (SELECT Id, Cno FROM mUSER JOIN mFAVOR ON Id=Uid) U, (SELECT Uid, Qnumber, Title, Good, Cno, Cname, Qtime, Content FROM mCATEGORY JOIN (SELECT Uid, Qnumber, Title, Good, Cno, Content, Qtime FROM mQUESTION JOIN mASK ON Qno=Qnumber) Q ON Cno=Cnumber) C WHERE U.Cno=C.Cno) Q ON Qno=Qnumber;
SELECT * FROM Favor_Qlist WHERE Id='test1';

DROP VIEW IF EXISTS NEW_Qlist;
CREATE VIEW NEW_Qlist AS
	SELECT Qnumber, Uid, Title, Content, Ccnt, Good, Cname, Qtime FROM (SELECT Qno, COUNT(*) AS Ccnt FROM mREPLY GROUP BY Qno) R RIGHT JOIN ((SELECT Uid, Qnumber, Title, Content, Good, Cno, Qtime FROM mQUESTION JOIN mASK ON Qno=Qnumber ORDER BY Qtime DESC) Q JOIN mCATEGORY ON Cno=Cnumber) ON Qnumber=Qno;
SELECT * FROM NEW_Qlist;

DROP VIEW IF EXISTS HOT_Qlist;
CREATE VIEW HOT_Qlist AS
	SELECT Qnumber, Uid, Title, Content, Ccnt, Good, Cname, Qtime FROM (SELECT Qno, COUNT(*) AS Ccnt FROM mREPLY GROUP BY Qno) R RIGHT JOIN (SELECT Uid, Qnumber, Title, Content, Good, Cname, Cnt, Qtime FROM mCATEGORY RIGHT JOIN (SELECT Uid, Qnumber, Title, Content, Cno, Good, Cnt, Qtime FROM mASK JOIN (SELECT Qnumber, Title, Content, Cno, Good, Cnt, Qtime FROM mQUESTION LEFT JOIN (SELECT Qno, COUNT(*) AS Cnt FROM mREPLY GROUP BY Qno ORDER BY COUNT(*) DESC) R ON Qnumber=Qno ORDER BY R.cnt DESC) Q ON Qno=Qnumber) A ON Cno=Cnumber ORDER BY Cnt DESC) Q ON Qno=Qnumber;
SELECT * FROM HOT_Qlist;

DROP VIEW IF EXISTS Qlist;
CREATE VIEW Qlist AS
	SELECT Uid, Title, Content, Cname, Qtime, Ccnt, Rating, Good FROM (SELECT Qno, COUNT(*) AS Ccnt FROM mREPLY GROUP BY Qno) R RIGHT JOIN (SELECT Qno, Uid, Title, Content, Cname, Qtime, Rating, Good FROM mCATEGORY JOIN (SELECT Qno, Uid, Title, Content, Cno, Qtime, Rating, Good FROM mQUESTION JOIN mASK ON Qnumber=Qno) Q ON Cnumber=Cno) Q On Q.Qno=R.Qno;
SELECT * FROM Qlist;

DROP VIEW IF EXISTS Clist;
CREATE VIEW Clist AS
	SELECT Qno, C.Cno, Uid, Content, Ctime, Rating, Good FROM mREPLY R JOIN (SELECT Cno, Uid, Content, Ctime, Rating, Good FROM mCOMMENT JOIN mANSWER ON Cno=Cnumber) C ON C.Cno = R.Cno;
SELECT * FROM Clist;

DROP VIEW IF EXISTS Mypage;
CREATE VIEW Mypage AS
	SELECT Id, Name, Nick, Dname, Qcnt, COUNT(Cno) AS Ccnt FROM mANSWER RIGHT JOIN (SELECT Id, Name, Nick, Dname, COUNT(Qno) AS Qcnt FROM (SELECT * FROM mUSER LEFT JOIN mDEPARTMENT ON Dno=Dnumber) U LEFT JOIN mASK ON Id=Uid GROUP BY Id) A ON Uid=Id GROUP BY Id;
SELECT * FROM Mypage;




SELECT * FROM mDEPARTMENT;
INSERT INTO mDEPARTMENT(Dname) VALUES('컴학');
INSERT INTO mDEPARTMENT(Dname) VALUES('전자');
INSERT INTO mDEPARTMENT(Dname) VALUES('모바일공학');
INSERT INTO mDEPARTMENT(Dname) VALUES('기계');
INSERT INTO mDEPARTMENT(Dname) VALUES('전기');

SELECT * FROM mUSER;
INSERT INTO mUSER(Id, Pw, Sid, Name, Nick) VALUES ('admin', '1234', '0000000000', '관리자', '관리자');
INSERT INTO mUSER(Id, Pw, Sid, Name, Nick) VALUES ('jwjw', '1111', '2013105046', '박재운', '박재운');
INSERT INTO mUSER(Id, Pw, Sid, Name, Nick) VALUES ('bkbk', '2222', '2016110679', '정보경', '정보경');
INSERT INTO mUSER(Id, Pw, Sid, Name, Nick) VALUES ('sisi', '3333', '2013105006', '황선익', '황선익');
INSERT INTO mUSER(Id, Pw, Sid, Name, Nick) VALUES ('grgr', '4444', '2013097088', '김규래', '김규래');
INSERT INTO mUSER(Id, Pw, Sid, Name, Nick, Dno) VALUES ('test1', '1234', '1111111111', 'test1', 'tt1', 1);
INSERT INTO mUSER(Id, Pw, Sid, Name, Nick, Dno) VALUES ('test2', '1234', '2222222222', 'test2', 'tt2', 1);

INSERT INTO mUSER (Id, Pw, Name, Sid, Nick, Dno) VALUES ('WP5Zrst', 'RcoIi$1', '전지유', '2011534661', 'test0', 1);
INSERT INTO mUSER (Id, Pw, Name, Sid, Nick, Dno) VALUES ('dVT9V', ')5x%UE6', '권소아', '2018233684', 'test1', 1);
INSERT INTO mUSER (Id, Pw, Name, Sid, Nick, Dno) VALUES ('24D185', 'blRi/Lh', '정재찬', '2013493139', 'test2', 1);
INSERT INTO mUSER (Id, Pw, Name, Sid, Nick, Dno) VALUES ('40016t', '4Xr3.V', '류지호', '2011953880', 'test3', 1);
INSERT INTO mUSER (Id, Pw, Name, Sid, Nick, Dno) VALUES ('Xbe9HH', '4j.sf5u-7', '한하윤', '2016782813', 'test4', 1);
INSERT INTO mUSER (Id, Pw, Name, Sid, Nick, Dno) VALUES ('57b6LpP', '!Z,WUD', '권하윤', '2014391934', 'test5', 2);
INSERT INTO mUSER (Id, Pw, Name, Sid, Nick, Dno) VALUES ('0tB6La', 'pE&Smh', '전서연', '2016247324', 'test6', 3);
INSERT INTO mUSER (Id, Pw, Name, Sid, Nick, Dno) VALUES ('N5UEXE', 'Il+%kD0!', '송하은', '2011632863', 'test7', 4);
INSERT INTO mUSER (Id, Pw, Name, Sid, Nick, Dno) VALUES ('7mIf43', '4IE64po', '백지민', '2013287282', 'test8', 5);
INSERT INTO mUSER (Id, Pw, Name, Sid, Nick, Dno) VALUES ('44w76', 'a92(&4', '전소아', '2012693117', 'test9', 3);
INSERT INTO mUSER (Id, Pw, Name, Sid, Nick, Dno) VALUES ('l3EFx92', 'G+#&3VB', '조소예', '2013632342', 'test10', 2);
INSERT INTO mUSER (Id, Pw, Name, Sid, Nick, Dno) VALUES ('Qo64XGh', '3EWYU(W', '안재현', '2013188054', 'test11', 1);
INSERT INTO mUSER (Id, Pw, Name, Sid, Nick, Dno) VALUES ('4Y0Z7tE', 'D&9p98#', '이나래', '2010207148', 'test12', 3);
INSERT INTO mUSER (Id, Pw, Name, Sid, Nick, Dno) VALUES ('bjCK5e', '5t&F4$j', '안은서', '2018910380', 'test13', 4);
INSERT INTO mUSER (Id, Pw, Name, Sid, Nick, Dno) VALUES ('E672W9', '0(e9%2', '정채원', '2015280796', 'test14', 5);
INSERT INTO mUSER (Id, Pw, Name, Sid, Nick, Dno) VALUES ('01LOScd', 'zY(68+t', '정다온', '2017624254', 'test15', 3);
INSERT INTO mUSER (Id, Pw, Name, Sid, Nick, Dno) VALUES ('86I4uw', 'T-iB,3Z', '문은서', '2014756349', 'test16', 2);
INSERT INTO mUSER (Id, Pw, Name, Sid, Nick, Dno) VALUES ('3w9S0', 'G962Y,8', '문하람', '2012423786', 'test17', 1);
INSERT INTO mUSER (Id, Pw, Name, Sid, Nick, Dno) VALUES ('uGj566', 'o2&3/z$g', '장하린', '2015793862', 'test18', 3);
INSERT INTO mUSER (Id, Pw, Name, Sid, Nick, Dno) VALUES ('2060Hx8', '/(4(+j-R', '황민지', '2018570661', 'test19', 4);

SELECT * FROM mCATEGORY ORDER BY Cnumber;
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
INSERT INTO mCATEGORY(Cname) VALUES ('모바일앱프로그래밍');

SELECT * FROM mQUESTION;
INSERT INTO mQUESTION(Title, Cno, Content) VALUES ('test1', 5, '1');
INSERT INTO mQUESTION(Title, Cno, Content) VALUES ('test2', 2, 'asdasdasfafasfaseqwzxvnadlfdsjfkljqporiqwoepqw');
INSERT INTO mQUESTION(Title, Cno, Content) VALUES ('test3', 4, '가나다라마바사아자ㅏ차카타파');
INSERT INTO mQUESTION(Title, Cno, Content) VALUES ('test4', 10, 'asdasfasfasafasfasfasf팜나람나갑작ㅂ잗ㅂㅈ');
INSERT INTO mQUESTION(Title, Cno, Content) VALUES ('test5', 1, 'ㅠㅠ');

INSERT INTO mQUESTION(Title, Cno, Content, Qtime, Good) VALUES ('xIYX77', 1, '강원도 군포시 덕양구', '2019-08-10', 145);
INSERT INTO mQUESTION(Title, Cno, Content, Qtime, Good) VALUES ('QC35L2L', 6, '충청남도 창원시 기흥구', '2013-06-11', 671);
INSERT INTO mQUESTION(Title, Cno, Content, Qtime, Good) VALUES ('KK5Nu23', 3, '광주광역시 영천시 영도구', '2014-01-12', 99);
INSERT INTO mQUESTION(Title, Cno, Content, Qtime, Good) VALUES ('FEujOK', 12, '대전광역시 수원시 영통구', '2018-03-12', 73);
INSERT INTO mQUESTION(Title, Cno, Content, Qtime, Good) VALUES ('TAenF0', 1, '서울특별시 창원시 남구', '2011-10-24', 297);
INSERT INTO mQUESTION(Title, Cno, Content, Qtime, Good) VALUES ('5490Gu', 5, '광주광역시 군포시 금정구', '2012-08-04', 631);
INSERT INTO mQUESTION(Title, Cno, Content, Qtime, Good) VALUES ('9Eo84G3', 15, '전라북도 목포시 남구', '2010-07-22', 454);
INSERT INTO mQUESTION(Title, Cno, Content, Qtime, Good) VALUES ('5St9Vd1', 5, '경상북도 양산시 상록구', '2017-02-09', 712);
INSERT INTO mQUESTION(Title, Cno, Content, Qtime, Good) VALUES ('L2ORB', 9, '경상남도 천안시 마산합포구', '2011-06-16', 76);
INSERT INTO mQUESTION(Title, Cno, Content, Qtime, Good) VALUES ('1m82Vr', 9, '전라북도 화성시 동안구', '2018-01-22', 734);
INSERT INTO mQUESTION(Title, Cno, Content, Qtime, Good) VALUES ('60Ld32', 17, '충청북도 수원시 단원구', '2010-02-19', 330);
INSERT INTO mQUESTION(Title, Cno, Content, Qtime, Good) VALUES ('ZSdL7', 14, '서울특별시 수원시 마산합포구', '2015-05-11', 208);
INSERT INTO mQUESTION(Title, Cno, Content, Qtime, Good) VALUES ('b71VTB2', 17, '부산광역시 목포시 소사구', '2015-01-07', 41);
INSERT INTO mQUESTION(Title, Cno, Content, Qtime, Good) VALUES ('2nrnDO', 9, '부산광역시 원주시 덕양구', '2019-04-27', 492);
INSERT INTO mQUESTION(Title, Cno, Content, Qtime, Good) VALUES ('0g0Rp', 2, '경기도 김포시 마산합포구', '2016-01-23', 736);
INSERT INTO mQUESTION(Title, Cno, Content, Qtime, Good) VALUES ('GK1hS7P', 14, '전라북도 화성시 중구', '2016-05-12', 906);
INSERT INTO mQUESTION(Title, Cno, Content, Qtime, Good) VALUES ('M6YhIie', 7, '부산광역시 이천시 서구', '2019-12-13', 225);
INSERT INTO mQUESTION(Title, Cno, Content, Qtime, Good) VALUES ('KHcpf83', 9, '경상북도 춘천시 단원구', '2011-03-07', 291);
INSERT INTO mQUESTION(Title, Cno, Content, Qtime, Good) VALUES ('69v2IL', 11, '대구광역시 김포시 진해구', '2014-03-05', 703);
INSERT INTO mQUESTION(Title, Cno, Content, Qtime, Good) VALUES ('3g0qq', 8, '인천광역시 제천시 중원구', '2013-06-24', 831);
INSERT INTO mQUESTION(Title, Cno, Content, Qtime, Good) VALUES ('GHPJa', 10, '전라남도 안동시 강서구', '2016-05-17', 940);
INSERT INTO mQUESTION(Title, Cno, Content, Qtime, Good) VALUES ('5smiLO8', 6, '충청남도 경산시 마산합포구', '2013-02-18', 246);
INSERT INTO mQUESTION(Title, Cno, Content, Qtime, Good) VALUES ('Ss6sIfL', 11, '부산광역시 김포시 소사구', '2017-07-20', 590);
INSERT INTO mQUESTION(Title, Cno, Content, Qtime, Good) VALUES ('w59P60', 5, '전라남도 성남시 북구', '2012-08-20', 533);
INSERT INTO mQUESTION(Title, Cno, Content, Qtime, Good) VALUES ('y8dl85', 6, '강원도 공주시 영도구', '2019-01-22', 812);
INSERT INTO mQUESTION(Title, Cno, Content, Qtime, Good) VALUES ('c8M1VP', 7, '전라남도 여수시 북구', '2014-01-26', 939);
INSERT INTO mQUESTION(Title, Cno, Content, Qtime, Good) VALUES ('DMy98l', 5, '충청북도 속초시 강서구', '2011-09-22', 193);
INSERT INTO mQUESTION(Title, Cno, Content, Qtime, Good) VALUES ('Sn659', 10, '제주시 창원시 동구', '2017-08-10', 999);
INSERT INTO mQUESTION(Title, Cno, Content, Qtime, Good) VALUES ('gM68W', 3, '충청남도 과천시 북구', '2015-08-07', 482);
INSERT INTO mQUESTION(Title, Cno, Content, Qtime, Good) VALUES ('7Ps2X1', 1, '부산광역시 거제시 흥덕구', '2015-10-05', 67);




SELECT * FROM mCOMMENT;
INSERT INTO mCOMMENT(Content) VALUES ('test');
INSERT INTO mCOMMENT(Content) VALUES ('test');
INSERT INTO mCOMMENT(Content) VALUES ('test');
INSERT INTO mCOMMENT(Content) VALUES ('test');
INSERT INTO mCOMMENT(Content) VALUES ('test');
INSERT INTO mCOMMENT(Content) VALUES ('test');
INSERT INTO mCOMMENT(Content) VALUES ('test');

INSERT INTO mCOMMENT(Content, Ctime, Good) VALUES ('자이아파트 202동 203호', '2011-04-07', 370);
INSERT INTO mCOMMENT(Content, Ctime, Good) VALUES ('드림뷰아파트 101동 104호', '2019-11-22', 206);
INSERT INTO mCOMMENT(Content, Ctime, Good) VALUES ('뜨란채아파트 103동 104호', '2017-02-01', 617);
INSERT INTO mCOMMENT(Content, Ctime, Good) VALUES ('롯데캐슬아파트 201동 204호', '2016-10-26', 345);
INSERT INTO mCOMMENT(Content, Ctime, Good) VALUES ('두산위브아파트 103동 104호', '2010-03-14', 491);
INSERT INTO mCOMMENT(Content, Ctime, Good) VALUES ('그랜드파크아파트 302동 402호', '2013-03-16', 265);
INSERT INTO mCOMMENT(Content, Ctime, Good) VALUES ('e-편한세상아파트 202동 403호', '2010-09-16', 112);
INSERT INTO mCOMMENT(Content, Ctime, Good) VALUES ('로얄팰리스아파트 301동 404호', '2015-04-22', 508);
INSERT INTO mCOMMENT(Content, Ctime, Good) VALUES ('뜨란채아파트 201동 404호', '2017-03-13', 727);
INSERT INTO mCOMMENT(Content, Ctime, Good) VALUES ('그랜드파크아파트 201동 303호', '2010-08-22', 356);
INSERT INTO mCOMMENT(Content, Ctime, Good) VALUES ('청구타운아파트 303동 103호', '2012-12-04', 622);
INSERT INTO mCOMMENT(Content, Ctime, Good) VALUES ('삼정그린코아아파트 301동 401호', '2016-04-16', 662);
INSERT INTO mCOMMENT(Content, Ctime, Good) VALUES ('삼환나우빌아파트 103동 103호', '2016-12-15', 935);
INSERT INTO mCOMMENT(Content, Ctime, Good) VALUES ('그랜드파크아파트 303동 303호', '2010-03-14', 936);
INSERT INTO mCOMMENT(Content, Ctime, Good) VALUES ('뜨란채아파트 202동 204호', '2014-01-07', 129);
INSERT INTO mCOMMENT(Content, Ctime, Good) VALUES ('로얄팰리스아파트 302동 104호', '2014-12-24', 775);
INSERT INTO mCOMMENT(Content, Ctime, Good) VALUES ('푸르지오아파트 301동 404호', '2011-10-13', 378);
INSERT INTO mCOMMENT(Content, Ctime, Good) VALUES ('드림뷰아파트 102동 403호', '2015-02-17', 106);
INSERT INTO mCOMMENT(Content, Ctime, Good) VALUES ('삼성아파트 203동 302호', '2012-03-24', 254);
INSERT INTO mCOMMENT(Content, Ctime, Good) VALUES ('그랜드파크아파트 103동 201호', '2012-08-27', 98);
INSERT INTO mCOMMENT(Content, Ctime, Good) VALUES ('휴먼시아아파트 202동 404호', '2013-03-11', 916);
INSERT INTO mCOMMENT(Content, Ctime, Good) VALUES ('그랜드파크아파트 302동 404호', '2011-06-14', 557);
INSERT INTO mCOMMENT(Content, Ctime, Good) VALUES ('청구타운아파트 101동 401호', '2011-07-03', 539);
INSERT INTO mCOMMENT(Content, Ctime, Good) VALUES ('휴먼시아아파트 103동 302호', '2018-06-17', 388);
INSERT INTO mCOMMENT(Content, Ctime, Good) VALUES ('롯데캐슬아파트 101동 102호', '2016-08-20', 248);
INSERT INTO mCOMMENT(Content, Ctime, Good) VALUES ('삼성아파트 203동 404호', '2012-07-01', 848);
INSERT INTO mCOMMENT(Content, Ctime, Good) VALUES ('아이프라임아파트 201동 303호', '2012-11-27', 497);
INSERT INTO mCOMMENT(Content, Ctime, Good) VALUES ('푸르지오아파트 301동 304호', '2013-07-09', 251);
INSERT INTO mCOMMENT(Content, Ctime, Good) VALUES ('e-편한세상아파트 202동 204호', '2012-07-07', 574);
INSERT INTO mCOMMENT(Content, Ctime, Good) VALUES ('드림뷰아파트 301동 401호', '2014-05-24', 802);
INSERT INTO mCOMMENT(Content, Ctime, Good) VALUES ('푸르지오아파트 303동 401호', '2015-11-14', 347);
INSERT INTO mCOMMENT(Content, Ctime, Good) VALUES ('삼정그린코아아파트 202동 104호', '2014-08-18', 468);
INSERT INTO mCOMMENT(Content, Ctime, Good) VALUES ('로얄팰리스아파트 301동 303호', '2014-02-19', 531);
INSERT INTO mCOMMENT(Content, Ctime, Good) VALUES ('삼환나우빌아파트 201동 202호', '2019-10-03', 32);
INSERT INTO mCOMMENT(Content, Ctime, Good) VALUES ('그랜드파크아파트 301동 302호', '2011-12-03', 927);
INSERT INTO mCOMMENT(Content, Ctime, Good) VALUES ('자이아파트 303동 102호', '2012-06-27', 60);
INSERT INTO mCOMMENT(Content, Ctime, Good) VALUES ('뜨란채아파트 101동 204호', '2015-03-24', 964);
INSERT INTO mCOMMENT(Content, Ctime, Good) VALUES ('삼정그린코아아파트 303동 201호', '2011-10-14', 345);
INSERT INTO mCOMMENT(Content, Ctime, Good) VALUES ('푸르지오아파트 302동 402호', '2016-06-04', 326);
INSERT INTO mCOMMENT(Content, Ctime, Good) VALUES ('삼환나우빌아파트 302동 403호', '2010-08-02', 679);
INSERT INTO mCOMMENT(Content, Ctime, Good) VALUES ('코오롱하늘채아파트 303동 401호', '2017-05-11', 109);
INSERT INTO mCOMMENT(Content, Ctime, Good) VALUES ('e-편한세상아파트 101동 102호', '2012-03-11', 488);
INSERT INTO mCOMMENT(Content, Ctime, Good) VALUES ('뜨란채아파트 103동 201호', '2015-06-09', 283);
INSERT INTO mCOMMENT(Content, Ctime, Good) VALUES ('롯데캐슬아파트 203동 404호', '2019-11-21', 622);
INSERT INTO mCOMMENT(Content, Ctime, Good) VALUES ('코오롱하늘채아파트 101동 402호', '2013-05-03', 516);
INSERT INTO mCOMMENT(Content, Ctime, Good) VALUES ('로얄팰리스아파트 302동 102호', '2018-04-25', 656);
INSERT INTO mCOMMENT(Content, Ctime, Good) VALUES ('삼환나우빌아파트 102동 201호', '2019-05-19', 110);
INSERT INTO mCOMMENT(Content, Ctime, Good) VALUES ('삼환나우빌아파트 202동 203호', '2018-09-03', 610);
INSERT INTO mCOMMENT(Content, Ctime, Good) VALUES ('삼환나우빌아파트 303동 403호', '2014-05-07', 644);
INSERT INTO mCOMMENT(Content, Ctime, Good) VALUES ('뜨란채아파트 301동 203호', '2018-10-18', 30);
INSERT INTO mCOMMENT(Content, Ctime, Good) VALUES ('e-편한세상아파트 301동 201호', '2011-11-14', 148);
INSERT INTO mCOMMENT(Content, Ctime, Good) VALUES ('아이프라임아파트 101동 103호', '2011-08-07', 383);
INSERT INTO mCOMMENT(Content, Ctime, Good) VALUES ('롯데캐슬아파트 203동 104호', '2016-11-17', 800);
INSERT INTO mCOMMENT(Content, Ctime, Good) VALUES ('LH아파트 102동 402호', '2011-12-06', 392);
INSERT INTO mCOMMENT(Content, Ctime, Good) VALUES ('아이프라임아파트 301동 104호', '2011-01-12', 77);
INSERT INTO mCOMMENT(Content, Ctime, Good) VALUES ('휴먼시아아파트 202동 402호', '2019-10-17', 275);
INSERT INTO mCOMMENT(Content, Ctime, Good) VALUES ('삼환나우빌아파트 103동 403호', '2015-02-01', 14);
INSERT INTO mCOMMENT(Content, Ctime, Good) VALUES ('그랜드파크아파트 101동 102호', '2014-03-27', 963);
INSERT INTO mCOMMENT(Content, Ctime, Good) VALUES ('삼환나우빌아파트 201동 103호', '2014-02-01', 320);
INSERT INTO mCOMMENT(Content, Ctime, Good) VALUES ('LH아파트 303동 304호', '2019-01-25', 517);
INSERT INTO mCOMMENT(Content, Ctime, Good) VALUES ('푸르지오아파트 303동 104호', '2019-04-09', 429);
INSERT INTO mCOMMENT(Content, Ctime, Good) VALUES ('두산위브아파트 203동 204호', '2014-09-02', 257);
INSERT INTO mCOMMENT(Content, Ctime, Good) VALUES ('우방타운아파트 202동 103호', '2016-05-03', 262);
INSERT INTO mCOMMENT(Content, Ctime, Good) VALUES ('코오롱하늘채아파트 201동 204호', '2015-12-26', 939);
INSERT INTO mCOMMENT(Content, Ctime, Good) VALUES ('삼정그린코아아파트 203동 301호', '2017-12-10', 332);
INSERT INTO mCOMMENT(Content, Ctime, Good) VALUES ('코오롱하늘채아파트 203동 402호', '2012-08-15', 760);
INSERT INTO mCOMMENT(Content, Ctime, Good) VALUES ('휴먼시아아파트 102동 403호', '2010-08-13', 193);
INSERT INTO mCOMMENT(Content, Ctime, Good) VALUES ('그랜드파크아파트 201동 303호', '2011-06-14', 307);
INSERT INTO mCOMMENT(Content, Ctime, Good) VALUES ('삼환나우빌아파트 203동 401호', '2012-02-18', 736);
INSERT INTO mCOMMENT(Content, Ctime, Good) VALUES ('LH아파트 102동 202호', '2012-04-15', 262);
INSERT INTO mCOMMENT(Content, Ctime, Good) VALUES ('삼성아파트 302동 402호', '2018-01-09', 56);
INSERT INTO mCOMMENT(Content, Ctime, Good) VALUES ('그랜드파크아파트 303동 404호', '2012-12-22', 242);
INSERT INTO mCOMMENT(Content, Ctime, Good) VALUES ('드림뷰아파트 302동 101호', '2012-04-22', 633);
INSERT INTO mCOMMENT(Content, Ctime, Good) VALUES ('그랜드파크아파트 202동 203호', '2017-02-11', 579);
INSERT INTO mCOMMENT(Content, Ctime, Good) VALUES ('e-편한세상아파트 202동 201호', '2017-03-24', 58);
INSERT INTO mCOMMENT(Content, Ctime, Good) VALUES ('코오롱하늘채아파트 302동 201호', '2012-06-15', 859);
INSERT INTO mCOMMENT(Content, Ctime, Good) VALUES ('푸르지오아파트 201동 201호', '2018-11-05', 112);
INSERT INTO mCOMMENT(Content, Ctime, Good) VALUES ('e-편한세상아파트 201동 403호', '2014-12-03', 575);
INSERT INTO mCOMMENT(Content, Ctime, Good) VALUES ('뜨란채아파트 303동 204호', '2015-11-11', 116);
INSERT INTO mCOMMENT(Content, Ctime, Good) VALUES ('청구타운아파트 302동 304호', '2015-11-09', 574);
INSERT INTO mCOMMENT(Content, Ctime, Good) VALUES ('두산위브아파트 101동 404호', '2010-03-14', 329);
INSERT INTO mCOMMENT(Content, Ctime, Good) VALUES ('롯데캐슬아파트 303동 201호', '2015-10-13', 258);
INSERT INTO mCOMMENT(Content, Ctime, Good) VALUES ('삼환나우빌아파트 301동 204호', '2013-08-06', 80);
INSERT INTO mCOMMENT(Content, Ctime, Good) VALUES ('두산위브아파트 302동 403호', '2011-01-11', 338);
INSERT INTO mCOMMENT(Content, Ctime, Good) VALUES ('우방타운아파트 303동 302호', '2012-01-23', 761);
INSERT INTO mCOMMENT(Content, Ctime, Good) VALUES ('코오롱하늘채아파트 303동 202호', '2011-03-22', 698);
INSERT INTO mCOMMENT(Content, Ctime, Good) VALUES ('휴먼시아아파트 303동 104호', '2014-09-18', 379);
INSERT INTO mCOMMENT(Content, Ctime, Good) VALUES ('그랜드파크아파트 103동 404호', '2019-11-20', 853);
INSERT INTO mCOMMENT(Content, Ctime, Good) VALUES ('청구타운아파트 302동 302호', '2016-08-21', 884);
INSERT INTO mCOMMENT(Content, Ctime, Good) VALUES ('e-편한세상아파트 201동 103호', '2016-09-07', 374);
INSERT INTO mCOMMENT(Content, Ctime, Good) VALUES ('그랜드파크아파트 201동 102호', '2012-11-13', 123);
INSERT INTO mCOMMENT(Content, Ctime, Good) VALUES ('그랜드파크아파트 303동 302호', '2018-02-24', 919);
INSERT INTO mCOMMENT(Content, Ctime, Good) VALUES ('두산위브아파트 203동 303호', '2012-07-06', 589);
INSERT INTO mCOMMENT(Content, Ctime, Good) VALUES ('LH아파트 102동 203호', '2012-07-13', 864);
INSERT INTO mCOMMENT(Content, Ctime, Good) VALUES ('드림뷰아파트 301동 202호', '2018-07-03', 483);
INSERT INTO mCOMMENT(Content, Ctime, Good) VALUES ('삼정그린코아아파트 101동 401호', '2011-04-12', 902);
INSERT INTO mCOMMENT(Content, Ctime, Good) VALUES ('자이아파트 103동 303호', '2018-10-17', 522);
INSERT INTO mCOMMENT(Content, Ctime, Good) VALUES ('삼성아파트 101동 103호', '2013-07-11', 321);
INSERT INTO mCOMMENT(Content, Ctime, Good) VALUES ('두산위브아파트 203동 104호', '2010-11-27', 316);
INSERT INTO mCOMMENT(Content, Ctime, Good) VALUES ('LH아파트 203동 301호', '2019-09-06', 643);



SELECT * FROM mFAVOR;
INSERT INTO mFAVOR(Uid, Cno) VALUES ('01LOScd',16);
INSERT INTO mFAVOR(Uid, Cno) VALUES ('0tB6La',1);
INSERT INTO mFAVOR(Uid, Cno) VALUES ('2060Hx8',3);
INSERT INTO mFAVOR(Uid, Cno) VALUES ('24D185',4);
INSERT INTO mFAVOR(Uid, Cno) VALUES ('3w9S0',1);
INSERT INTO mFAVOR(Uid, Cno) VALUES ('40016t',7);
INSERT INTO mFAVOR(Uid, Cno) VALUES ('44w76',15);
INSERT INTO mFAVOR(Uid, Cno) VALUES ('4Y0Z7tE',8);
INSERT INTO mFAVOR(Uid, Cno) VALUES ('57b6LpP',14);
INSERT INTO mFAVOR(Uid, Cno) VALUES ('7mIf43',4);
INSERT INTO mFAVOR(Uid, Cno) VALUES ('86I4uw',10);
INSERT INTO mFAVOR(Uid, Cno) VALUES ('bjCK5e',2);
INSERT INTO mFAVOR(Uid, Cno) VALUES ('bkbk',15);
INSERT INTO mFAVOR(Uid, Cno) VALUES ('dVT9V',1);
INSERT INTO mFAVOR(Uid, Cno) VALUES ('E672W9',16);
INSERT INTO mFAVOR(Uid, Cno) VALUES ('grgr',13);
INSERT INTO mFAVOR(Uid, Cno) VALUES ('jwjw',17);
INSERT INTO mFAVOR(Uid, Cno) VALUES ('l3EFx92',11);
INSERT INTO mFAVOR(Uid, Cno) VALUES ('N5UEXE',13);
INSERT INTO mFAVOR(Uid, Cno) VALUES ('Qo64XGh',5);
INSERT INTO mFAVOR(Uid, Cno) VALUES ('sisi',6);
INSERT INTO mFAVOR(Uid, Cno) VALUES ('test1',1);
INSERT INTO mFAVOR(Uid, Cno) VALUES ('test2',15);
INSERT INTO mFAVOR(Uid, Cno) VALUES ('uGj566',10);
INSERT INTO mFAVOR(Uid, Cno) VALUES ('WP5Zrst',16);
INSERT INTO mFAVOR(Uid, Cno) VALUES ('Xbe9HH',14);

SELECT * FROM mASK;
INSERT INTO mASK(Uid, Qno) VALUES ('test1',1);
INSERT INTO mASK(Uid, Qno) VALUES ('Xbe9HH',2);
INSERT INTO mASK(Uid, Qno) VALUES ('Xbe9HH',3);
INSERT INTO mASK(Uid, Qno) VALUES ('2060Hx8',4);
INSERT INTO mASK(Uid, Qno) VALUES ('Qo64XGh',5);
INSERT INTO mASK(Uid, Qno) VALUES ('57b6LpP',6);
INSERT INTO mASK(Uid, Qno) VALUES ('test2',7);
INSERT INTO mASK(Uid, Qno) VALUES ('Xbe9HH',8);
INSERT INTO mASK(Uid, Qno) VALUES ('24D185',9);
INSERT INTO mASK(Uid, Qno) VALUES ('N5UEXE',10);
INSERT INTO mASK(Uid, Qno) VALUES ('test1',11);
INSERT INTO mASK(Uid, Qno) VALUES ('sisi',12);
INSERT INTO mASK(Uid, Qno) VALUES ('test2',13);
INSERT INTO mASK(Uid, Qno) VALUES ('N5UEXE',14);
INSERT INTO mASK(Uid, Qno) VALUES ('4Y0Z7tE',15);
INSERT INTO mASK(Uid, Qno) VALUES ('Xbe9HH',16);
INSERT INTO mASK(Uid, Qno) VALUES ('44w76',17);
INSERT INTO mASK(Uid, Qno) VALUES ('l3EFx92',18);
INSERT INTO mASK(Uid, Qno) VALUES ('test2',19);
INSERT INTO mASK(Uid, Qno) VALUES ('bkbk',20);
INSERT INTO mASK(Uid, Qno) VALUES ('test2',21);
INSERT INTO mASK(Uid, Qno) VALUES ('E672W9',22);
INSERT INTO mASK(Uid, Qno) VALUES ('44w76',24);
INSERT INTO mASK(Uid, Qno) VALUES ('7mIf43',25);
INSERT INTO mASK(Uid, Qno) VALUES ('Qo64XGh',26);
INSERT INTO mASK(Uid, Qno) VALUES ('bjCK5e',27);
INSERT INTO mASK(Uid, Qno) VALUES ('86I4uw',28);
INSERT INTO mASK(Uid, Qno) VALUES ('44w76',30);
INSERT INTO mASK(Uid, Qno) VALUES ('test2',31);
INSERT INTO mASK(Uid, Qno) VALUES ('test2',32);
INSERT INTO mASK(Uid, Qno) VALUES ('bjCK5e',33);
INSERT INTO mASK(Uid, Qno) VALUES ('WP5Zrst',34);
INSERT INTO mASK(Uid, Qno) VALUES ('WP5Zrst',35);


SELECT * FROM mREPLY;
INSERT INTO mREPLY(Qno, Cno) VALUES (1, 1);
INSERT INTO mREPLY(Qno, Cno) VALUES (1, 2);
INSERT INTO mREPLY(Qno, Cno) VALUES (1, 3);
INSERT INTO mREPLY(Qno, Cno) VALUES (1, 4);
INSERT INTO mREPLY(Qno, Cno) VALUES (2, 5);
INSERT INTO mREPLY(Qno, Cno) VALUES (2, 6);

SELECT * FROM mANSWER;
INSERT INTO mANSWER(Uid, Cno) VALUES ('WP5Zrst',1);
INSERT INTO mANSWER(Uid, Cno) VALUES ('dVT9V',2);
INSERT INTO mANSWER(Uid, Cno) VALUES ('bkbk',3);
INSERT INTO mANSWER(Uid, Cno) VALUES ('40016t',4);
INSERT INTO mANSWER(Uid, Cno) VALUES ('Xbe9HH',5);
INSERT INTO mANSWER(Uid, Cno) VALUES ('dVT9V',6);
INSERT INTO mANSWER(Uid, Cno) VALUES ('grgr',7);
INSERT INTO mANSWER(Uid, Cno) VALUES ('57b6LpP',8);
INSERT INTO mANSWER(Uid, Cno) VALUES ('test1',9);
INSERT INTO mANSWER(Uid, Cno) VALUES ('dVT9V',10);
INSERT INTO mANSWER(Uid, Cno) VALUES ('grgr',11);
INSERT INTO mANSWER(Uid, Cno) VALUES ('jwjw',12);
INSERT INTO mANSWER(Uid, Cno) VALUES ('40016t',13);
INSERT INTO mANSWER(Uid, Cno) VALUES ('test1',14);
INSERT INTO mANSWER(Uid, Cno) VALUES ('bkbk',15);
INSERT INTO mANSWER(Uid, Cno) VALUES ('test2',16);
INSERT INTO mANSWER(Uid, Cno) VALUES ('E672W9',17);
INSERT INTO mANSWER(Uid, Cno) VALUES ('E672W9',18);
INSERT INTO mANSWER(Uid, Cno) VALUES ('40016t',19);
INSERT INTO mANSWER(Uid, Cno) VALUES ('24D185',20);
INSERT INTO mANSWER(Uid, Cno) VALUES ('3w9S0',21);
INSERT INTO mANSWER(Uid, Cno) VALUES ('86I4uw',22);
INSERT INTO mANSWER(Uid, Cno) VALUES ('jwjw',23);
INSERT INTO mANSWER(Uid, Cno) VALUES ('sisi',24);
INSERT INTO mANSWER(Uid, Cno) VALUES ('N5UEXE',25);
INSERT INTO mANSWER(Uid, Cno) VALUES ('uGj566',26);
INSERT INTO mANSWER(Uid, Cno) VALUES ('test1',27);
INSERT INTO mANSWER(Uid, Cno) VALUES ('bkbk',28);
INSERT INTO mANSWER(Uid, Cno) VALUES ('Qo64XGh',29);
INSERT INTO mANSWER(Uid, Cno) VALUES ('dVT9V',30);
INSERT INTO mANSWER(Uid, Cno) VALUES ('Xbe9HH',31);
INSERT INTO mANSWER(Uid, Cno) VALUES ('2060Hx8',32);
INSERT INTO mANSWER(Uid, Cno) VALUES ('40016t',33);
INSERT INTO mANSWER(Uid, Cno) VALUES ('grgr',34);
INSERT INTO mANSWER(Uid, Cno) VALUES ('4Y0Z7tE',35);
INSERT INTO mANSWER(Uid, Cno) VALUES ('jwjw',36);
INSERT INTO mANSWER(Uid, Cno) VALUES ('7mIf43',37);
INSERT INTO mANSWER(Uid, Cno) VALUES ('bjCK5e',38);
INSERT INTO mANSWER(Uid, Cno) VALUES ('0tB6La',39);
INSERT INTO mANSWER(Uid, Cno) VALUES ('dVT9V',40);
INSERT INTO mANSWER(Uid, Cno) VALUES ('E672W9',41);
INSERT INTO mANSWER(Uid, Cno) VALUES ('86I4uw',42);
INSERT INTO mANSWER(Uid, Cno) VALUES ('N5UEXE',43);
INSERT INTO mANSWER(Uid, Cno) VALUES ('3w9S0',44);
INSERT INTO mANSWER(Uid, Cno) VALUES ('grgr',45);
INSERT INTO mANSWER(Uid, Cno) VALUES ('3w9S0',46);
INSERT INTO mANSWER(Uid, Cno) VALUES ('40016t',47);
INSERT INTO mANSWER(Uid, Cno) VALUES ('l3EFx92',48);
INSERT INTO mANSWER(Uid, Cno) VALUES ('4Y0Z7tE',49);
INSERT INTO mANSWER(Uid, Cno) VALUES ('4Y0Z7tE',50);
INSERT INTO mANSWER(Uid, Cno) VALUES ('86I4uw',51);
INSERT INTO mANSWER(Uid, Cno) VALUES ('86I4uw',52);
INSERT INTO mANSWER(Uid, Cno) VALUES ('jwjw',53);
INSERT INTO mANSWER(Uid, Cno) VALUES ('grgr',54);
INSERT INTO mANSWER(Uid, Cno) VALUES ('Qo64XGh',55);
INSERT INTO mANSWER(Uid, Cno) VALUES ('40016t',56);
INSERT INTO mANSWER(Uid, Cno) VALUES ('Qo64XGh',57);
INSERT INTO mANSWER(Uid, Cno) VALUES ('WP5Zrst',58);
INSERT INTO mANSWER(Uid, Cno) VALUES ('uGj566',59);
INSERT INTO mANSWER(Uid, Cno) VALUES ('test1',60);
INSERT INTO mANSWER(Uid, Cno) VALUES ('test2',61);
INSERT INTO mANSWER(Uid, Cno) VALUES ('jwjw',62);
INSERT INTO mANSWER(Uid, Cno) VALUES ('N5UEXE',63);
INSERT INTO mANSWER(Uid, Cno) VALUES ('Qo64XGh',64);
INSERT INTO mANSWER(Uid, Cno) VALUES ('Qo64XGh',65);
INSERT INTO mANSWER(Uid, Cno) VALUES ('0tB6La',66);
INSERT INTO mANSWER(Uid, Cno) VALUES ('4Y0Z7tE',67);
INSERT INTO mANSWER(Uid, Cno) VALUES ('24D185',68);
INSERT INTO mANSWER(Uid, Cno) VALUES ('N5UEXE',69);
INSERT INTO mANSWER(Uid, Cno) VALUES ('sisi',70);
INSERT INTO mANSWER(Uid, Cno) VALUES ('WP5Zrst',71);
INSERT INTO mANSWER(Uid, Cno) VALUES ('7mIf43',72);
INSERT INTO mANSWER(Uid, Cno) VALUES ('0tB6La',73);
INSERT INTO mANSWER(Uid, Cno) VALUES ('Xbe9HH',74);
INSERT INTO mANSWER(Uid, Cno) VALUES ('bkbk',75);
INSERT INTO mANSWER(Uid, Cno) VALUES ('grgr',76);
INSERT INTO mANSWER(Uid, Cno) VALUES ('bjCK5e',77);
INSERT INTO mANSWER(Uid, Cno) VALUES ('24D185',78);
INSERT INTO mANSWER(Uid, Cno) VALUES ('test1',79);
INSERT INTO mANSWER(Uid, Cno) VALUES ('0tB6La',80);
INSERT INTO mANSWER(Uid, Cno) VALUES ('24D185',81);
INSERT INTO mANSWER(Uid, Cno) VALUES ('0tB6La',82);
INSERT INTO mANSWER(Uid, Cno) VALUES ('test1',83);
INSERT INTO mANSWER(Uid, Cno) VALUES ('3w9S0',84);
INSERT INTO mANSWER(Uid, Cno) VALUES ('86I4uw',85);
INSERT INTO mANSWER(Uid, Cno) VALUES ('Xbe9HH',86);
INSERT INTO mANSWER(Uid, Cno) VALUES ('44w76',87);
INSERT INTO mANSWER(Uid, Cno) VALUES ('dVT9V',88);
INSERT INTO mANSWER(Uid, Cno) VALUES ('40016t',89);
INSERT INTO mANSWER(Uid, Cno) VALUES ('grgr',90);
INSERT INTO mANSWER(Uid, Cno) VALUES ('E672W9',91);
INSERT INTO mANSWER(Uid, Cno) VALUES ('dVT9V',92);
INSERT INTO mANSWER(Uid, Cno) VALUES ('l3EFx92',93);
INSERT INTO mANSWER(Uid, Cno) VALUES ('test2',94);
INSERT INTO mANSWER(Uid, Cno) VALUES ('l3EFx92',95);
INSERT INTO mANSWER(Uid, Cno) VALUES ('N5UEXE',96);
INSERT INTO mANSWER(Uid, Cno) VALUES ('test2',97);
INSERT INTO mANSWER(Uid, Cno) VALUES ('2060Hx8',98);
INSERT INTO mANSWER(Uid, Cno) VALUES ('WP5Zrst',99);
INSERT INTO mANSWER(Uid, Cno) VALUES ('dVT9V',100);
INSERT INTO mANSWER(Uid, Cno) VALUES ('Xbe9HH',101);
INSERT INTO mANSWER(Uid, Cno) VALUES ('57b6LpP',102);
INSERT INTO mANSWER(Uid, Cno) VALUES ('Qo64XGh',103);
INSERT INTO mANSWER(Uid, Cno) VALUES ('test2',104);
INSERT INTO mANSWER(Uid, Cno) VALUES ('l3EFx92',105);
INSERT INTO mANSWER(Uid, Cno) VALUES ('N5UEXE',106);
