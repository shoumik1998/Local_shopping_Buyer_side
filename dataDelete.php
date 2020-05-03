<?php
$conn=mysqli_connect("localhost","","","userdb");

$id=array();
$id=$_POST['id'];
foreach ($id as $value) {
	$sql="delete from products where id='".$value."'";
	mysqli_query($conn,$sql);
	
}
if(mysqli_query($conn,$sql)){
		$status="OK";
	}else{
		$status="Error";
	}
echo json_encode(array("response"=>$status));
mysqli_close($conn);



?>