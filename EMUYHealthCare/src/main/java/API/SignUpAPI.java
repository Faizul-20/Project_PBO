package API;

public class SignUpAPI {
    public static String Username;
    public static String Password;
    public static String UlangTahun;
    public static int TinggiBadan;
    public static int BeratBadan;

    public SignUpAPI(String Username,String Password,String UlangTahun,String TinggiBadan,String BeratBadan){
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

}
