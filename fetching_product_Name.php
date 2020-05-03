<?php

$conn=mysqli_connect("localhost","","","userdb");

$pro_name=$_GET['product_name'];
$sql="select description from products where description like '$pro_name%'";
$response=array();
$result=mysqli_query($conn,$sql);
while($row=mysqli_fetch_array($result)){
	array_push($response,array('name'=>$row['description']));

}
echo json_encode($response);


?>