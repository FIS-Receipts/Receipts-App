package com.fis.receiptsapp.controllers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EncryptTest {

    @Test
    void testSameStringHashesAreEqual() {
        boolean equal = Encrypt.sha256("test123").equals(Encrypt.sha256("test123"));
        assertTrue(equal);
    }

    @Test
    void testDifferentStringHashesAreDifferent() {
        boolean equal = Encrypt.sha256("test123").equals(Encrypt.sha256("anaaremere"));
        assertFalse(equal);
    }
}