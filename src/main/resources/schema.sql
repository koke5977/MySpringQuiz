DROP TABLE IF EXISTS question;
CREATE TABLE question
(
   id INT NOT NULL,            --クイズID
   question TEXT NOT NULL,     --問題文
   choice1 TEXT NOT NULL,      --選択肢1
   choice2 TEXT NOT NULL,      --選択肢2
   choice3 TEXT NOT NULL,      --選択肢3
   choice4 TEXT NOT NULL,      --選択肢4
   answer_id INT NOT NULL,    --解答ID
   answer TEXT NOT NULL,       --解答
   description TEXT NOT NULL,  --解説文
   answered boolean,           --解答済みであるか
   correct boolean,            --正解であるか
   PRIMARY KEY(id)
);