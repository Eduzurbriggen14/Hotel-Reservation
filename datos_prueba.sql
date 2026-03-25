-- ===== INSERTAR USUARIOS =====
INSERT INTO `user` (user_name, user_email, user_password, name, last_name, user_rol, user_status) VALUES
('admin', 'admin@hotel.com', 'Admin123!', 'Carlos', 'Rodríguez', 'ADMIN', 'ACTIVE'),
('jperez', 'juan.perez@email.com', 'Pass1234', 'Juan', 'Pérez', 'CLIENT', 'ACTIVE'),
('mgarcia', 'maria.garcia@email.com', 'Pass1234', 'María', 'García', 'CLIENT', 'ACTIVE'),
('lmartinez', 'luis.martinez@email.com', 'Pass1234', 'Luis', 'Martínez', 'CLIENT', 'ACTIVE'),
('employee1', 'empleado1@hotel.com', 'Emp1234', 'Pedro', 'Sánchez', 'EMPLOYEE', 'ACTIVE'),
('employee2', 'empleado2@hotel.com', 'Emp1234', 'Ana', 'López', 'EMPLOYEE', 'ACTIVE'),
('aruiz', 'ana.ruiz@email.com', 'Pass1234', 'Ana', 'Ruiz', 'CLIENT', 'ACTIVE'),
('cfernandez', 'carlos.fernandez@email.com', 'Pass1234', 'Carlos', 'Fernández', 'CLIENT', 'INACTIVE');

-- ===== INSERTAR CATEGORÍAS =====
INSERT INTO category (description, price_per_night, max_occupancy, category_type) VALUES
('Habitación Simple: Ideal para una persona, cama individual, baño privado, TV y WiFi.', 1500.00, 1, 'SIMPLE'),
('Habitación Doble: Perfecta para parejas o dos personas, cama matrimonial, baño privado, TV, WiFi y minibar.', 2500.00, 2, 'DOUBLE'),
('Suite de Lujo: Amplia suite con sala de estar, cama king size, jacuzzi, balcón con vista, TV, WiFi y servicio de habitación premium.', 5000.00, 4, 'SUITE');

-- ===== INSERTAR HABITACIONES =====
-- Habitaciones Simples
INSERT INTO room (room_number, room_state, category_id) VALUES
('101', 'AVAILABLE', 1),
('102', 'AVAILABLE', 1),
('103', 'OCCUPIED', 1),
('104', 'AVAILABLE', 1),
('105', 'DIRTY', 1);

-- Habitaciones Dobles
INSERT INTO room (room_number, room_state, category_id) VALUES
('201', 'AVAILABLE', 2),
('202', 'OCCUPIED', 2),
('203', 'AVAILABLE', 2),
('204', 'CLEANING', 2),
('205', 'AVAILABLE', 2),
('206', 'AVAILABLE', 2);

-- Suites
INSERT INTO room (room_number, room_state, category_id) VALUES
('301', 'AVAILABLE', 3),
('302', 'OCCUPIED', 3),
('303', 'AVAILABLE', 3);

-- ===== INSERTAR RESERVACIONES =====
-- Reservaciones activas (NOTA: number_of_guests con 's' al final)
INSERT INTO reservation (check_in_date, check_out_date, number_of_guests, total_amount, reservation_status, user_id, room_id) VALUES
('2026-03-01', '2026-03-05', 1, 6000.00, 'CONFIRMED', 2, 3),
('2026-03-02', '2026-03-07', 2, 12500.00, 'CONFIRMED', 3, 7),
('2026-03-03', '2026-03-10', 3, 35000.00, 'CONFIRMED', 4, 13);

-- Reservaciones pendientes
INSERT INTO reservation (check_in_date, check_out_date, number_of_guests, total_amount, reservation_status, user_id, room_id) VALUES
('2026-03-15', '2026-03-20', 1, 7500.00, 'PENDING', 2, 1),
('2026-03-18', '2026-03-22', 2, 10000.00, 'PENDING', 7, 6);

-- Reservaciones pasadas completadas
INSERT INTO reservation (check_in_date, check_out_date, number_of_guests, total_amount, reservation_status, user_id, room_id) VALUES
('2026-02-10', '2026-02-15', 1, 7500.00, 'COMPLETED', 2, 2),
('2026-02-15', '2026-02-20', 2, 12500.00, 'COMPLETED', 3, 8);

-- Reservación cancelada
INSERT INTO reservation (check_in_date, check_out_date, number_of_guests, total_amount, reservation_status, user_id, room_id) VALUES
('2026-03-25', '2026-03-30', 1, 7500.00, 'CANCELLED', 4, 4);

-- ===== INSERTAR SERVICIOS DE HABITACIÓN =====
-- IMPORTANTE: service_start y service_end ahora son DATETIME (fecha + hora)

-- Servicios de limpieza
INSERT INTO room_service (service_date, service_start, service_end, notes, service_type, service_status, user_id, room_id) VALUES
('2026-03-03', 'Limpieza diaria estándar', 'CLEANING', 'COMPLETED', 5, 1),
('2026-03-03', 'Limpieza profunda solicitada', 'CLEANING', 'COMPLETED', 5, 2),
('2026-03-03', 'Limpieza después del checkout', 'CLEANING', 'IN_PROGRESS', 6, 5);

-- Servicios de mantenimiento
INSERT INTO room_service (service_date, service_start, service_end, notes, service_type, service_status, user_id, room_id) VALUES
('2026-03-03',  'Reparar aire acondicionado', 'MAINTENANCE', 'IN_PROGRESS', 5, 9),
('2026-03-04',  'Reparación de TV programada', 'MAINTENANCE', 'PENDING', 5, 4);

-- Servicio de habitación (comida)
INSERT INTO room_service (service_date, service_start, service_end, notes, service_type, service_status, user_id, room_id) VALUES
('2026-03-03', 'Almuerzo solicitado - Suite 302', 'ROOM_SERVICE', 'COMPLETED', 6, 13),
('2026-03-03', 'Cena para dos - Habitación 202', 'ROOM_SERVICE', 'PENDING', 6, 7);

-- Servicio de lavandería
INSERT INTO room_service (service_date, service_start, service_end, notes, service_type, service_status, user_id, room_id) VALUES
('2026-03-03', 'Lavandería express', 'LAUNDRY', 'IN_PROGRESS', 6, 3),
('2026-03-02', 'Servicio de lavandería estándar', 'LAUNDRY', 'COMPLETED', 6, 13);

