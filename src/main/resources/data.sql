-- users テーブルにデータを挿入するクエリ
INSERT INTO users ( email, name, password)
VALUES
('tanaka@aaa.com', '田中太郎', 'test123'),
('suzuki@aaa.com', '鈴木一郎', 'test456');


--categories テーブルにデータを挿入するクエリ
INSERT INTO categories(name) VALUES('運動');
INSERT INTO categories(name) VALUES('仕事');
INSERT INTO categories(name) VALUES('その他');

-- tasks テーブルにデータを挿入するクエリ
INSERT INTO tasks (user_id, category_id,title, closing_date, progress, memo)
VALUES
(1, 1, '筋トレ', '2026/5/18', 0, '腕立て、背筋、腹筋をそれぞれ100回する'),
(2, 2, '何日までに納期を終わらせる', '2026/5/18', 1, '納期が終わるまで1時間残業する');
