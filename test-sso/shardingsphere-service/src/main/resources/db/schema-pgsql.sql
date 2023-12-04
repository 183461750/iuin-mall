
-- 建表
begin;

DROP TABLE IF EXISTS com_user;

CREATE TABLE com_user
(
    id    int8 NOT NULL,
    pid    int8 NOT NULL,
    name  VARCHAR(30) NULL DEFAULT NULL,
    age   int4 NULL DEFAULT NULL,
    sex   int4 NULL DEFAULT NULL,
    email VARCHAR(50) NULL DEFAULT NULL,
    PRIMARY KEY (id)
);

COMMENT ON COLUMN com_user.pid IS '父id';
COMMENT ON COLUMN com_user.name IS '姓名';
COMMENT ON COLUMN com_user.age IS '年龄';
COMMENT ON COLUMN com_user.sex IS '性别: 0女 1男';
COMMENT ON COLUMN com_user.email IS '邮箱';

DROP TABLE IF EXISTS com_address;

CREATE TABLE com_address
(
    id      int8 NOT NULL,
    user_id int8 NULL DEFAULT NULL,
    city    VARCHAR(50) NULL DEFAULT NULL,
    address VARCHAR(50) NULL DEFAULT NULL,
    PRIMARY KEY (id)
);

COMMENT ON COLUMN com_address.user_id IS '用户id';
COMMENT ON COLUMN com_address.city IS '城市';
COMMENT ON COLUMN com_address.address IS '地址';

end;
