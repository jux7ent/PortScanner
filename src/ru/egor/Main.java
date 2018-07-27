package ru.egor;

public class Main {
    public static void main(String[] args) throws Exception {
        if (args.length == 3) {
            PortScanner portScanner = new PortScanner(args[0]);

            portScanner.scan(Integer.parseInt(args[1]), Integer.parseInt(args[2]), System.out);
        }
        else {
            System.err.println("args error");
        }
    }
}
