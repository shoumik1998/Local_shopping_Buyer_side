<?php

$conn=mysqli_connect("localhost","","","userdb");

$u_name=$_GET['user_name'];
$sql="select * from products where user_name='$u_name'";
$response=array();
$result=mysqli_query($conn,$sql);
while($row=mysqli_fetch_array($result)){
	array_push($response,array('id'=>$row['id'],'name'=>$row['description'],'price'=>$row['price'],'image_path'=>
$row['imagepath']));

}
echo json_encode($response);







?>