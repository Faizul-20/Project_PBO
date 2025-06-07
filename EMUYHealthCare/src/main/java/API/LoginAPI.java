package API;

import Controller.SceneController;
import DataBaseController.UserConnecting;

public class LoginAPI {
    public String Username;
    public String Password;
    UserConnecting userConnecting = new UserConnecting();
    SceneController sceneController = new SceneController();

    public LoginAPI(String Username,String Password){
        this.Username = Username;
        this.Password = Password;
    }

    public void CekValue(){
        System.out.println("Username : " + Username);
        System.out.println("Password : " + Password);
    }

    public void Login(){
        if (userConnecting.SignIn(Username,Password)){
            System.out.println("Login Successful");
            Username = null;
            Password = null;
            sceneController.SceneChange(sceneController.getDASHBOARD_LINK());
        }else {
            System.out.println("Login Failed");
            Username = null;
            Password = null;
        }
    }



}
