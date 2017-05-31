<?php
    require("password.php");
    $connect = mysqli_connect("localhost", "root", "", "data");
    
    $email = $_POST["email"];
    $username = $_POST["username"];
    $password = $_POST["password"];
	$fingerprint = $_POST["fingerprint"];
	$gloc = $_POST["gloc"];
	$ipaddr = $_POST["ipaddr"];
	$mPhoneNumber = $_POST["mPhoneNumber"];
	$mSerialNumber = $_POST["mSerialNumber"];
	$mModel = $_POST["mModel"];
	
     function registerUser() {
        global $connect,  $email, $username, $password, $fingerprint;
        $passwordHash = password_hash($password, PASSWORD_DEFAULT);
        $statement = mysqli_prepare($connect, "INSERT INTO user (email, username, password, fingerprint, gloc, ipaddr, mPhoneNumber, mSerialNumber, mModel) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
        mysqli_stmt_bind_param($statement, "siss", $name, $username, $passwordHash, $fingerprint, $gloc, $ipaddr, $mPhoneNumber, $mSerialNumber, $mModel);
        mysqli_stmt_execute($statement);
        mysqli_stmt_close($statement);     
    }
    function usernameAvailable() {
        global $connect, $username;
        $statement = mysqli_prepare($connect, "SELECT * FROM user WHERE username = ?"); 
        mysqli_stmt_bind_param($statement, "s", $username);
        mysqli_stmt_execute($statement);
        mysqli_stmt_store_result($statement);
        $count = mysqli_stmt_num_rows($statement);
        mysqli_stmt_close($statement); 
        if ($count < 1){
            return true; 
        }else {
            return false; 
        }
	}
	function emailValid() {
        global $connect, $email;
        $statement = mysqli_prepare($connect, "SELECT * FROM user WHERE email = ?"); 
        mysqli_stmt_bind_param($statement, "s", $email);
        mysqli_stmt_execute($statement);
        mysqli_stmt_store_result($statement);
        $count = mysqli_stmt_num_rows($statement);
        mysqli_stmt_close($statement); 
        if ($count < 1){
            return true; 
        }else {
            return false; 
        }
    }
    $response = array();
    $response["success"] = false;  
    if (usernameAvailable()){
        registerUser();
        $response["success"] = true;  
    }
    
    echo json_encode($response);
?>