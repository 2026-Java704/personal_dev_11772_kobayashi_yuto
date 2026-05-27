-- users テーブルにデータを挿入するクエリ
INSERT INTO users ( email, name, password)
VALUES
('KobayashiYuto@gmail.com', '小林悠斗', 'test456');

--categories テーブルにデータを挿入するクエリ
INSERT INTO categories(name) VALUES('運動');
INSERT INTO categories(name) VALUES('勉強');
INSERT INTO categories(name) VALUES('仕事');
INSERT INTO categories(name) VALUES('その他');

-- tasks テーブルにデータを挿入するクエリ
INSERT INTO tasks (user_id, category_id,title, closing_date, progress, memo)
VALUES
(1, 2, '数学', '2026/5/28', 0, '事前に予習をしておくようにする');

