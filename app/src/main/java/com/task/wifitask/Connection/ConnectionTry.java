package com.task.wifitask.Connection;

public interface ConnectionTry {
    boolean connectOpen();

    boolean connectWEP();

    boolean connectWPA();

    boolean tryConnectWiFi(String passwordTry);

}
