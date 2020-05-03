<?php
$hostname="localhost";
$database_name="userdb";

$conn=mysqli_connect($hostname,"","",$database_name);

$sql2="CREATE TABLE login_info(
id INT(8) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
name VARCHAR(30) NOT NULL,
user_name VARCHAR(30) NOT NULL,
user_password VARCHAR(30) NOT NULL,
country VARCHAR(50) NOT NULL,
district VARCHAR(50) NOT NULL,
subdistrict VARCHAR(50) NOT NULL,
region VARCHAR(50) NOT NULL
)";
mysqli_query($conn,$sql2);

?>