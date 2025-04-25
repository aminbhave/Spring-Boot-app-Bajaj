# 🏥 Bajaj Finserv Health – Spring Boot Challenge

A Spring Boot application developed for the Bajaj Finserv Health Hiring Challenge (April 2025). The app runs logic automatically on startup—no manual trigger required.

## 🚀 Features

- Auto API call to `/generateWebhook` on startup
- Solves the assigned problem (Mutual Followers or Nth-Level Followers)
- Posts result to a secured webhook using JWT Authorization
- Retry mechanism on webhook failure (up to 4 attempts)
- No REST controller or manual endpoints

## 🔍 Problem Summary

### 📌 Question 1 – Mutual Followers

Identify mutual follow pairs (2-node cycles) in the format `[minId, maxId]`.

### 📌 Question 2 – Nth-Level Followers

Given a user ID and level `n`, return all user IDs exactly `n` levels away in the follow graph.

## 🛠️ Tech Stack

- Java 17+
- Spring Boot
- Maven
- RestTemplate

## 🧪 How to Run

1. Clone the repo  
   ```bash
   git clone https://github.com/your-username/your-repo-name.git
