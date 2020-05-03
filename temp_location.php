<?php

$conn=mysqli_connect("localhost","","","userdb");
$country=$_GET['country'];
$district=$_GET['district'];
$subdistrict=$_GET['subdistrict'];
$region=$_GET['region'];
$sql="SELECT products.description,products.price, products.imagepath,login_info.name, login_info.country,login_info.district,login_info.subdistrict, login_info.region FROM products JOIN login_info ON products.user_name=login_info.user_name where login_info.country='$country' AND login_info.district='$district' AND login_info.subdistrict ='$subdistrict' AND login_info.region='$region' ORDER BY RAND() LIMIT  5";
$response=array();
$result=mysqli_query($conn,$sql);
while($row=mysqli_fetch_array($result)){
		array_push($response,array('name'=>$row['description'],
			'price'=>$row['price'],'image_path'=>$row['imagepath'],'country'=>$row['country'],
			'district'=>$row['district'],'subdistrict'=>$row['subdistrict'],
			'region'=>$row['region'],'shop_name'=>$row['name']));
	}
	echo json_encode($response);






?>