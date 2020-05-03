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

$user_name=$_GET['user_name'];
$user_password=$_GET['user_password'];

$sql="select name from login_info where user_name='$user_name' and user_password='$user_password'";

$result=mysqli_query($conn,$sql);

if(!mysqli_num_rows($result)>0){
	$status="failed";
	echo json_encode(array("response"=>$status));
}else{
	$row=mysqli_fetch_assoc($result);
	$name=$row['name'];
	$status="OK";
	echo json_encode(array("response"=>$status,"name"=>$name));
	
}
mysqli_close($conn);



?>