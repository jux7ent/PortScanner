package ru.egor;

import java.io.IOException;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

class IndexException extends Exception {
    IndexException(String message) {
        super(message);
    }
}

public class PortScanner {
    private String ipOrHost;
    private boolean statusIp;

    private static final int TIMEOUT = 100;

    private PortScanner() {}

    PortScanner(String ipOrHost) {
        statusIp = verifyIpOrHostname(ipOrHost);

        this.ipOrHost = ipOrHost;
    }

    private static boolean verifyIpOrHostname(String ip_or_hostname) {
        return ip_or_hostname.matches("^[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}$");
    }

    private boolean verifyPort(int port) {
        try {
            Socket socket = new Socket();

            if (statusIp) {
                socket.connect(new InetSocketAddress(ipOrHost, port), TIMEOUT);
            }
            else {
                socket.connect(new InetSocketAddress(InetAddress.getByName(ipOrHost), port), TIMEOUT);
            }
            socket.close();

            return true;
        } catch (IOException ex) {
            return false;
        }
    }

    void scan(int start, int end, PrintStream ps) throws IndexException, IOException {
        if (start < 0 || end > 65536 || start > end) {
            throw new IndexException(start + " " + end + "index exception");
        }

        boolean status = false;
        String result;

        for (int i = start; i <= end; ++i) {
            ps.println("PORT:" + i + " STATUS:" + (verifyPort(i)? "OPEN" : "CLOSED"));
        }
    }
}
