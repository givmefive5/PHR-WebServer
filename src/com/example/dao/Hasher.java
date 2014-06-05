package com.example.dao;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.example.exceptions.DatabaseAccessException;

public class Hasher {

	public static String hashPassword(String password) throws DatabaseAccessException{
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            byte[] bytes = md.digest();
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			throw new DatabaseAccessException("Hashing failed", e);
		}
	}
}
