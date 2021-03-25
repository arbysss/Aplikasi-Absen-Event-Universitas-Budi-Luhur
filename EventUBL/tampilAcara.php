<?php
	//Mendapatkan Nilai Dari Variable yang ingin ditampilkan
	$id_acara = $_GET['id_acara'];
	$nama_acara = $_GET['nama_acara'];
	$tanggal_acara = $_GET['tanggal_acara'];
	$waktu_acara = $_GET['waktu_acara'];
	$lokasi_acara = $_GET['lokasi_acara'];

	//Importing database
	require_once('koneksi.php');

	//Membuat SQL Query dengan pegawai yang ditentukan secara spesifik sesuai
	$sql = "SELECT * FROM acara where id_acara=$id_acara";

	//Mendapatkan Hasil
	$r = mysqli_query($con,$sql);

	//Memasukkan Hasil Kedalam Array
	$result = array();
	$row = mysqli_fetch_array($r);
	array_push($result,array(
			"kd"=>$row['id_acara'],
			"nm"=>$row['nama_acara'],
			"tgl"=>$row['tanggal_acara'],
			"jam"=>$row['waktu_acara'],
			"lokasi"=>$row['lokasi_acara']
		));

	//Menampilkan dalam format JSON
	echo json_encode(array('result'=>$result));

	mysqli_close($con);
?>
