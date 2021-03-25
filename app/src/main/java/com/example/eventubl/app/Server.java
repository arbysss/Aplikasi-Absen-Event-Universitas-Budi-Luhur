package com.example.eventubl.app;

public class Server {
    public static final String URL = "http://192.168.1.104/EventUBL/";
    public static final String URL_GET_ALL = "http://192.168.1.104/EventUBL/tampilSemuaAcara.php";
    public static final String URL_GET_ACR = "http://192.168.1.104/EventUBL/tampilAcara.php?id_acara=";

    //JSON Tags
    public static final String TAG_JSON_ARRAY="result";
    public static final String TAG_KDACARA = "kd";
    public static final String TAG_NMACARA = "nm";
    public static final String TAG_ACARATGL = "tgl";
    public static final String TAG_ACARAJAM = "jam";
    public static final String TAG_LOKASI = "lokasi";
    public static final String TAG_NIP = "nip";


    //acr itu singkatan dari acara
    public static final String DSN_NIP = "dsn_nip";
    public static final String ACR_KD = "acr_kd";

}
