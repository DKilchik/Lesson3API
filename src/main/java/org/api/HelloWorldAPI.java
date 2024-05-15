package org.api;

import static spark.Spark.*;

public class HelloWorldAPI {
    public static void main(String[] args) {
        // Set port (default is 4567)
        //port(8080);

        // Define a route for the "Hello, World!" message
        get("/hello", (req, res) -> "Hello, World!");
    }
}
