package com.capgemini.fs.coindashboard.encryptionService;

import org.springframework.beans.factory.annotation.Value;
import javax.crypto.*;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;


public class AESService {
  private SecretKey key;
  private final String TRANSFORMATION = "AES/GCM/NoPadding";
  private final int TAG_LENGTH = 128;
  private byte[] IV;
  public AESService(String secret_key,String iv) {
    key = new SecretKeySpec(decode(secret_key),"AES");
    this.IV = decode(iv);
  }

  public String decrypt(String encryptedMessage) throws NoSuchPaddingException,
      NoSuchAlgorithmException,
      InvalidKeyException,
      InvalidAlgorithmParameterException,
      IllegalBlockSizeException,
      BadPaddingException{
    byte[] messageInBytes = decode(encryptedMessage);
    Cipher decryptionCipher = Cipher.getInstance(TRANSFORMATION);
    GCMParameterSpec spec = new GCMParameterSpec(TAG_LENGTH,IV);
    decryptionCipher.init(Cipher.DECRYPT_MODE,key,spec);
    byte[] decryptedBytes = decryptionCipher.doFinal(messageInBytes);
    return new String(decryptedBytes);
  }


  private byte[] decode(String data){ return Base64.getDecoder().decode(data); }
}
