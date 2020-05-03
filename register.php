<?php

include_once("init3.php");

$country=$_GET["country"];
$district=$_GET["district"];
$subdistrict=$_GET["subdistrict"];
$region=$_GET["region"];
$name=$_GET["name"];
$user_name=$_GET["user_name"];
$user_password=$_GET["user_password"];





$sql="select * from login_info where user_name='$user_name'";

$result=mysqli_query($conn,$sql);

if(mysqli_num_rows($result)>0){
	$status="exist";
}else{
	$sql="insert into login_info(name,user_name,user_password,country,district,subdistrict,region) values(
'$name','$user_name','$user_password','$country','$district','$subdistrict','$region')";

if(mysqli_query($conn,$sql)){
	$status="OK";
}else{
	$status="Error";
}
}

echo json_encode(array("response"=>$status));
mysqli_close($conn);



?>