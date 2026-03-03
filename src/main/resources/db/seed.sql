-- This file populates the database with initial testing data.
-- HOW TO RUN:
--   Run schema.sql first, then run this file in DataGrip.
--
-- TEST LOGIN CREDENTIALS:
--   Admin:   email: admin@nola.gov    password: Test@1234
--   Citizen: email: citizen@nola.gov  password: Test@1234
-- ============================================================


-- ============================================================
-- DEPARTMENTS
-- Pre-loading all New Orleans city departments
-- ============================================================
INSERT INTO departments (name, description, contact_email, contact_phone) VALUES
('Public Works',
 'Handles road and infrastructure repairs',
 'publicworks@nola.gov',
 '(504) 658-8000'),

('Sewage & Water Board',
 'Manages water and sewage systems',
 'swb@nola.gov',
 '(504) 529-2837'),

('Department of Public Safety',
 'Oversees public safety infrastructure',
 'safety@nola.gov',
 '(504) 658-8065'),

('Parks & Parkways',
 'Maintains parks and green spaces',
 'parks@nola.gov',
 '(504) 658-3200'),

('Traffic Engineering',
 'Manages traffic signs and signals',
 'traffic@nola.gov',
 '(504) 658-8080'),

('Sanitation',
 'Handles waste and debris removal',
 'sanitation@nola.gov',
 '(504) 658-8090');


-- ============================================================
-- USERS
-- One test ADMIN and one test CITIZEN account
-- Password for both accounts is: Test@1234
-- Passwords are hashed using BCrypt with cost factor 12
-- ============================================================
INSERT INTO users (username, email_or_phonenum, password_hash, role) VALUES
('admin_user',
 'admin@nola.gov',
 '$2a$12$pCkJkNzBqcDLwMbJXBWnHOQfh6Ah4pJ5qJxMBbGhMJyMWkFvJfKHa',
 'ADMIN'),

('citizen_user',
 'citizen@nola.gov',
 '$2a$12$pCkJkNzBqcDLwMbJXBWnHOQfh6Ah4pJ5qJxMBbGhMJyMWkFvJfKHa',
 'CITIZEN');


-- ============================================================
-- SAMPLE REPORTS
-- A mix of categories, severities, and statuses across
-- real New Orleans locations for testing the map view
-- ============================================================
INSERT INTO reports (title, description, category, severity, latitude, longitude, status, created_by) VALUES

('Large Pothole on Canal Street',
 'Deep pothole approximately 2 feet wide causing damage to vehicles passing through',
 'POTHOLE', 'HIGH',
 29.9584, -90.0776,
 'OPEN', 2),

('Flooding on Magazine Street',
 'Street flooding after heavy rain making road impassable for vehicles',
 'FLOODING', 'CRITICAL',
 29.9245, -90.0856,
 'IN_PROGRESS', 2),

('Cracked Road on Bourbon Street',
 'Large crack running across the entire width of the road near the intersection',
 'CRACK', 'MEDIUM',
 29.9583, -90.0653,
 'OPEN', 2),

('Damaged Street Sign on St. Charles Ave',
 'Stop sign knocked over and laying on the sidewalk creating a safety hazard',
 'SIGN_DAMAGE', 'HIGH',
 29.9401, -90.0855,
 'OPEN', 2),

('Debris Blocking Road on Tchoupitoulas St',
 'Large pile of debris blocking the right lane after recent storm',
 'DEBRIS', 'MEDIUM',
 29.9382, -90.0776,
 'RESOLVED', 2),

('Road Collapse on Esplanade Ave',
 'Section of road has collapsed near the intersection creating a dangerous sinkhole',
 'ROAD_COLLAPSE', 'CRITICAL',
 29.9697, -90.0742,
 'IN_PROGRESS', 2);