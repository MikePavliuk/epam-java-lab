-- -----------------------------------------------------
-- Data for table `smart_cookie`.`genre`
-- -----------------------------------------------------
DELETE FROM genre;

INSERT INTO `smart_cookie`.`genre` (`id`, `name`) VALUES (1, 'fiction');
INSERT INTO `smart_cookie`.`genre` (`id`, `name`) VALUES (2, 'novel');
INSERT INTO `smart_cookie`.`genre` (`id`, `name`) VALUES (3, 'cookbook');
INSERT INTO `smart_cookie`.`genre` (`id`, `name`) VALUES (4, 'detective');
INSERT INTO `smart_cookie`.`genre` (`id`, `name`) VALUES (5, 'historical');
INSERT INTO `smart_cookie`.`genre` (`id`, `name`) VALUES (6, 'horror');
INSERT INTO `smart_cookie`.`genre` (`id`, `name`) VALUES (7, 'science');

-- -----------------------------------------------------
-- Data for table `smart_cookie`.`language`
-- -----------------------------------------------------
DELETE FROM language;

INSERT INTO `smart_cookie`.`language` (`id`, `name`) VALUES (1, 'english');
INSERT INTO `smart_cookie`.`language` (`id`, `name`) VALUES (2, 'ukrainian');

-- -----------------------------------------------------
-- Data for table `smart_cookie`.`publication`
-- -----------------------------------------------------
DELETE FROM publication;

INSERT INTO `smart_cookie`.`publication` (`id`, `price_per_month`, `genre_id`) VALUES (1, 10, 1);
INSERT INTO `smart_cookie`.`publication` (`id`, `price_per_month`, `genre_id`) VALUES (2, 8.25, 1);
INSERT INTO `smart_cookie`.`publication` (`id`, `price_per_month`, `genre_id`) VALUES (3, 14.10, 2);
INSERT INTO `smart_cookie`.`publication` (`id`, `price_per_month`, `genre_id`) VALUES (4, 12.50, 2);
INSERT INTO `smart_cookie`.`publication` (`id`, `price_per_month`, `genre_id`) VALUES (5, 17.99, 3);
INSERT INTO `smart_cookie`.`publication` (`id`, `price_per_month`, `genre_id`) VALUES (6, 12, 3);
INSERT INTO `smart_cookie`.`publication` (`id`, `price_per_month`, `genre_id`) VALUES (7, 3.99, 4);
INSERT INTO `smart_cookie`.`publication` (`id`, `price_per_month`, `genre_id`) VALUES (8, 4.95, 4);
INSERT INTO `smart_cookie`.`publication` (`id`, `price_per_month`, `genre_id`) VALUES (9, 4.99, 5);
INSERT INTO `smart_cookie`.`publication` (`id`, `price_per_month`, `genre_id`) VALUES (10, 4, 5);
INSERT INTO `smart_cookie`.`publication` (`id`, `price_per_month`, `genre_id`) VALUES (11, 11.99, 6);
INSERT INTO `smart_cookie`.`publication` (`id`, `price_per_month`, `genre_id`) VALUES (12, 8.50, 6);
INSERT INTO `smart_cookie`.`publication` (`id`, `price_per_month`, `genre_id`) VALUES (13, 15, 7);
INSERT INTO `smart_cookie`.`publication` (`id`, `price_per_month`, `genre_id`) VALUES (14, 14.25, 7);

-- -----------------------------------------------------
-- Data for table `smart_cookie`.`publication_info`
-- -----------------------------------------------------
DELETE FROM publication_info;

