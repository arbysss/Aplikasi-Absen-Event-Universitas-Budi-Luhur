	<?php
	include_once "koneksi.php";

	 class usr{}
	 $nip = $_POST["nip"];
	 $nama = $_POST["nama"];
	 $jk_dosen = $_POST["jk_dosen"];
	 $email = $_POST["email"];
	 $no_telp = $_POST["no_telp"];
	 $kata_sandi = $_POST["kata_sandi"];
	 
	 if ((empty($nip))) {
	 	$response = new usr();
	 	$response->success = 0;
	 	$response->message = "Kolom nip tidak boleh kosong";
	 	die(json_encode($response));
	 } else if ((empty($nama))) {
	 	$response = new usr();
	 	$response->success = 0;
	 	$response->message = "Kolom nama tidak boleh kosong";
	 	die(json_encode($response));
	 } else if ((empty($jk_dosen))) {
	 	$response = new usr();
	 	$response->success = 0;
	 	$response->message = "Kolom jenis kelamin tidak boleh kosong";
	 	die(json_encode($response));
	 } else if ((empty($email))) {
	 	$response = new usr();
	 	$response->success = 0;
	 	$response->message = "Kolom Email tidak boleh kosong";
	 	die(json_encode($response));
	} else if ((empty($no_telp))) {
	 	$response = new usr();
	 	$response->success = 0;
	 	$response->message = "Kolom nomor telephone tidak boleh kosong";
	 	die(json_encode($response));
	 }else if ((empty($kata_sandi))) {
	 	$response = new usr();
	 	$response->success = 0;
	 	$response->message = "Kolom password tidak boleh kosong";
	 	die(json_encode($response));
	 } else {
		 if (!empty($nip)){
		 	$num_rows = mysqli_num_rows(mysqli_query($con, "SELECT * FROM dosen WHERE nip='".$nip."'"));

		 	if ($num_rows == 0){
		 		$query = mysqli_query($con, "INSERT INTO dosen (nip, nama, jk_dosen, email, no_telp, kata_sandi) VALUES('".$nip."','".$nama."', '".$jk_dosen."', '".$email."','".$telp."', '".$kata_sandi."')");

		 		if ($query){
		 			$response = new usr();
		 			$response->success = 1;
		 			$response->message = "Register berhasil, silahkan login.";
		 			die(json_encode($response));

		 		} else {
		 			$response = new usr();
		 			$response->success = 0;
		 			$response->message = "Gagal Mendaftar";
		 			die(json_encode($response));
		 		}
		 	} else {
		 		$response = new usr();
		 		$response->success = 0;
				$response->message = "Nip sudah ada";
		 		die(json_encode($response));
		 	}
		 }
	 }
	 mysqli_close($con);
?>