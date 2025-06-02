INSERT INTO book (id, title, publication_date)
VALUES (nextval('book_seq'), 'Il nome della rosa', '1980-10-01'),
       (nextval('book_seq'), 'Se questo è un uomo', '1947-01-01'),
       (nextval('book_seq'), 'Il Gattopardo', '1958-11-01'),
       (nextval('book_seq'), 'Cristo si è fermato a Eboli', '1945-01-01'),
       (nextval('book_seq'), 'La coscienza di Zeno', '1923-01-01'),
       (nextval('book_seq'), 'I promessi sposi', '1827-01-01'),
       (nextval('book_seq'), 'Canne al vento', '1913-01-01'),
       (nextval('book_seq'), 'Uno, nessuno e centomila', '1926-01-01'),
       (nextval('book_seq'), 'Il barone rampante', '1957-01-01'),
       (nextval('book_seq'), 'Le città invisibili', '1972-01-01');
INSERT INTO author (id, first_name, last_name, date_of_birth, date_of_death, nationality)
VALUES (nextval('author_seq'), 'Umberto', 'Eco', '1932-01-05', '2016-02-19', 'Italiana'),
       (nextval('author_seq'), 'Primo', 'Levi', '1919-07-31', '1987-04-11', 'Italiana'),
       (nextval('author_seq'), 'Giuseppe', 'Tomasi di Lampedusa', '1896-12-23', '1957-07-23', 'Italiana'),
       (nextval('author_seq'), 'Carlo', 'Levi', '1900-11-29', '1975-01-04', 'Italiana'),
       (nextval('author_seq'), 'Italo', 'Svevo', '1861-12-19', '1928-09-13', 'Italiana'),
       (nextval('author_seq'), 'Alessandro', 'Manzoni', '1785-03-07', '1873-05-22', 'Italiana'),
       (nextval('author_seq'), 'Grazia', 'Deledda', '1871-09-28', '1936-08-15', 'Italiana'),
       (nextval('author_seq'), 'Luigi', 'Pirandello', '1867-06-28', '1936-12-10', 'Italiana'),
       (nextval('author_seq'), 'Italo', 'Calvino', '1923-10-15', '1985-09-19', 'Italiana');


INSERT INTO book_authors (books_id, authors_id)
VALUES (1, 1), -- Il nome della rosa -> Umberto Eco
       (51, 51);-- Se questo è un uomo -> Primo Levi
INSERT INTO users (id, first_name,last_name,email)
VALUES
    (nextval('users_seq'), 'Mario','Rossi','marRos'),
    (nextval('users_seq'), 'Giuseppe','Verdi','giusVerdi'),
    (nextval('users_seq'), 'Alfonso','Neri','alfNeri'),
    (nextval('users_seq'), 'Utente','Test','utenteTest@mail.com'),
    (nextval('users_seq'), 'Admin','Admin','admin@mail.com');

insert into review (rating, author_id,book_id, id, title, text)
values
    (5,1,1,nextval('review_seq'),'Review Positivia','Commento positivo');
insert into credentials (id, user_id, password, role, username)
    values (nextval('credentials_seq'),151,'$2a$10$qmUgXJRxhVNDIWqi/zhi3uXEsgdYkm3LHOWmwontED/4uXwi0prYS','DEFAULT','test'),
           (nextval('credentials_seq'),201,'$2a$10$3a20w/4E8bvaI3M9QC6xseOyrabaUJFfnXWgEUx3iNzE4gDl1EZim','ADMIN','admin');