INSERT INTO `smart_cookie`.`publication_info` (`id`, `publication_id`, `language_id`, `title`, `description`) VALUES (1, 1, 1, 'The Magazine of Fantasy & Science Fiction', 'It is a Hugo, World Fantasy, and British Fantasy Award-winning science fiction and fantasy magazine that publishes short stories, interviews, articles and audio fiction.');
INSERT INTO `smart_cookie`.`publication_info` (`id`, `publication_id`, `language_id`, `title`, `description`) VALUES (2, 1, 2, 'Журнал фентезі та наукової фантастики', 'Це журнал із наукової фантастики та фентезі, який отримав нагороди Hugo, World Fantasy та British Fantasy, який публікує короткі історії, інтерв’ю, статті та аудіофантастику.');
INSERT INTO `smart_cookie`.`publication_info` (`id`, `publication_id`, `language_id`, `title`, `description`) VALUES (3, 2, 1, 'SFX', 'Covers sci-fi movies, TV shows, books, video games and more! We\'re an online magazine focused on sci-fi and fantasy entertainment.');
INSERT INTO `smart_cookie`.`publication_info` (`id`, `publication_id`, `language_id`, `title`, `description`) VALUES (4, 2, 2, 'SFX', 'Охоплює науково-фантастичні фільми, телешоу, книги, відеоігри та багато іншого! Ми – онлайн-журнал, присвячений науково-фантастичним і фантастичним розвагам.');
INSERT INTO `smart_cookie`.`publication_info` (`id`, `publication_id`, `language_id`, `title`, `description`) VALUES (5, 3, 1, 'The New Yorker', 'The New Yorker is an American magazine featuring all kind of novels.');
INSERT INTO `smart_cookie`.`publication_info` (`id`, `publication_id`, `language_id`, `title`, `description`) VALUES (6, 3, 2, 'Ньюйорківець', 'Ньюйорківець — американський журнал, у якому представлені всі види романів.');
INSERT INTO `smart_cookie`.`publication_info` (`id`, `publication_id`, `language_id`, `title`, `description`) VALUES (7, 4, 1, 'Granta ', 'Granta’s Best of Young British Novelists 5 is now open for nominations.');
INSERT INTO `smart_cookie`.`publication_info` (`id`, `publication_id`, `language_id`, `title`, `description`) VALUES (8, 4, 2, 'Гранта', 'Найкращий з молодих британських романістів Гранта тепер відкритий для номінацій.');
INSERT INTO `smart_cookie`.`publication_info` (`id`, `publication_id`, `language_id`, `title`, `description`) VALUES (9, 5, 1, 'The Arbuturian ', 'The Arbuturian is a magazine covering food and drink as art depiction!');
INSERT INTO `smart_cookie`.`publication_info` (`id`, `publication_id`, `language_id`, `title`, `description`) VALUES (10, 5, 2, 'Арбутурян', 'Журнал, який висвітлює їжу та напої як художнє зображення!');
INSERT INTO `smart_cookie`.`publication_info` (`id`, `publication_id`, `language_id`, `title`, `description`) VALUES (11, 6, 1, 'Cocina', 'It features recipes, cooking tips, culinary tourism information, restaurant reviews, chefs, wine pairings ');
INSERT INTO `smart_cookie`.`publication_info` (`id`, `publication_id`, `language_id`, `title`, `description`) VALUES (12, 6, 2, 'Кокіна', 'Тут представлені рецепти, кулінарні поради, інформація про кулінарний туризм, відгуки про ресторани, кухарі, винні пари');
INSERT INTO `smart_cookie`.`publication_info` (`id`, `publication_id`, `language_id`, `title`, `description`) VALUES (13, 7, 1, 'True Crime', 'Brought to you by world\'s top experts in true crime world, the True Crime e-Magazine offers an exclusive and fascinating journey through the criminal mind!');
INSERT INTO `smart_cookie`.`publication_info` (`id`, `publication_id`, `language_id`, `title`, `description`) VALUES (14, 7, 2, 'Справжній злочин', 'Електронний журнал, створений для вас найкращими експертами зі світу справжньої злочинності, пропонує ексклюзивну та захоплюючу подорож кримінальним розумом!');
INSERT INTO `smart_cookie`.`publication_info` (`id`, `publication_id`, `language_id`, `title`, `description`) VALUES (15, 8, 1, 'Parkaman', 'At Parkaman Magazine, you can find all True Crime stories, articles and news on serial killers, famous murder cases, unsolved crime mysteries, missing cases and crime media.');
INSERT INTO `smart_cookie`.`publication_info` (`id`, `publication_id`, `language_id`, `title`, `description`) VALUES (16, 8, 2, 'Паркаман', 'У журналі ви можете знайти всі історії про справжні злочини, статті та новини про серійних вбивць, відомі справи про вбивства, нерозкриті таємниці злочинів, справи про зникнення та кримінальні ЗМІ.');
INSERT INTO `smart_cookie`.`publication_info` (`id`, `publication_id`, `language_id`, `title`, `description`) VALUES (17, 9, 1, 'History Today', 'History Today is the world\'s leading serious history magazine. We publish the world\'s leading scholars, on all periods, regions and themes of history.');
INSERT INTO `smart_cookie`.`publication_info` (`id`, `publication_id`, `language_id`, `title`, `description`) VALUES (18, 9, 2, 'Історія сьогодні', 'History Today — провідний серйозний історичний журнал у світі. Ми публікуємо провідних світових науковців на всі періоди, регіони та теми історії.');
INSERT INTO `smart_cookie`.`publication_info` (`id`, `publication_id`, `language_id`, `title`, `description`) VALUES (19, 10, 1, 'HistoryNet', 'HistoryNet.com is brought to you by Historynet LLC, the world\'s largest publisher of history magazines. HistoryNet.com contains daily features, photo galleries and over 5,000 articles originally published in our various magazines.');
INSERT INTO `smart_cookie`.`publication_info` (`id`, `publication_id`, `language_id`, `title`, `description`) VALUES (20, 10, 2, 'HistoryNet', 'HistoryNet.com пропонує вам Historynet LLC, найбільший у світі видавець історичних журналів. HistoryNet.com містить щоденні статті, фотогалереї та понад 5000 статей, спочатку опублікованих у наших різних журналах.');
INSERT INTO `smart_cookie`.`publication_info` (`id`, `publication_id`, `language_id`, `title`, `description`) VALUES (21, 11, 1, 'Rue Morgue', 'Launched in 1997 by Rodrigo Gudio, RUE MORGUE is the world\'s leading horror in culture and entertainment brand, spearheaded by its multiple award-winning magazine');
INSERT INTO `smart_cookie`.`publication_info` (`id`, `publication_id`, `language_id`, `title`, `description`) VALUES (22, 11, 2, 'Рю Морг', 'Заснований у 1997 році Родріго Гудіо, Рю Морг є провідним світовим брендом жахів у сфері культури та розваг, очолюваний його журналом, удостоєним багатьох нагород.');
INSERT INTO `smart_cookie`.`publication_info` (`id`, `publication_id`, `language_id`, `title`, `description`) VALUES (23, 12, 1, 'Scream Magazine', 'The world\'s scariest publication. We are the WORLD\'s NO.1 print & digital full colour magazine bringing you everything in the name of horror... The Horror Magazine in print and digital...for Horror News, Interviews, Reviews, Previews and Trailers.');
INSERT INTO `smart_cookie`.`publication_info` (`id`, `publication_id`, `language_id`, `title`, `description`) VALUES (24, 12, 2, 'Журнал «Крик»', 'Найжахливіше видання світу. Ми є друкованим і цифровим повнокольоровим журналом №1 у світі, який пропонує вам все в ім’я жахів... The Horror Magazine у друкованому та цифровому форматі... для новин жахів, інтерв’ю, оглядів, прев’ю та трейлерів.');
INSERT INTO `smart_cookie`.`publication_info` (`id`, `publication_id`, `language_id`, `title`, `description`) VALUES (25, 13, 1, 'Science Magazine', 'Science is a leading outlet for scientific news, commentary, and cutting-edge research. Science\'s authorship is global too, and its articles consistently rank among the world\'s most cited research.');
INSERT INTO `smart_cookie`.`publication_info` (`id`, `publication_id`, `language_id`, `title`, `description`) VALUES (26, 13, 2, 'Науковий журнал', 'Є провідним джерелом наукових новин, коментарів та передових досліджень. Авторство науки також є глобальним, і її статті стабільно входять до числа найбільш цитованих досліджень у світі.');
INSERT INTO `smart_cookie`.`publication_info` (`id`, `publication_id`, `language_id`, `title`, `description`) VALUES (27, 14, 1, 'New Scientist', 'New Scientist is the best place to find out what\'s new in science. It is the world\'s number one science and technology magazine, and online it is the go-to site for breaking news, exclusive content and breakthroughs that will change your world.');
INSERT INTO `smart_cookie`.`publication_info` (`id`, `publication_id`, `language_id`, `title`, `description`) VALUES (28, 14, 2, 'Новий Вчений', 'Найкраще місце, щоб дізнатися, що нового в науці. Це провідний у світі науково-технічний журнал, а в Інтернеті це найпопулярніший сайт для останніх новин, ексклюзивного контенту та проривів, які змінять ваш світ.');

-- -----------------------------------------------------
-- Data for table `smart_cookie`.`role`
-- -----------------------------------------------------
DELETE FROM role;

INSERT INTO `smart_cookie`.`role` (`id`, `name`) VALUES (1, 'subscriber');
INSERT INTO `smart_cookie`.`role` (`id`, `name`) VALUES (2, 'admin');

-- -----------------------------------------------------
-- Data for table `smart_cookie`.`user_status`
-- -----------------------------------------------------
DELETE FROM user_status;

INSERT INTO `smart_cookie`.`user_status` (`id`, `name`) VALUES (1, 'active');
INSERT INTO `smart_cookie`.`user_status` (`id`, `name`) VALUES (2, 'blocked');

-- -----------------------------------------------------
-- Data for table `smart_cookie`.`user`
-- -----------------------------------------------------
DELETE FROM user;

INSERT INTO `smart_cookie`.`user` (`id`, `email`, `password`, `role_id`, `user_status_id`) VALUES (1, 'admin@gmail.com', 'Admin12345!', 2, 1);
