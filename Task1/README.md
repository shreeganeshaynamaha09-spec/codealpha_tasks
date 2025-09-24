# AI Chatbot Project - User Manual

## 1. Introduction 🤖
This document provides a complete guide to setting up, configuring, and using the AI Chatbot application. This project is a full-stack, interactive web application featuring a Java-based backend powered by the Spring Boot framework and a modern, responsive frontend.

The chatbot leverages Google's Gemini AI for intelligent, generative responses and is capable of using external tools to fetch real-time information.

---

## 2. Core Features
The chatbot comes with a rich set of features designed to provide an impressive and interactive user experience.

* **Intelligent Conversation:** Utilizes the powerful Google Gemini LLM to understand user queries and generate natural, context-aware answers on a vast range of topics.
* **Live Weather Tool:** The chatbot can detect when a user is asking for the weather, call an external OpenWeatherMap API to get live data, and formulate a helpful, real-time response.
* **Impressive User Interface:** The frontend is modern, professional, and user-friendly, featuring:
    * Distinct message bubbles for the user and the AI assistant.
    * An animated "typing indicator" while the bot is thinking.
    * Beautiful formatting for AI responses, including headings, lists, and bold text, powered by a Markdown renderer.
    * A friendly bot avatar to enhance the conversational feel.
* **Real-Time Interaction:** A robust client-server architecture allows for seamless, real-time messaging between the user's browser and the Java backend.

---

## 3. System Prerequisites ⚙️
Before you begin, ensure you have the following software installed on your local machine:

* **Java Development Kit (JDK):** Version 17 or newer.
* **Apache Maven:** A build automation tool used to compile and manage the project's dependencies.
* **Git:** A version control system for downloading the source code.
* **A modern web browser:** Such as Google Chrome, Firefox, or Microsoft Edge.

---

## 4. Installation & Setup
Follow these steps to download and build the project.

1.  **Download the Code**
    Open your terminal or command prompt and clone the project repository using Git.
    ```bash
    git clone [https://github.com/your-username/your-chatbot-project.git](https://github.com/your-username/your-chatbot-project.git)
    ```

2.  **Navigate to the Project Directory**
    Change your directory to the project folder that was just downloaded.
    ```bash
    cd your-chatbot-project
    ```

3.  **Build the Project**
    Use the Maven Wrapper to compile the code and download all necessary dependencies. This may take a few minutes the first time.
    * On Windows (PowerShell):
        ```powershell
        .\mvnw clean install
        ```
    * On Mac/Linux:
        ```bash
        ./mvnw clean install
        ```
    You should see a `BUILD SUCCESS` message upon completion.

---

## 5. Configuration (Important!)
The chatbot requires API keys to connect to the AI and weather services.

1.  Navigate to the `src/main/resources/` folder.
2.  Open the `application.properties` file in a text editor.
3.  You will need to add your personal API keys. Replace the placeholder text with your actual keys.
    * **Gemini API Key:** Get this from [Google AI Studio](https://aistudio.google.com/).
    * **OpenWeatherMap API Key:** Get this from [OpenWeatherMap](https://openweathermap.org/api).

    ```properties
    spring.application.name=demo
    gemini.api.key=PASTE_YOUR_GEMINI_API_KEY_HERE
    weather.api.key=PASTE_YOUR_OPENWEATHERMAP_API_KEY_HERE
    ```
4.  Save and close the file.

---

## 6. Running the Application 🚀
After the project is built and configured, you can start the server.

1.  Make sure you are still in the project's root directory in your terminal.
2.  Run the following Maven command:
    * On Windows (PowerShell):
        ```powershell
        .\mvnw spring-boot:run
        ```
    * On Mac/Linux:
        ```bash
        ./mvnw spring-boot:run
        ```
3.  The terminal will show a log, and when the application is ready, you will see a line that says `Tomcat started on port 8080 (http)`.

---

## 7. Using the Chatbot
1.  Open your web browser.
2.  Navigate to the following address: **`http://localhost:8080`**
3.  The chat interface will load. You can start the conversation by typing a message in the input box at the bottom and pressing "Send" or Enter.

---

## 8. Troubleshooting 💡

* **Problem:** The application fails to start with an error message: `Port 8080 was already in use`.
    * **Solution:** Another application on your computer is using port 8080. If you have an old instance of the chatbot running, stop it from your IDE or terminal.

* **Problem:** The bot's avatar image is broken.
    * **Solution:** Make sure you have placed an image file named **`bot-avatar.png`** inside the **`src/main/resources/static/images/`** directory.

* **Problem:** The chat interface looks unstyled or doesn't work correctly.
    * **Solution:** Your browser may have cached old files. Perform a **hard refresh** by pressing `Ctrl+Shift+R` (Windows/Linux) or `Cmd+Shift+R` (Mac).
