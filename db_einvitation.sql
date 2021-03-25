# Host: localhost  (Version 5.5.5-10.4.8-MariaDB)
# Date: 2020-07-09 22:17:10
# Generator: MySQL-Front 6.1  (Build 1.26)


#
# Structure for table "admin"
#

DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `id_admin` int(11) NOT NULL AUTO_INCREMENT,
  `nama_admin` varchar(50) DEFAULT NULL,
  `username` varchar(25) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id_admin`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;

#
# Data for table "admin"
#

INSERT INTO `admin` VALUES (1,'Sekertariat 1 FTI','admin','admin'),(5,'Sekertariat 2 FTI','admin2','admin2'),(7,'Test Admin 123 TEST','123','123');

#
# Structure for table "dosen"
#

DROP TABLE IF EXISTS `dosen`;
CREATE TABLE `dosen` (
  `nip` char(6) NOT NULL DEFAULT '',
  `nama` varchar(50) NOT NULL DEFAULT '',
  `jk_dosen` enum('Laki-laki','Perempuan') NOT NULL DEFAULT 'Laki-laki',
  `email` varchar(75) NOT NULL DEFAULT '',
  `no_telp` varchar(15) NOT NULL DEFAULT '',
  `kata_sandi` varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (`nip`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

#
# Data for table "dosen"
#

INSERT INTO `dosen` VALUES ('111111','Lulu','Perempuan','lulu@test','111111','111111'),('123123','Test Peserta','Laki-laki','test@test','1234','123123'),('123456','Nama Dosen 1','Laki-laki','dosen@fti.com','1234','123'),('171250','arby','Laki-laki','arbysudewa12@gmail.com','','123456'),('222222','Lala','Perempuan','lala@test','123222','222222'),('333333','Lele','Laki-laki','lele@test','123333','333333'),('444444','Lolo','Laki-laki','lolo@test','123444','444444'),('55555','Lili','Perempuan','lili@test','123455','55555');

#
# Structure for table "jenis_acara"
#

DROP TABLE IF EXISTS `jenis_acara`;
CREATE TABLE `jenis_acara` (
  `id_jenis` int(11) NOT NULL AUTO_INCREMENT,
  `nama_jenis` varchar(50) DEFAULT NULL,
  `keterangan` text DEFAULT NULL,
  PRIMARY KEY (`id_jenis`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=latin1;

#
# Data for table "jenis_acara"
#

INSERT INTO `jenis_acara` VALUES (1,'Rapat Kerja FTI','Rapat kerja yang diadakan per semester oleh Fakultas Teknologi Informasi'),(10,'Sidang PKBM','Sidang PKBM tahunan dosen'),(11,'Seminar FTI','Seminar tahunan dosen'),(12,'Sidang Karya Ilmiah','Sidang karya tulis ilmiah tahunan dosen'),(13,'Test Jenis Id = 13','Id = 13');

#
# Structure for table "acara"
#

DROP TABLE IF EXISTS `acara`;
CREATE TABLE `acara` (
  `id_acara` int(11) NOT NULL AUTO_INCREMENT,
  `id_jenis` int(11) DEFAULT NULL,
  `nama_acara` varchar(50) DEFAULT NULL,
  `deskripsi_acara` text DEFAULT NULL,
  `tanggal_acara` date DEFAULT NULL,
  `waktu_acara` time DEFAULT NULL,
  `lokasi_acara` varchar(50) DEFAULT NULL,
  `banner` varchar(255) DEFAULT NULL,
  `materi` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id_acara`),
  KEY `id_jenis` (`id_jenis`),
  CONSTRAINT `acara_ibfk_1` FOREIGN KEY (`id_jenis`) REFERENCES `jenis_acara` (`id_jenis`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;

#
# Data for table "acara"
#

INSERT INTO `acara` VALUES (2,12,'Presentasi Genap 219/2020','Presentasi Karya Ilmiah Dosen Periode Genap, Tahun Ajaran 2019/2020.','2020-06-28','13:00:00','Ruang Teater (6.4.1)','1593345656_Jellyfish.jpg','1593338813_ERD-LRS.jpg'),(3,11,'Seminar Penyuluhan E-Learning','Seminar penyuluhan mengenai sistem e-learning baru genap 2019/2020.','2020-07-07','09:00:00','Labkom (7.4.x)','1593345685_Hydrangeas.jpg','1593338801_ERD'),(5,1,'Rapat Kerja FTI Genap 19/20','test Acara','2020-06-23','08:30:00','Auditorium','1593345696_Desert.jpg','1593340599_ERD-ERD-LRS.jpg'),(6,1,'Test Acara 123 Test 123 Nama Acara','test','2020-07-04','22:01:00','Test Loct','1593346752_Desert.jpg','1593345741_ERD-ERD.jpg'),(7,12,'Acara 123 Test 123 Acara Data','1212','2020-06-28','19:06:00','test','1593345831_Chrysanthemum.jpg','1593345831_index.php'),(8,12,'arby sidang','hehe','2020-07-09','22:10:00','auditorium','1594307412_splash.jpg','1594307412_splash.jpg');

#
# Structure for table "presensi"
#

DROP TABLE IF EXISTS `presensi`;
CREATE TABLE `presensi` (
  `id_presensi` int(11) NOT NULL AUTO_INCREMENT,
  `id_acara` int(11) DEFAULT NULL,
  `nip` char(6) DEFAULT NULL,
  `id_admin` int(11) DEFAULT NULL,
  PRIMARY KEY (`id_presensi`),
  KEY `id_acara` (`id_acara`),
  KEY `nip` (`nip`),
  KEY `id_admin` (`id_admin`),
  CONSTRAINT `presensi_ibfk_1` FOREIGN KEY (`id_acara`) REFERENCES `acara` (`id_acara`),
  CONSTRAINT `presensi_ibfk_2` FOREIGN KEY (`nip`) REFERENCES `dosen` (`nip`),
  CONSTRAINT `presensi_ibfk_3` FOREIGN KEY (`id_admin`) REFERENCES `admin` (`id_admin`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=latin1;

#
# Data for table "presensi"
#

INSERT INTO `presensi` VALUES (1,3,'111111',NULL),(2,3,'222222',NULL),(4,3,'333333',NULL),(5,3,'444444',NULL),(7,3,'55555',NULL),(8,2,'123123',7),(11,5,'111111',7),(12,5,'333333',7),(13,5,'123123',7),(14,5,'55555',NULL),(16,2,'444444',NULL),(17,2,'333333',NULL),(18,2,'111111',NULL),(21,2,'55555',NULL),(22,7,'55555',NULL),(23,7,'123123',NULL),(24,7,'111111',NULL),(25,2,'171250',NULL),(26,3,'171250',NULL),(27,7,'171250',NULL),(28,8,'171250',1);
