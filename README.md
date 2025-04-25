# Spring-Boot-app-Bajaj
🚀 Bajaj Finserv Health – Programming Challenge (Spring Boot Project)
This project is a Spring Boot application built for the Bajaj Finserv Health Hiring Challenge (April 25th–30th, 2025). The application is designed to interact with a remote API immediately on startup without requiring any manual HTTP trigger or REST controller.

🧠 Problem Statement
The app automatically:

Calls the /generateWebhook endpoint on startup.

Processes a given problem (based on the registration number).

Sends the processed output to a provided webhook using JWT authentication.

🔍 Assigned Problem (Question 1: Mutual Followers)
Given a list of users and the people they follow, the app identifies pairs of users who mutually follow each other, i.e., both follow each other back. Only direct 2-node cycles are considered and outputted in [minId, maxId] format.

📦 Features
Automatic API call at startup using RestTemplate.

JSON parsing and logic execution without any external trigger.

JWT-based authorization for secure webhook POST.

Retry mechanism (up to 4 times) for webhook delivery upon failure.

🛠️ Tech Stack
Java

Spring Boot

RestTemplate

Maven

🧪 How It Works
On app startup, a POST request is sent to generate a webhook.

The response contains:

Webhook URL

JWT Access Token

Problem-specific input data

The app processes the problem:

If the registration number ends in an odd digit → Mutual Followers logic (Question 1).

The solution is sent to the webhook using the access token.

📤 Submission Includes
✅ Full project code

✅ Final JAR output

✅ Raw downloadable JAR link via GitHub
