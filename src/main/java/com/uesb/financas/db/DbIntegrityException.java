package com.uesb.financas.db;

public class DbIntegrityException extends RuntimeException {
    private static final long serialVersionUUID = 1L;

    public DbIntegrityException(String msg) {
        super(msg);
    }

}
