import { Router } from "express";
const router = Router();
import { getTopScores } from "../controllers/leaderboard.controller.js";

router.get("/top", getTopScores);

export default router;
