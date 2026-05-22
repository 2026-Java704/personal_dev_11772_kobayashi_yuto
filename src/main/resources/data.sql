-- users テーブルにデータを挿入するクエリ
INSERT INTO users ( email, name, password)
VALUES
('kobayashi@aaa.com', '小林悠斗', 'test789');


--categories テーブルにデータを挿入するクエリ
INSERT INTO categories(name) VALUES('運動');
INSERT INTO categories(name) VALUES('勉強');
INSERT INTO categories(name) VALUES('仕事');
INSERT INTO categories(name) VALUES('その他');

-- tasks テーブルにデータを挿入するクエリ
INSERT INTO tasks (user_id, category_id,title, closing_date, progress, memo)
VALUES
(1, 1, '筋トレ', '2026/5/18', 0, '腕立て、背筋、腹筋をそれぞれ100回する');

