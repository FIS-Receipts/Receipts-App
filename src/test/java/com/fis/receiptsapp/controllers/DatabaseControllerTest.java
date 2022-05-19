package com.fis.receiptsapp.controllers;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseControllerTest {

    @Test
    void connectionTest() {
        DatabaseController db = DatabaseController.getInstance();
        assertNotNull(db.getConnection());
    }
}