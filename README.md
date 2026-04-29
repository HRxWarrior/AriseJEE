# Arise JEE

A Solo Leveling-inspired JEE preparation app built with Kotlin + Jetpack Compose.

## Features
- **Rank System**: E, D, C, B, A, S, National Level
- **3 Subjects**: Physics, Chemistry, Maths (15 chapters each)
- **Quiz System**: JEE Mains & Advanced, 15 questions per chapter (5 Easy + 5 Medium + 5 Hard)
- **XP & Leveling**: Difficulty-scaled XP, streak bonuses, rank-based multipliers
- **Weak Topic Detection**: Tracks wrong answers, slow times, and repeated mistakes
- **Study Timer**: Scattered session tracking (daily/weekly/total)
- **Focus Mode**: Full-screen distraction warning
- **Daily Quests**: 4 daily quests with XP rewards
- **Profile**: Username, profile picture, stats, timetable upload
- **Offline-first**: Room database, internet only for loading more questions

## Setup

### If `gradle-wrapper.jar` is missing:
```bash
# Install Gradle 8.4 and run:
gradle wrapper --gradle-version 8.4
```
Or simply push to GitHub — the Actions workflow handles Gradle setup automatically.

### Build locally:
```bash
chmod +x gradlew
./gradlew assembleDebug
```

### Build via GitHub Actions:
Push to `main` or `master` branch. The workflow automatically builds and uploads `app-debug.apk` as an artifact.

## Tech Stack
- Kotlin 1.9.20
- Jetpack Compose (BOM 2023.10.01)
- Room 2.6.1 (KSP)
- Navigation Compose 2.7.6
- Coil (image loading)
- Material3 + Material Icons Extended

## Project Structure
```
app/src/main/java/com/arisejee/app/
├── data/
│   ├── db/entity/      # Room entities
│   ├── db/dao/          # Room DAOs
│   ├── db/AppDatabase   # Room database
│   ├── model/           # Data models (Subject, Chapter, Rank, etc.)
│   ├── provider/        # Question providers (local + remote placeholder)
│   └── repository/      # Repository layer
├── ui/
│   ├── theme/           # Solo Leveling dark fantasy theme
│   ├── components/      # GlowCard, RankBadge, XpBar, etc.
│   ├── screens/         # All 10 screens
│   └── navigation/      # NavGraph + Screen routes
├── viewmodel/           # ViewModels for all screens
├── util/                # XpCalculator, WeakTopicAnalyzer, TimeFormatter
├── AriseJeeApp.kt       # Application class
└── MainActivity.kt      # Entry point
```
