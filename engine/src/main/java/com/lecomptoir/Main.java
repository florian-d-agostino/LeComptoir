package com.lecomptoir;

import com.lecomptoir.services.Cart;
import com.lecomptoir.services.CartLine;
import com.lecomptoir.services.Catalog;
import com.lecomptoir.services.Checkout;
import com.lecomptoir.services.discount.LoyaltyCard;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

public class Main {

    public static String runCheckoutDemo() {
        LoyaltyCard card = new LoyaltyCard(150);

        Cart cart = new Cart();
        cart.addLine(new CartLine(Catalog.BAGUETTE, 2));
        cart.addLine(new CartLine(Catalog.COCA, 3));
        cart.addLine(new CartLine(Catalog.STEAK, 1));
        cart.addLine(new CartLine(Catalog.BROOM, 1));

        Checkout checkout = new Checkout();
        return checkout.generateReceipt(cart, card);
    }

    public static void main(String[] args) throws IOException {
        int port = 8080;
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);

        server.createContext("/receipt", (HttpExchange exchange) -> {
            // Activer CORS pour permettre les requêtes depuis un navigateur ou client distant
            exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
            exchange.getResponseHeaders().set("Content-Type", "text/plain; charset=UTF-8");

            if ("GET".equalsIgnoreCase(exchange.getRequestMethod())) {
                String receipt = runCheckoutDemo();
                byte[] responseBytes = receipt.getBytes(StandardCharsets.UTF_8);

                exchange.sendResponseHeaders(200, responseBytes.length);
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(responseBytes);
                }
            } else {
                exchange.sendResponseHeaders(405, -1); // Method Not Allowed
            }
        });

        server.start();
        System.out.println("Serveur Java démarré sur http://localhost:" + port + "/receipt");
    }
}