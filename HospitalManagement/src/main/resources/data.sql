INSERT INTO patient (name, birth_date, email, gender, blood_group)
VALUES
('Anil Kumar', '1990-05-12', 'anil.kumar@gmail.com', 'Male', 'O_POSITIVE'),

('Sharanya Jakkula', '2005-03-03', 'sharanya.j@gmail.com', 'Female', 'A_POSITIVE'),

('Ravi Teja', '1988-11-20', 'raviteja@gmail.com', 'Male', 'B_POSITIVE'),

('Sneha Reddy', '1995-07-15', 'sneha.reddy@gmail.com', 'Female', 'AB_POSITIVE'),

('Vikram Rao', '1982-01-08', 'vikram.rao@gmail.com', 'Male', 'O_NEGATIVE');

INSERT INTO doctor (name, specialization, email)
VALUES
('Dr. Anil Kumar', 'Cardiologist', 'anil.kumar@hospital.com'),
('Dr. Priya Sharma', 'Dermatologist', 'priya.sharma@hospital.com'),
('Dr. Ravi Teja', 'Orthopedic', 'ravi.teja@hospital.com'),
('Dr. Neha Verma', 'Pediatrician', 'neha.verma@hospital.com'),
('Dr. Suresh Rao', 'Neurologist', 'suresh.rao@hospital.com');

INSERT INTO appointment (appointment_time, reason, patient_id, doctor_id)
VALUES
('2026-02-01 10:30:00', 'Regular heart checkup', 1, 1),
('2026-02-01 11:15:00', 'Skin allergy consultation', 2, 2),
('2026-02-02 09:00:00', 'Knee pain evaluation', 3, 3),
('2026-02-02 10:45:00', 'Child fever and cold', 4, 4),
('2026-02-03 12:00:00', 'Migraine and headache issues', 5, 5);
