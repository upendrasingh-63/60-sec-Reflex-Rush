import express from "express";
import cors from "cors";
import connectDB from "./config/db.js";

import gameRoutes from "./routes/game.routes.js";
import leaderboardRoutes from "./routes/leaderboard.routes.js";
import dotenv from "dotenv";
dotenv.config();


const app = express();
connectDB();

app.use(cors());
app.use(express.json());

app.get("/", (req, res) => {
    res.send("Reflex Rush Backend is running");
});

app.use("/game", gameRoutes);
app.use("/leaderboard", leaderboardRoutes);

const PORT = 5000;
app.listen(5000, "0.0.0.0", () => {
    console.log("Server running on port 5000");
});
