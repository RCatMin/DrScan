CREATE USER web_dev@"%" IDENTIFIED BY "n39qo1QXqjmPJLd";

GRANT SELECT, INSERT, DELETE, UPDATE ON drscan_db.* TO web_dev@"%";

CREATE TABLE users (
user_code INT AUTO_INCREMENT PRIMARY KEY,
account_type ENUM('admin', 'user', 'temporary') DEFAULT 'temporary',
username VARCHAR(20) NOT NULL UNIQUE CHECK(username REGEXP '^[a-zA-Z0-9-_]{6,20}$'),
password VARCHAR(255) NOT NULL CHECK(password REGEXP '^[A-Za-z0-9]*[!@#$%^&*][A-Za-z0-9]*$'),
hospital_name VARCHAR(40),
department VARCHAR(20),
name VARCHAR(10) NOT NULL CHECK (name REGEXP '^[가-힣]{2,5}$'),
email VARCHAR(40) NOT NULL UNIQUE CHECK (email REGEXP '^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$'),
phone CHAR(13) NOT NULL UNIQUE,
otp_key VARCHAR(255) NOT NULL UNIQUE,
status ENUM('active', 'pending', 'suspended') DEFAULT 'pending',
fail_count TINYINT DEFAULT 0,
reg_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
mod_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
)AUTO_INCREMENT=1001;

CREATE TABLE permissions (
permission_code INT AUTO_INCREMENT PRIMARY KEY,
user_code INT UNIQUE,
permission ENUM('admin', 'user', 'temporary') DEFAULT 'temporary',
FOREIGN KEY (user_code) REFERENCES users(user_code) ON DELETE CASCADE
)AUTO_INCREMENT=1001;

CREATE TABLE logs (
code INT AUTO_INCREMENT PRIMARY KEY,
user_code INT,
report_code INT,
event VARCHAR(400),
event_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
FOREIGN KEY (user_code) REFERENCES users(user_code) ON DELETE SET NULL,
FOREIGN KEY (report_code) REFERENCES radiologist_reports(report_code) ON DELETE SET NULL
)AUTO_INCREMENT=1;

CREATE TABLE radiologist_reports (
report_code INT AUTO_INCREMENT PRIMARY KEY,
series_ins_uid VARCHAR(100) REFERENCES seriestab(seriesinsuid) ON DELETE SET NULL,
patient_id VARCHAR(6) REFERENCES patienttab (pid) ON DELETE SET NULL,
user_code INT REFERENCES users(user_code) ON DELETE SET NULL,
approve_user_code INT REFERENCES users(user_code) ON DELETE SET NULL,
study_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
approve_study_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
study_name VARCHAR(50),
modality VARCHAR(20),
body_part VARCHAR(50),
patient_name VARCHAR(100),
patient_sex ENUM('M', 'F'),
patient_birth_date TIMESTAMP,
patient_age CHAR,
severity_level ENUM('1', '2', '3', '4', '5'),
report_status ENUM('Draft', 'Finalized', 'Needs Revsion'),
reg_date TIMESTAMP,
mod_date TIMESTAMP,
FOREIGN KEY (user_code) REFERENCES users(user_code),
FOREIGN KEY (approve_user_code) REFERENCES users(user_code)
)AUTO_INCREMENT=1;

CREATE TABLE clinic (
clinic_code INT AUTO_INCREMENT PRIMARY KEY,
patient_code VARCHAR(6),
user_code INT,
clinic_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
context VARCHAR(200),
reg_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
mod_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
FOREIGN KEY (user_code) REFERENCES users(user_code) ON DELETE SET NULL
)AUTO_INCREMENT=10001;