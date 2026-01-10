import { Schema, model } from "mongoose";

const scoreSchema = new Schema(
    {
        username: {
      type: String,
      required: true
    },
        score: {
            type: Number,
            required: true,
        },
        accuracy: {
            type: Number,
            required: true,
        },
    },
    { timestamps: true }
);

export default model("Score", scoreSchema);
