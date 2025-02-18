# Unsplash Viewer

Unsplash Viewer is a simple Android app that displays high-quality images fetched from the Unsplash API. It includes user authentication features powered by Firebase Auth and allows users to save image data to Firebase Firestore by double-tapping images.    

## Tech Stack
- Android: Java/Kotlin
- Firebase
  - Firebase Authentication (for login and signup)
  - Firebase Firestore (for saving image data)
- Unsplash API: For fetching images


## Setup Instructions
  ### 1. Clone the repository
    git clone https://github.com/aljazw/unsplash_viewer.git

  ### 2. Add Firebase to your Android project
  Follow the instructions from the [Firebase documentation](https://firebase.google.com/docs/android/setup) to add Firebase to your project.

  ### 3. Set up Firebase Authentication and Firestore
  - Go to Firebase Console and enable Firebase Authentication.
  - Set up Firestore Database to store image data.

  ### 4. Add Unsplash API Key
  - Sign up at [Unsplash Developer](https://unsplash.com/developers) to get your API key.
  - Add this line of code to local.properties file:  
  ``` UNSPLASH_API_KEY="your_api_key_here" ```

  ### 5. Run the app
  - Open the project in Android Studio
  - Make sure all dependencies are installed (Sync Gradle if needed).
  - Run the app on an emulator or physical device.

## Contributing
Feel free to fork this project, submit issues, and create pull requests. Contributions are always welcome!

## License
This project is licensed under the [MIT License](LICENSE).

