<?php
    require("password.php");
    $connect = mysqli_connect("localhost", "id1792515_user", "aaa12345", "id1792515_data");
    
	$email = $_POST["email"];
    $username = $_POST["username"];
    $password = $_POST["password"];
	$gloc = $_POST["gloc"];
	$last_ip_addr = $_POST["last_ip_addr"];
	$phone_number = $_POST["phone_number"];
	$phone_type = $_POST["phone_type"];
	$serial_number = $_POST["serial_number"];
	
	function updateUser() {
        global $connect,  $email, $username, $password;
        $passwordHash = password_hash($password, PASSWORD_DEFAULT);
        $statement = mysqli_prepare($connect, "SELECT * FROM user WHERE username = ?");
        mysqli_stmt_bind_param($statement, "siss", $username);
        mysqli_stmt_execute($statement);
        mysqli_stmt_close($statement);     
    }
    
    $response = array();
    $response["success"] = false;  
    if (usernameAvailable()){
        registerUser();
        $response["success"] = true;  
    }
    
    echo json_encode($response);
?>