Grama-Urja — Community Power Monitor

Empowering rural farmers with real-time electricity visibility

Grama-Urja ("Village Energy") is a crowdsourced Android application that helps farmers in rural Karnataka know whether electricity is available at their transformer zone — **without walking to the field**. When one farmer sees power return, they tap "Power ON" and every farmer in that zone gets an instant alert.


Problem Statement

In rural areas, power cuts are frequent and unpredictable. Farmers waste hours walking to their fields just to check if the electricity has returned to run their irrigation pumps. This app solves that problem using community intelligence farmers report power status for others in the same transformer zone.

Features
Login / Register | Firebase Authentication with email & password |
Power Status | Real-time ON/OFF status updated by the community |
Freshness Indicator | Shows "Updated 3 min ago" so farmers know data reliability |
Reporter Name | Displays which farmer last updated the status |
Pump Timer | Calculates how long to run the pump based on crop type & land size |
Zone Selection | Choose your village transformer zone |


 Project Structure

grama_urja/
├── app/
│   ├── src/
│   │   └── main/
│   │       ├── java/com/example/gramaurja/
│   │       │   ├── LoginActivity.kt        ← Login & Register screen
│   │       │   ├── HomeActivity.kt         ← Power status + real-time Firebase
│   │       │   └── PumpTimerActivity.kt    ← Irrigation calculator
│   │       ├── res/
│   │       │   ├── layout/
│   │       │   │   ├── activity_login.xml
│   │       │   │   ├── activity_home.xml
│   │       │   │   └── activity_pump_timer.xml
│   │       │   ├── values/
│   │       │   │   ├── colors.xml
│   │       │   │   └── strings.xml
│   │       │   └── drawable/
│   │       └── AndroidManifest.xml
│   └── build.gradle.kts
├── build.gradle.kts
├── libs.versions.toml
└── README.md


Tech Stack

| Technology | Purpose |

| Kotlin | Primary language |
| Android Studio | IDE |
| Firebase Authentication | User login & registration |
| Firebase Realtime Database | Live power status sync across all users |
| XML Layouts | UI design |
| Material Design | Button and UI components |

How to Run

Prerequisites
- Android Studio (Hedgehog or later)
- Android phone or emulator (API 24+)
- A Firebase account (free)

Step 1 — Clone the repository
```bash
git clone https://github.com/sanjanahsn04-crypto/grama_urja.git
cd grama_urja
```

Step 2 — Set up Firebase
1. Go to [console.firebase.google.com](https://console.firebase.google.com)
2. Create a new project called GramaUrja
3. Add an Android app with package name `com.example.gramaurja`
4. Download `google-services.json` and place it in the `app/` folder
5. Enable Authentication → Email/Password
6. Create a Realtime Database and set rules to test mode

Step 3 — Build and Run
1. Open the project in Android Studio
2. Click Sync Now when prompted
3. Connect your Android phone via USB and enable USB Debugging
4. Press the ▶ Run button


Firebase Database Structure
{
  "zones": {
    "zone_1": {
      "powerStatus": {
        "isOn": true,
        "timestamp": 1706789412000,
        "reporter": "Raju K."
      }
    }
  },
  "users": {
    "uid_abc123": {
      "name": "Raju Kumar",
      "email": "raju@example.com",
      "zone": "zone_1"
    }
  }
}


User Flow

App Opens
    ↓
Login Screen  ──→  Register (new users)
    ↓
Home Screen  (shows power status for selected zone)
    ↓
Farmer taps "Power ON" or "Power OFF"
    ↓
Firebase updates instantly  ──→  All users in zone see update within 2 seconds
    ↓
Pump Timer  (calculates irrigation runtime by crop & acres)


Impact Goals

Save Time — Farmers no longer walk to the field just to check power
Save Water — Knowing power availability helps plan irrigation efficiently
Community Intelligence — Shared data solves an infrastructure gap
Works on 2G/3G — Lightweight Firebase calls, no heavy data needed

Screens

1. Login Screen
- Email, password, and name fields
- Login button for existing users
- Register button for new users
- Firebase Authentication handles both

2. Home Screen
- Greets user by name: "Welcome, Raju!"
- Large power status card (green = ON, red = OFF)
- Shows who last reported and how long ago
- Two buttons: Power ON / Power OFF
- Link to Pump Timer

3. Pump Timer Screen
- Select crop type (Paddy, Ragi, Sugarcane, Vegetables, Cotton)
- Select land size (0.5 to 5 acres)
- Instantly calculates recommended pump runtime

Security

- Firebase Authentication protects all user data
- Database rules restrict read/write to authenticated users only
- No sensitive data stored locally on device



Developer
Sanjana S V
Android Developer | Karnataka, India

Built with love for the farming community of rural Karnataka
