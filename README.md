# Vaibhav 🕉️

**An all-in-one, offline-first companion app for UPSC aspirants.**

UPSC aspirants typically juggle multiple apps — one for notes, another for syllabus tracking, another for focus/productivity. Vaibhav consolidates all of this into a single, lightweight, privacy-first application with **no login and no account required**.

🔗 Live: [vaibhav-app.vercel.app](https://vaibhav-app.vercel.app)

---

## ✨ Features

- **No Login, Fully Offline** — All user data (name, attempt year, optional subject) is captured locally on first launch and used to generate a personalised in-app profile. No backend account required.
- **Daily Motivation** — Home screen surfaces a rotating Bhagavad Geeta quote with its meaning to keep aspirants grounded and motivated.
- **Daily Task Manager** — Add, track, and manage daily study goals.
- **Focus Mode** — Pomodoro-based timer with support for custom durations, built for deep-focus study sessions.
- **Notes Tab** — Upload and store personal notes/PDFs locally, with a built-in revision timer to schedule spaced revisions.
- **Syllabus Tracker** — Visual progress tracking for Prelims and Mains syllabus, scoped to the user's selected optional subject.

> **Status:** Actively in development — currently **Phase 3**. Planned for future phases: AI-based study suggestions, curated lecture video recommendations, and book suggestions.

---

## 🛠️ Tech Stack

| Layer | Technology |
|---|---|
| Frontend | React (Vite) |
| Build Tooling | Vite |
| Linting | ESLint |
| Mobile Packaging | Capacitor (Android) |
| Hosting | Vercel |
| Language Split | JavaScript (~97%), Java (~3%, Capacitor Android shell), HTML |

The app is built as a React + Vite single-page application and wrapped with **Capacitor** to produce a native Android build, allowing the same codebase to ship both as a web app and an installable Android app.

---

## 📁 Project Structure

```
vaibhav-app/
├── android/              # Capacitor-generated native Android project
├── public/               # Static assets
├── src/                  # React application source
├── capacitor.config.json # Capacitor configuration
├── vite.config.js        # Vite build configuration
├── eslint.config.js      # ESLint rules
└── package.json
```

---

## 🚀 Getting Started

### Prerequisites
- Node.js (LTS recommended)
- npm

### Installation

```bash
git clone https://github.com/rajat1606-dev/vaibhav-app.git
cd vaibhav-app
npm install
```

### Run locally (web)

```bash
npm run dev
```

### Build for production

```bash
npm run build
```

### Android build (via Capacitor)

```bash
npx cap sync android
npx cap open android
```

---

## 🗺️ Roadmap

- [x] Offline personalised profile setup
- [x] Daily Geeta quote with meaning
- [x] Daily task manager
- [x] Focus mode with Pomodoro + custom timer
- [x] Notes tab with PDF upload + revision timer
- [x] Syllabus tracker (Prelims/Mains)
- [ ] AI-based study suggestions
- [ ] Lecture video recommendations
- [ ] Curated book suggestions

---

## 🤝 Contributing

This project is under active development. Issues, suggestions, and pull requests are welcome.

## 📄 License

No license specified yet.
