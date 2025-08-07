📌 Overview

A real-time air defense simulation featuring:
- **360° Radar Monitoring** with sweep animation
- **Unauthorized Aircraft Detection** using angle/distance algorithms
- **Multi-Level Alerts** (Visual/Audio/System)

✨ Key Features

### 🚨 Threat Detection System
- Core collision logic (AirCraftDetection.java)
- Real-time Processing: AnimationTimer at 60FPS
- Thread-Safe UI: Platform.runLater() for FX updates
- Multi-Modal Alerts:
🔴 Red blips + glow effects
🔉 Loopable siren sounds
📝 Live incident logging

### 🛡️ Defense Response
- Pilot-aircraft pairing interface
- Alert resolution workflow
- Database-backed authorization



🧰 Tech Stack
Component	Implementation Highlights
- Core	Java 17, OOP Principles
- UI	JavaFX (CSS Styled)
- Persistence	GSON 2.10+
- Concurrency	Platform.runLater(), Service
- Audio	javax.sound.sampled

🏗️ Project Structure
resources/
  └── images/
      └── login_bg.jpg
  └── sounds/
      └── 574581__outlawsoftware__alarm-loop-3-v1.wav
src/app/
  └── controllers/
      ├── HomeController.java
      └── LoginController.java
  └── data/
      └── DataManager.java
  └── models/
      ├── Aircraft.java
      └── Pilot.java
  └── services/
      ├── AircraftGenerator.java
      ├── RadarSimulation.java
      └── SessionManager.java
  └── views/
      ├── AddAircraftDialog.java
      ├── AddPilotDialog.jav
      ├── AlertResponseView.java
      ├── DeploymentLogView.java
      ├── InventoryView.java
      ├── LoginView.java
      ├── PilotView.java
      └── RadarView.java
  └── Main.java
