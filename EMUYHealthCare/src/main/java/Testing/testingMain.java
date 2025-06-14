package Testing;

import java.util.Scanner;

public class testingMain {

    public static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {

        UserConnectingtest user = new UserConnectingtest();

        user.ConnectToDatabase(user.getUserData());

        System.out.print("Enter Your Username : ");
        String username = sc.nextLine();
        System.out.print("Enter Your Password : ");
        String password = sc.nextLine();
        TestingDb1 testingDb1 = new TestingDb1(username, password);
        testingDb1.Login();

    }
}
