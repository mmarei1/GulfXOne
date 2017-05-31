<?php
    require("password.php");
	// edit these to contain host details
    $con = mysqli_connect("localhost", "root", "", "data");
    
	// username retrieved using POST request based on user's selected login method
    $ulogin = $_POST["ulogin"];
	$ulogin_type = $_POST["ulogin_type"];
    $password = $_POST["password"];

	// Check for the ulogin_type and execute corresponding function
	if($ulogin_type == "email")
	{
		$statement = mysqli_prepare($con, "SELECT * FROM user WHERE email = ?");
	} else if ($ulogin_type == "username")
	{
		$statement = mysqli_prepare($con, "SELECT * FROM user WHERE username = ?");
	} // check if fingerprint and fingerprint is non-null 
	else if ($ulogin_type == "fingerprint" && $ulogin == not-null)
	{
		$statement = mysqli_prepare($con, "SELECT * FROM user WHERE fingerprint = ?");
	}
	
	mysqli_stmt_bind_param($statement, "s", $ulogin);
	mysqli_stmt_execute($statement);
	mysqli_store_result($statement);
	mysqli_stmt_bind_result($statement, $colUser_id, $colUserame, $colPassword, $colEmail, $col);
	
    $response = array();
    $response["success"] = false;  
    
	// encode successful access as a JSON object
	// as long as the fetch returns true
    while(mysqli_stmt_fetch($statement)){
        if (password_verify($password, $colPassword)) {
            $response["success"] = true;  
            $response["username"] = $colName;
            $response["email"] = $colEmail;
        }
    }
    echo json_encode($response);
?>