package org.api;

import com.google.gson.Gson;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static spark.Spark.*;

public class UserService {
    public static void main(String[] args) {
        // Set port (default is 4567)
        port(8080);

        // In-memory storage for users (for demonstration purposes)
        Map<Integer, User> users = new ConcurrentHashMap<>();
        AtomicInteger idCounter = new AtomicInteger();

        // Create a new user
        post("/users", (req, res) -> {
            Gson gson = new Gson();
            User user = gson.fromJson(req.body(), User.class);
            int id = idCounter.incrementAndGet();
            user.setId(id);
            users.put(id, user);
            res.status(201);
            return gson.toJson(user);
        });

        // Get all users
        get("/users", (req, res) -> {
            return new Gson().toJson(users.values());
        });

        // Get user by ID
        get("/users/:id", (req, res) -> {
            int id = Integer.parseInt(req.params("id"));
            User user = users.get(id);
            if (user != null) {
                return new Gson().toJson(user);
            } else {
                res.status(404);
                return "User not found";
            }
        });

        // Update user by ID
        put("/users/:id", (req, res) -> {
            int id = Integer.parseInt(req.params("id"));
            User user = users.get(id);
            if (user != null) {
                Gson gson = new Gson();
                User updatedUser = gson.fromJson(req.body(), User.class);
                user.setName(updatedUser.getName());
                user.setEmail(updatedUser.getEmail());
                return gson.toJson(user);
            } else {
                res.status(404);
                return "User not found";
            }
        });

        // Delete user by ID
        delete("/users/:id", (req, res) -> {
            int id = Integer.parseInt(req.params("id"));
            User removedUser = users.remove(id);
            if (removedUser != null) {
                return "User deleted: " + removedUser.getName();
            } else {
                res.status(404);
                return "User not found";
            }
        });


    }
}
