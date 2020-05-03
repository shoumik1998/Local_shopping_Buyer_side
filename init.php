<?php
$hostname="localhost";
$database_name="userdb";

$conn=mysqli_connect($hostname,"","",$database_name);

$sql2="CREATE TABLE login_info(
id INT(8) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
name VARCHAR(30) NOT NULL,
user_name VARCHAR(30) NOT NULL,
user_password VARCHAR(30) NOT NULL
)";

?>