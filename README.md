# 📞 Contact & Dialer App

A fully functional, feature-rich Android application designed to manage contacts, view call logs, and handle in-app messaging. Built with a clean user interface, this app serves as a robust alternative to default system dialers and contact managers.

![Android](https://img.shields.io/badge/Platform-Android-3DDC84?style=for-the-badge&logo=android&logoColor=white)
![Java](https://img.shields.io/badge/Language-Java-007396?style=for-the-badge&logo=java&logoColor=white)
![SQLite](https://img.shields.io/badge/Database-SQLite-003B57?style=for-the-badge&logo=sqlite&logoColor=white)

## ✨ Key Features

The application is structured around a modern Fragment-based navigation system, offering the following core functionalities:

* **👥 Contact Management (`ContactsFragment`):** * Add, edit, view, and organize contact details.
  * Dedicated `AddContactActivity` and `EditContactActivity` for seamless data entry.
* **⭐ Favorites (`FavouritesFragment`):** * Quickly access frequently contacted numbers and important people.
* **🕒 Call History (`RecentsFragment`):** * View detailed logs of incoming, outgoing, and missed calls. 
* **🔢 Keypad/Dialer (`KeypadFragment`):** * A functional dialer interface to easily punch in numbers and initiate calls.
* **💬 In-App Messaging (`ChatActivity`):** * Integrated SMS/chat interface to send and receive messages directly within the app.

## 🛠️ Technology Stack

* **Language:** Java
* **UI/UX:** XML (Activities, Fragments, and Custom Drawables)
* **Local Storage:** SQLite (Managed via `DatabaseHelper` for Contacts, Messages, and Call Logs)
* **View Components:** RecyclerView (with custom adapters: `ContactAdapter`, `CallHistoryAdapter`, `MessageAdapter`)
* **Build System:** Gradle

## 📂 Project Architecture

The codebase follows a clear, modular structure for maintainability:

```text
app/src/main/java/com/example/my_contact/
├── activity/       # UI controllers for single-screen tasks (Add/Edit Contact, Chat, Details)
├── adapter/        # RecyclerView adapters for dynamic lists
├── database/       # SQLite DatabaseHelper for local data persistence
├── fragment/       # Main navigation tabs (Contacts, Favourites, Keypad, Recents)
└── model/          # POJO data models (Contact, CallRecord, Message)
```
Here is a professional and structured README.md tailored for your Contact App Android project. You can copy this directly into the root of your repository.

Markdown
# 📞 Contact & Dialer App

A fully functional, feature-rich Android application designed to manage contacts, view call logs, and handle in-app messaging. Built with a clean user interface, this app serves as a robust alternative to default system dialers and contact managers.

![Android](https://img.shields.io/badge/Platform-Android-3DDC84?style=for-the-badge&logo=android&logoColor=white)
![Java](https://img.shields.io/badge/Language-Java-007396?style=for-the-badge&logo=java&logoColor=white)
![SQLite](https://img.shields.io/badge/Database-SQLite-003B57?style=for-the-badge&logo=sqlite&logoColor=white)

## ✨ Key Features

The application is structured around a modern Fragment-based navigation system, offering the following core functionalities:

* **👥 Contact Management (`ContactsFragment`):** * Add, edit, view, and organize contact details.
  * Dedicated `AddContactActivity` and `EditContactActivity` for seamless data entry.
* **⭐ Favorites (`FavouritesFragment`):** * Quickly access frequently contacted numbers and important people.
* **🕒 Call History (`RecentsFragment`):** * View detailed logs of incoming, outgoing, and missed calls. 
* **🔢 Keypad/Dialer (`KeypadFragment`):** * A functional dialer interface to easily punch in numbers and initiate calls.
* **💬 In-App Messaging (`ChatActivity`):** * Integrated SMS/chat interface to send and receive messages directly within the app.

## 🛠️ Technology Stack

* **Language:** Java
* **UI/UX:** XML (Activities, Fragments, and Custom Drawables)
* **Local Storage:** SQLite (Managed via `DatabaseHelper` for Contacts, Messages, and Call Logs)
* **View Components:** RecyclerView (with custom adapters: `ContactAdapter`, `CallHistoryAdapter`, `MessageAdapter`)
* **Build System:** Gradle

## 📂 Project Architecture

The codebase follows a clear, modular structure for maintainability:

```text
app/src/main/java/com/example/my_contact/
├── activity/       # UI controllers for single-screen tasks (Add/Edit Contact, Chat, Details)
├── adapter/        # RecyclerView adapters for dynamic lists
├── database/       # SQLite DatabaseHelper for local data persistence
├── fragment/       # Main navigation tabs (Contacts, Favourites, Keypad, Recents)
└── model/          # POJO data models (Contact, CallRecord, Message)
```

🚀 Getting Started
Prerequisites
Android Studio: Latest version recommended.

Android SDK: API Level 24 (Nougat) or higher (adjust based on your build.gradle).

Java SDK: Java 8 or higher.

Installation & Setup
Clone the repository:

Bash
git clone [https://github.com/mvinduwara/Contact-App.git](https://github.com/mvinduwara/Contact-App.git)
cd Contact-App
Open in Android Studio:

Launch Android Studio.

Select File > Open and navigate to the cloned directory.

Wait for Gradle to finish syncing the project.

Permissions:

Note: Ensure you grant the necessary runtime permissions (Contacts, Phone, SMS) when prompted on your device for the app to function correctly.

Build and Run:

Connect a physical Android device or start an emulator.

Click the Run button (Shift + F10) to deploy the app.
👨‍💻 Author
Manilka Vinduwara

GitHub Profile : https://github.com/mvinduwara
Email: dev.manilkavinduwara@gmail.com
If you find this project interesting or helpful for learning Android development, please consider giving it a ⭐!
