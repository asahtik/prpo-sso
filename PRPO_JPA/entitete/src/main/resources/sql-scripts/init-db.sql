INSERT INTO profesor (ime, priimek, email) VALUES ('A', 'A', 'A@A.a');
INSERT INTO student (ime, priimek, email, stizkaznice) VALUES ('B', 'B', 'b@b.b', '63180301');
INSERT INTO student (ime, priimek, email, stizkaznice) VALUES ('C', 'C', 'c@c.c', '63180302');
INSERT INTO termin (timestamp, maxst, location, profesor_id) VALUES (1608822000000, 5, 1, 1);
INSERT INTO termin (timestamp, maxst, location, profesor_id) VALUES (1608850000000, 10, 1, 1);
INSERT INTO prijava (email, potrjena, timestamp, student_id, termin_id) VALUES ('b@b.b', false, 1607961195000, 1, 1);
INSERT INTO prijava (email, potrjena, timestamp, student_id, termin_id) VALUES ('c@c.c', false, 1607961200000, 2, 1);
INSERT INTO prijava (email, potrjena, timestamp, student_id, termin_id) VALUES ('b@b.b', false, 1607961300000, 1, 2);