-- AUTORI (10 in totale, senza Elena Ferrante)
INSERT INTO author (id, first_name, last_name, date_of_birth, date_of_death, nationality)
VALUES
    (1, 'Umberto', 'Eco', '1932-01-05', '2016-02-19', 'Italiana'),
    (51, 'Primo', 'Levi', '1919-07-31', '1987-04-11', 'Italiana'),
    (52, 'Haruki', 'Murakami', '1949-01-12', NULL, 'Giapponese'),
    (54, 'Margaret', 'Atwood', '1939-11-18', NULL, 'Canadese'),
    (55, 'Chimamanda', 'Ngozi Adichie', '1977-09-15', NULL, 'Nigeriana'),
    (56, 'Khaled', 'Hosseini', '1965-03-04', NULL, 'Afghana'),
    (57, 'Sally', 'Rooney', '1991-02-20', NULL, 'Irlandese'),
    (58, 'Colson', 'Whitehead', '1969-11-06', NULL, 'Statunitense'),
    (59, 'Kazuo', 'Ishiguro', '1954-11-08', NULL, 'Britannico'),
    (60, 'Jhumpa', 'Lahiri', '1967-07-11', NULL, 'Italo-americana');

-- LIBRI (20 in totale, "L’amica geniale" rimosso e sostituito)
INSERT INTO book (id, title, publication_date)
VALUES
    (1, 'Il nome della rosa', '1980-10-01'),
    (2, 'Se questo è un uomo', '1947-01-01'),
    (3, 'Il Gattopardo', '1958-11-01'),
    (4, 'Cristo si è fermato a Eboli', '1945-01-01'),
    (5, 'La coscienza di Zeno', '1923-01-01'),
    (6, 'I promessi sposi', '1827-01-01'),
    (7, 'Canne al vento', '1913-01-01'),
    (8, 'Uno, nessuno e centomila', '1926-01-01'),
    (9, 'Il barone rampante', '1957-01-01'),
    (10, 'Le città invisibili', '1972-01-01'),
    (11, 'Norwegian Wood', '1987-09-04'),
    (12, 'Interpreter of Maladies', '1999-01-01'),
    (13, 'The Handmaid''s Tale', '1985-01-01'),
    (14, 'Americanah', '2013-05-01'),
    (15, 'The Kite Runner', '2003-05-29'),
    (16, 'Normal People', '2018-08-28'),
    (17, 'The Nickel Boys', '2019-07-16'),
    (18, 'Never Let Me Go', '2005-03-01'),
    (19, 'Colorless Tsukuru Tazaki and His Years of Pilgrimage', '2013-04-12'),
    (20, 'A Thousand Splendid Suns', '2007-05-22');

-- RELAZIONI LIBRO ↔ AUTORE (aggiornato)
INSERT INTO book_authors (books_id, authors_id)
VALUES
    (1, 1),   -- Il nome della rosa -> Umberto Eco
    (2, 51),  -- Se questo è un uomo -> Primo Levi
    (11, 52), -- Norwegian Wood -> Murakami
    (12, 60), -- Interpreter of Maladies -> Lahiri
    (13, 54), -- The Handmaid's Tale -> Atwood
    (14, 55), -- Americanah -> Adichie
    (15, 56), -- The Kite Runner -> Hosseini
    (16, 57), -- Normal People -> Rooney
    (17, 58), -- The Nickel Boys -> Whitehead
    (18, 59), -- Never Let Me Go -> Ishiguro
    (19, 52), -- Colorless Tsukuru Tazaki -> Murakami
    (20, 56); -- A Thousand Splendid Suns -> Hosseini

-- UTENTI
INSERT INTO users (id, first_name, last_name, email)
VALUES
    (nextval('users_seq'), 'Mario','Rossi','marRos'),
    (nextval('users_seq'), 'Giuseppe','Verdi','giusVerdi'),
    (nextval('users_seq'), 'Alfonso','Neri','alfNeri'),
    (nextval('users_seq'), 'Utente','Test','utenteTest@mail.com'),
    (nextval('users_seq'), 'Admin','Admin','admin@mail.com');

-- RECENSIONI
INSERT INTO review (rating, author_id, book_id, id, title, text)
VALUES
    (5, 1, 1, nextval('review_seq'), 'Review Positiva', 'Commento positivo'),
    (5, 1, 2, nextval('review_seq'), 'Review Positiva', 'Commento positivo'),
    (5, 51, 2, nextval('review_seq'), 'Review Positiva', 'Commento positivo');

-- CREDENZIALI
INSERT INTO credentials (id, user_id, password, role, username)
VALUES
    (nextval('credentials_seq'), 151, '$2a$10$qmUgXJRxhVNDIWqi/zhi3uXEsgdYkm3LHOWmwontED/4uXwi0prYS', 'DEFAULT', 'test'),
    (nextval('credentials_seq'), 201, '$2a$10$3a20w/4E8bvaI3M9QC6xseOyrabaUJFfnXWgEUx3iNzE4gDl1EZim', 'ADMIN', 'admin');
