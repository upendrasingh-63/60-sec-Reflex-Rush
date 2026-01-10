import { Router } from "express";
const router = Router();
import { submitScore } from "../controllers/game.controller.js";

router.post("/score", submitScore);

export default router;
