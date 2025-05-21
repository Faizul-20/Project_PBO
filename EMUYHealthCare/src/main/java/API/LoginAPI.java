package API;

public class LoginAPI {
    public static String Username;
    public static String Password;

    public LoginAPI(String Username,String Password){
        this.Username = Username;
        this.Password = Password;
    }

    public void CekValue(){
        System.out.println("Username : " + Username);
        System.out.println("Password : " + Password);
    }
}
