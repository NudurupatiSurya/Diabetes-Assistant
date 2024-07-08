# Diabetes Assistant

Diabetes Assistant is an Android app that assists users in determining the safety of consuming various foods, especially for sugar patients. Users can speak the name of a food item, and the app will consult ChatGPT for dietary advice and read the response aloud. Future updates will support other medical conditions and languages, ensuring a comprehensive dietary guidance tool that complies with internationalization.

## Key Features

1. **Voice Recognition**: Uses Android's built-in speech recognizer to capture user input.
2. **Text-to-Speech**: Reads the responses using Android's TextToSpeech API.
3. **OpenAI Integration**: Integrates with OpenAI's GPT-3.5-turbo to advise users on whether they can eat the inputted food or not.
4. **User-Friendly UI**: Clean and intuitive user interface with a responsive mic button and clear display of transcriptions and responses.

## Demo
### Video
[![Watch the video](https://img.youtube.com/vi/kqU3Yw8ig6Y/maxresdefault.jpg)](https://youtu.be/kqU3Yw8ig6Y)

## Code Structure

This project follows the Model-View-ViewModel (MVVM) architecture to ensure a clean and maintainable codebase. Here’s an overview of the main components:

### Model:
- **ChatGPTResponseModel**: Represents the response from OpenAI's ChatGPT.

### Network:
- **APIService**: Defines the Retrofit API service for interacting with OpenAI APIs.
- **RetrofitObject**: Provides the Retrofit instance.

### Repository:
- **ChatRepo**: Infers OpenAI's chat completions API with a special prompt and the word user spoke.

### UI:
- **Mic Component**: Displays a mic button and handles the recording state and animation.
- **AnswerBox**: Displays the response from ChatGPT and shows a circular indicator while making the API call.
- **SpeakBox**: Displays the transcribed text from the user's speech.
- **LoadingIndicator Component**: Displays a loading indicator while waiting for responses.

### ViewModel:
- **MainViewModel**: Manages data retrieval, processing, and UI state. Implements methods for handling voice recognition, audio transcription, ChatGPT responses, and user interactions.

**Note:** You need to create an API key from OpenAI and add it as `APIKEY` in the `ChatRepo` file to successfully run this project.

