
package com.basicsecurity.SecureVault.models;

public class SecureVault {
    private String userName;
    private int hash;
    private String publicKey;
    private int salt;
    private String receiver;
    private String file;
    private int status;
    private int symmetrischeKey;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getHash() {
        return hash;
    }

    public void setHash(int hash) {
        this.hash = hash;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public int getSalt() {
        return salt;
    }

    public void setSalt(int salt) {
        this.salt = salt;
    }

    public java.lang.String getReceiver() {
        return receiver;
    }

    public void setReceiver(java.lang.String receiver) {
        this.receiver = receiver;
    }

    public java.lang.String getFile() {
        return file;
    }

    public void setFile(java.lang.String file) {
        this.file = file;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getSymmetrischeKey() {
        return symmetrischeKey;
    }

    public void setSymmetrischeKey(int symmetrischeKey) {
        this.symmetrischeKey = symmetrischeKey;
    }
}
