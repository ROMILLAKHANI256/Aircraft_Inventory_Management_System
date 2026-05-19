-- Drop tables if they exist to start fresh
DROP TABLE IF EXISTS product_parts;
DROP TABLE IF EXISTS products;
DROP TABLE IF EXISTS parts;

-- Create tables
CREATE TABLE parts (
    id INTEGER PRIMARY KEY,
    name TEXT,
    price REAL,
    stock INTEGER,
    min INTEGER,
    max INTEGER,
    machineId INTEGER,
    companyName TEXT,
    partType TEXT
);

CREATE TABLE products (
    id INTEGER PRIMARY KEY,
    name TEXT,
    price REAL,
    stock INTEGER,
    min INTEGER,
    max INTEGER
);

CREATE TABLE product_parts (
    product_id INTEGER,
    part_id INTEGER,
    PRIMARY KEY (product_id, part_id),
    FOREIGN KEY (product_id) REFERENCES products(id),
    FOREIGN KEY (part_id) REFERENCES parts(id)
);

-- Insert parts (only InHouse parts given, companyName NULL, partType='InHouse')
INSERT INTO parts (id, name, price, stock, min, max, machineId, companyName, partType) VALUES
(1, 'Jet Engine', 1200000.00, 2, 1, 5, 1001, NULL, 'InHouse'),
(2, 'Landing Gear', 450000.00, 4, 1, 6, 1002, NULL, 'InHouse'),
(3, 'Avionics System', 250000.00, 5, 1, 10, 1003, NULL, 'InHouse'),
(4, 'Cabin Seat', 1200.00, 100, 20, 200, 1004, NULL, 'InHouse'),
(5, 'Cockpit Display', 35000.00, 6, 1, 10, 1005, NULL, 'InHouse'),
(6, 'Wing Flap', 18000.00, 10, 2, 15, 1006, NULL, 'InHouse'),
(7, 'Oxygen Mask', 800.00, 50, 10, 100, 1007, NULL, 'InHouse'),
(8, 'Fuel Pump', 15000.00, 7, 2, 10, 1008, NULL, 'InHouse'),
(9, 'Hydraulic System', 40000.00, 3, 1, 5, 1009, NULL, 'InHouse'),
(10, 'Radar System', 60000.00, 2, 1, 3, 1010, NULL, 'InHouse');

-- Insert products
INSERT INTO products (id, name, price, stock, min, max) VALUES
(1, 'Cargo Plane A1', 10000000.00, 2, 1, 4),
(2, 'Passenger Jet B2', 25000000.00, 3, 1, 5),
(3, 'Fighter Jet C3', 40000000.00, 1, 1, 2),
(4, 'Private Jet D4', 15000000.00, 2, 1, 3),
(5, 'Helicopter E5', 12000000.00, 4, 1, 6),
(6, 'Glider F6', 300000.00, 5, 1, 10),
(7, 'Drone G7', 150000.00, 10, 2, 20),
(8, 'Training Plane H8', 2500000.00, 6, 1, 8),
(9, 'Surveillance Jet I9', 22000000.00, 1, 1, 3),
(10, 'Bomber Jet J10', 55000000.00, 1, 1, 2);

-- Insert product-part associations
INSERT INTO product_parts (product_id, part_id) VALUES
-- product 1 associations
(1, 1),
(1, 2),

-- product 2 associations
(2, 3),
(2, 4),
(2, 5),

-- product 3 associations
(3, 1),
(3, 9),

-- product 4 associations
(4, 4),
(4, 5),

-- product 5 associations
(5, 6),
(5, 8),

-- product 6 associations
(6, 4),

-- product 7 associations
(7, 7),
(7, 10),

-- product 8 associations
(8, 3),
(8, 4),

-- product 9 associations
(9, 10),
(9, 1),

-- product 10 associations
(10, 9),
(10, 2);
