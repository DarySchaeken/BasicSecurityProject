package be.basicsecurity.securevault.models;

public class SecureVault {
    private String receiver;
    private String file;
    private int status;
    private int symmetrischeKey;

    public String getReceiver() {
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
