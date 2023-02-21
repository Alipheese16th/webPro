-- �������� ���̺� DROP & CREATE

DROP TABLE FRIEND;
DROP SEQUENCE FRIEND_NO_SEQ;

CREATE TABLE FRIEND(
    NO      NUMBER(4) PRIMARY KEY,
    NAME    VARCHAR2(30) NOT NULL,
    TEL     VARCHAR2(30)
);

CREATE SEQUENCE FRIEND_NO_SEQ
    START WITH 1
    INCREMENT BY 1
    MAXVALUE 9999
    MINVALUE 1
    NOCACHE
    NOCYCLE;


-- ģ�� �߰� public int addFriend(String name,String tel)
INSERT INTO FRIEND (NO,NAME,TEL) VALUES(FRIEND_NO_SEQ.NEXTVAL,'���̺�','032-7777-7777');

-- ģ�� ��� ��� public ArrayList<FriendDto> getList()
SELECT * FROM FRIEND;

-- ģ�� �˻�(���� �߰�)
SELECT * FROM FRIEND WHERE NAME LIKE '%'||'��ġ'||'%' AND TEL LIKE '%'||'4656'||'%';


COMMIT;



