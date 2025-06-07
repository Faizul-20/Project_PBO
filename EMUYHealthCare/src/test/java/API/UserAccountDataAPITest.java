package API;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserAccountDataAPITest {

    @Test
    void getDataUser() {
        UserAccountDataAPI UserAccountDataAPITest = new UserAccountDataAPI();

        UserAccountDataAPITest.testConnection();

    }
}