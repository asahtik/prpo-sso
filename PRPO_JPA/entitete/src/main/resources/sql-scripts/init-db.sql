INSERT INTO Student (ime, priimek, email, stizkaznice) VALUES ('Petra', 'Kos', 'petrakos@gmail.com', 69);
INSERT INTO Student (ime, priimek, email, stizkaznice) VALUES ('Miha', 'Novak', 'miha.novak@gmail.com', 70);

INSERT INTO Profesor (ime, priimek, email) VALUES ('uciteljMiha', 'Novak', 'uciteljmiha.novak@gmail.com');
INSERT INTO Profesor (ime, priimek, email) VALUES ('uciteljMiha', 'Novak', 'uciteljmiha.novak@gmail.com');

INSERT INTO Prijava (datum, potrjena, email) VALUES ('2020-02-01', true,'miha.novak@gmail.com');
INSERT INTO Prijava (datum, potrjena, email) VALUES ('2020-02-02', true,'petrakos@gmail.com');

INSERT INTO Termin (datum,ura,maxSt,location) VALUES ('2020-02-01', '14:00', 5,'Ljubljana');
INSERT INTO Termin (datum,ura,maxSt,location) VALUES ('2020-02-02', '15:00', 5,'Ljubljana');
