package lk.ijse.dep8.db;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class DBConnectionTest {

    @Test
    void getInstance() {
        assertNotNull(DBConnection.getInstance());
        assertEquals(DBConnection.getInstance(), DBConnection.getInstance());
    }

    @Test
    void getConnection() {
        assertNotNull(DBConnection.getInstance().getConnection());
        assertEquals(DBConnection.getInstance().getConnection(), DBConnection.getInstance().getConnection());
    }
}