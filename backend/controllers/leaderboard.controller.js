import Score from "../models/Score.js";

export async function getTopScores(req, res) {
    try {
        const topScores = await Score.find()
            .sort({ score: -1 })
            .limit(10);

        const leaderboard = topScores.map((entry, index) => ({
            rank: index + 1,
            username: entry.username,
            score: entry.score,
            accuracy: entry.accuracy
        }));

        console.log(leaderboard);

        res.json(leaderboard);
    } catch (error) {
        res.status(500).json({ message: "Server error" });
    }
}
