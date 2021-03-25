<?php
	include_once "koneksi.php";

	class usr{}
	
	$nip = $_POST["nip"];
	$kata_sandi = $_POST["kata_sandi"];
	
	if ((empty($nip)) || (empty($kata_sandi))) { 
	$response = new usr();
	 	$response->success = 0;
	 	$response->message = "Kolom tidak boleh kosong"; 
	 	die(json_encode($response));
	 }
	
	 $query = mysqli_query($con, "SELECT * FROM dosen WHERE nip='$nip' AND kata_sandi='$kata_sandi'");
	
	 $row = mysqli_fetch_array($query);
	
	if (!empty($row)){
		$response = new usr();
		$response->success = 1;
		$response->message = "Selamat datang ".$row['nip'];
		$response->nip = $row['nip'];
		die(json_encode($response));
		
	} else { 
		$response = new usr();
		$response->success = 0;
		$response->message = "NIP atau password salah";
		die(json_encode($response));
	}
	
	mysqli_close($con);


?>