package API;

public class LoginAPI {
    public static String Username;
    public static String Password;

    public LoginAPI(String Username,String Password){
        this.Username = Username;
        this.Password = Password;
    }
    static {
        LoginAPI.Username = "admin";
        LoginAPI.Password = "admin";
    }

    public void CekValue(){
        System.out.println("Username : " + Username);
        System.out.println("Password : " + Password);
    }


}
