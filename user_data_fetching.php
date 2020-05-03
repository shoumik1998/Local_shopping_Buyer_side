<?php

$conn=mysqli_connect("localhost","","","userdb");

$page_number=$_GET['page_num'];
if($page_number>0){
$sql="select * from products join login_info on products.user_name=login_info.user_name order by rand() limit 10";
$response=array();
$result=mysqli_query($conn,$sql);
while($row=mysqli_fetch_array($result)){
	array_push($response,array('shop_name'=>$row['name'],'name'=>$row['description'],'price'=>$row['price'],'image_path'=>
$row['imagepath']));

} 
echo json_encode($response);
}







?>