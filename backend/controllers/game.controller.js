import Score from "../models/Score.js";

export const submitScore = async (req, res) => {
  try {
    const { username, score, accuracy } = req.body;
    console.log(req.body)

    if (!username || score == null || accuracy == null) {
      return res.status(400).json({ message: "Invalid data" });
    }

    const newScore = new Score({
      username,
      score,
      accuracy
    });

    await newScore.save();

    res.status(201).json({ message: "Score submitted successfully" });
  } catch (error) {
    res.status(500).json({ message: "Server error" });
  }
};

