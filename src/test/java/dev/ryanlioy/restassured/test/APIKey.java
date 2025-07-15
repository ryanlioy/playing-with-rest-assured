package dev.ryanlioy.restassured.test;

import io.restassured.http.Header;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class APIKey {
    public static Header getAuthHeader() {
        String key;
        try {
            File file = new File("src/test/resources/key.txt");
            key = new Scanner(file).nextLine();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e.getMessage());
        }
        return new Header("x-api-key", key);
    }
}
