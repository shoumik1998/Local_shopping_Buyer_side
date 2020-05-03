<?php
$conn=mysqli_connect("localhost","","","userdb");

if(isset($_GET['region'])){
	$region=$_GET['region'];
	$sql="select country ,district, subdistrict,region from login_info where region like '$region%'";
	$result=mysqli_query($conn,$sql);
	$response=array();
	while ($row=mysqli_fetch_assoc($result)) {
			array_push($response,array('country'=>$row['country'],'district'=>$row['district'],
				'subdistrict'=>$row['subdistrict'],'region'=>$row['region']));
	}
	echo json_encode($response);
}




?>