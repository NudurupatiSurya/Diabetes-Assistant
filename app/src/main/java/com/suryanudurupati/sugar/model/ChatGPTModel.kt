package com.suryanudurupati.sugar.model

data class ChatGPTRequest(
    val model: String,
    val messages: List<Message>,
)

data class ChatGPTResponse(
    val choices: List<Choice>
)

data class Choice(
    val message: Message
)

data class Message(
    val role: String,
    val content: String
)