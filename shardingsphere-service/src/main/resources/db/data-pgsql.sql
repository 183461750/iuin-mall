
-- 插入数据
begin;

truncate table com_user;

INSERT INTO com_user (id, pid, name, age, sex, email)
VALUES (1, 0, 'Jone', 18, 0, 'test1@baomidou.com'),
       (2, 1, 'Jack', 20, 1, 'test2@baomidou.com'),
       (3, 2, 'Tom', 28, 1, 'test3@baomidou.com'),
       (4, 2, 'Sandy', 21, 0, 'test4@baomidou.com'),
       (5, 3, 'Billie', 24, 1, 'test5@baomidou.com');

truncate table com_address;

INSERT INTO com_address (id, user_id, city, address)
VALUES (1, 1, '北京', '人民广场'),
       (2, 2, '上海', '人民广场'),
       (3, 3, '广州', '人民广场'),
       (4, 4, '上海', '人民广场'),
       (5, 5, '北京', '人民广场');

end;