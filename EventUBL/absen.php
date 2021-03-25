<?php
	include_once "koneksi.php";

	class pemesanan{}
	
	$kdacara = $_POST["kdacara"];
	$nipdosen = $_POST["nipdosen"];
	
	if (!empty($kdacara && $nipdosen)){
 		$num_rows = mysqli_num_rows(mysqli_query($con, "SELECT * FROM presensi WHERE id_acara='".$kdacara."' AND nip='".$nipdosen."'"));
		if ($num_rows == 0 ){
	 		$query = mysqli_query($con, "INSERT INTO presensi (id_presensi, id_acara, nip) VALUES(0,'".$kdacara."','".$nipdosen."')");
			if ($query){
	 			$response = new pemesanan();
	 			$response->success = 1;
	 			$response->message = "Absen berhasil";
	 			die(json_encode($response));
	 		} else {
	 			$response = new pemesanan();
	 			$response->success = 0;
	 			$response->message = "Gagal Absen";
	 			die(json_encode($response));
	 		}
	 	} else {
	 		$response = new pemesanan();
	 		$response->success = 0;
			$response->message = "Maaf anda sudah melaksanakan absen";
	 		die(json_encode($response));
	 	}
	} else {
	 	$response = new pemesanan();
	 	$response->success = 0;
		$response->message = "Maaf anda sudah melaksanakan absen";
 		die(json_encode($response));
	}
	mysqli_close($con);
?>