package chatBotEngine;

import DataBaseController.UserConnecting;

public class Main {

    public static void main(String[] args) {
        UserConnecting connecting = new UserConnecting();
        connecting.ConnectToDatabase(connecting.getPENYAKIT_DATA());




    }
}
