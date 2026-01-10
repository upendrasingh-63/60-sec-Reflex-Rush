import Score from "../models/Score.js";

export async function submitScore(req, res) {
    try {
        const { score, accuracy } = req.body;
        console.log(req.body);

        if (score === undefined || accuracy === undefined) {
            return res.status(400).json({ message: "Invalid data" });
        }

        const newScore = new Score({ score, accuracy });
        await newScore.save();

        res.status(201).json({
            message: "Score submitted successfully",
        });
    } catch (error) {
        res.status(500).json({ message: "Server error" });
    }
}
