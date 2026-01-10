#[Documentation](https://docs.google.com/document/d/196dSRYDqDp7KI-dxTzNO9MENU4scCMAl/edit?usp=sharing&ouid=105379724275299374909&rtpof=true&sd=true)

# 🎮 Reflex Rush – Gamified Reflex-Based Mobile Game

Reflex Rush is a fast-paced, reflex-based mobile game built using **Android Jetpack Compose** with a **Node.js + MongoDB backend**.  
The game challenges users to react quickly within a **60-second time limit**, rewards accuracy and speed, and displays rankings on a **Global Leaderboard**.

This project demonstrates a **complete end-to-end system**, including mobile frontend, backend APIs, database integration, and documentation — designed to be easily integrated into a larger rewards platform such as **Sharp Rewards**.

---

## 🚀 What This Project Does

- ⏱️ 60-second reflex-based gameplay
- 🎯 Randomly spawning correct & incorrect objects
- 📊 Real-time score and accuracy calculation
- 👤 Auto-generated unique username per user (no authentication)
- ☁️ Automatic score submission to backend
- 🏆 Global leaderboard with usernames
- 🔄 Smooth navigation and user feedback
- 🧱 Clean MVVM architecture

---

## 🧠 Tech Stack

### 📱 Android App (Main Branch)
- **Language:** Kotlin
- **UI:** Jetpack Compose
- **Architecture:** MVVM
- **State Management:** StateFlow
- **Navigation:** Navigation Compose
- **Networking:** Retrofit

### 🌐 Backend (backend Branch / Folder)
- **Runtime:** Node.js
- **Framework:** Express.js
- **Database:** MongoDB
- **ODM:** Mongoose
- **API Style:** REST (JSON)

---

## 🏗️ Project Structure

```text
├── main/              # Android App (Jetpack Compose)
│   ├── game/
│   ├── leaderboard/
│   ├── navigation/
│   └── network/
│
├── backend/           # Node.js Backend
│   ├── controllers/
│   ├── routes/
│   ├── models/
│   └── config/
│
└── README.md
```
###How to run this project
- app is on main branch
- backend in on other branch named backend
- download and setup this project and then run
