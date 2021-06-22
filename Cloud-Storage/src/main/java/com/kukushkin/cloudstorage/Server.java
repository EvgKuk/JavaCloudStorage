package com.kukushkin.cloudstorage;

import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    // TODO: 14.06.2021
    // организовать корректный вывод статуса

    // подумать почему так реализован цикл в ClientHandler -
    // ОТВЕТ - возможно, чтобы считать количество прочитанных байтов относительно размера буфера? //

    public Server() {
        ExecutorService service = Executors.newFixedThreadPool(4);
        try (ServerSocket server = new ServerSocket(4567)){
            System.out.println("Server started");
            while (true) {
                service.execute(new ClientHandler(server.accept()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Server();
    }
}

