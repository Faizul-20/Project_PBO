package API;

import DataBaseController.UserConnecting;

public class SignUpAPI {
    private String Username;
    private  String Password;
    private  String UlangTahun;
    private  int TinggiBadan;
    private  int BeratBadan;
    UserConnecting userConnecting = new UserConnecting();

    public SignUpAPI(String Username,String Password,String UlangTahun,String TinggiBadan,String BeratBadan) {
        this.Username = Username;
        this.Password = Password;
        this.UlangTahun = UlangTahun;
        this.TinggiBadan = Integer.parseInt(TinggiBadan);
        this.BeratBadan = Integer.parseInt(BeratBadan);

    }

    public void CekValue(){
        System.out.println("Username : " + Username);
        System.out.println("Password : " + Password);
        System.out.println("TTL      : " + UlangTahun);
        System.out.println("Tinggi   : " + TinggiBadan);
        System.out.println("Berat    :" + BeratBadan );
    }

    public void PostDataUserTodatabase(){
        userConnecting.InsertDataUser(Username,Password,TinggiBadan,BeratBadan,UlangTahun);
        Username = null;
        Password = null;
        TinggiBadan = 0;
        BeratBadan = 0;
        UlangTahun = null;
    }


}
