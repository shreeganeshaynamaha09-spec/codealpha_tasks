document.addEventListener("DOMContentLoaded", () => {
    const chatForm = document.getElementById("chat-form");
    const userInput = document.getElementById("user-input");
    const chatWindow = document.getElementById("chat-window");

    chatForm.addEventListener("submit", async (event) => {
        event.preventDefault();
        const userMessage = userInput.value.trim();
        if (userMessage === "") return;

        appendMessage(userMessage, "user");
        userInput.value = "";
        showTypingIndicator();

        try {
            const response = await fetch("/chat", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ message: userMessage }),
            });
            if (!response.ok) throw new Error("Network response was not ok");

            const data = await response.json();
            const botReply = data.reply;

            removeTypingIndicator();
            appendMessage(botReply, "bot");

        } catch (error) {
            removeTypingIndicator();
            console.error("Error:", error);
            appendMessage("Sorry, an error occurred. Please try again.", "bot");
        }
    });

    function appendMessage(message, sender) {
        const messageRow = document.createElement("div");
        messageRow.className = `message-row ${sender}-message`;

        let messageHtml = '';

        if (sender === 'bot') {
            messageHtml = `
                <img src="images/bot-avatar.png" alt="Bot" class="avatar">
                <div class="chat-message">
                    <div class="message-content">
                        ${marked.parse(message, { gfm: true })}
                    </div>
                </div>
            `;
        } else {
            // User message (no avatar for this design)
            messageHtml = `
                <div class="chat-message">
                    <div class="message-content">
                        <p>${message}</p>
                    </div>
                </div>
            `;
        }

        messageRow.innerHTML = messageHtml;
        chatWindow.appendChild(messageRow);
        chatWindow.scrollTo({ top: chatWindow.scrollHeight, behavior: 'smooth' });
    }

    function showTypingIndicator() {
        const typingElement = document.createElement("div");
        typingElement.id = "typing-indicator";
        typingElement.className = "message-row bot-message";
        typingElement.innerHTML = `
            <img src="images/bot-avatar.png" alt="Bot" class="avatar">
            <div class="chat-message typing-indicator">
                <span class="dot"></span><span class="dot"></span><span class="dot"></span>
            </div>
        `;
        chatWindow.appendChild(typingElement);
        chatWindow.scrollTo({ top: chatWindow.scrollHeight, behavior: 'smooth' });
    }

    function removeTypingIndicator() {
        const typingElement = document.getElementById("typing-indicator");
        if (typingElement) {
            typingElement.remove();
        }
    }
});