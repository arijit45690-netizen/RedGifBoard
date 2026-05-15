# RedGif Board — Android GIF Keyboard

A custom Android keyboard that searches RedGifs and lets you send GIFs directly into any chat app.

---

## 📁 Project Structure

```
RedGifBoard/
├── build.gradle                          ← project-level gradle
├── settings.gradle
└── app/
    ├── build.gradle                      ← dependencies go here
    └── src/main/
        ├── AndroidManifest.xml
        ├── java/com/yourname/redgifboard/
        │   ├── Models.kt                 ← data classes (GifItem, etc.)
        │   ├── RedGifsApi.kt             ← Retrofit API interface
        │   ├── GifAdapter.kt             ← RecyclerView adapter
        │   ├── GifKeyboardService.kt     ← THE KEYBOARD (main logic)
        │   └── MainActivity.kt           ← setup screen
        └── res/
            ├── layout/
            │   ├── keyboard_view.xml     ← keyboard panel UI
            │   ├── gif_item.xml          ← single GIF cell
            │   └── activity_main.xml     ← setup screen UI
            ├── xml/
            │   ├── method.xml            ← IME declaration
            │   └── file_provider_paths.xml
            └── drawable/
                └── search_bg.xml         ← search bar style
```

---

## 🚀 Setup in Android Studio

### Step 1 — Open the project
1. Open Android Studio
2. **File → Open** → select the `RedGifBoard` folder
3. Wait for Gradle sync to finish (may take 2–3 minutes first time)

### Step 2 — Change the package name (optional but recommended)
Replace all `com.yourname.redgifboard` with your own package name, e.g. `com.arijit.redgifboard`:
- **Edit → Find → Replace in Files** → replace all occurrences
- Also update `namespace` and `applicationId` in `app/build.gradle`

### Step 3 — Run on your phone
1. Enable Developer Mode on your Android phone
2. Connect via USB, trust the computer
3. Press **Run ▶** in Android Studio
4. The app installs and opens the setup screen

### Step 4 — Enable the keyboard
1. Tap **"Enable Keyboard in Settings"** → find RedGif Board → toggle ON
2. Come back to the app, tap **"Switch to RedGif Board"**
3. Open WhatsApp / Telegram / any chat app → tap a text field → switch keyboard

---

## 🔧 How It Works

| Component | Purpose |
|-----------|---------|
| `GifKeyboardService` | The IME (keyboard) service — shown when user selects your keyboard |
| `RedGifsClient` | Retrofit HTTP client calling `api.redgifs.com` |
| `GifAdapter` | Loads GIFs into a 2-column grid using Glide |
| `commitContent()` | Android API that sends the GIF to the chat app |
| `FileProvider` | Securely shares the downloaded GIF file with other apps |

---

## ⚠️ Known Limitations

- RedGifs API is unofficial — no API key needed currently, but could change
- GIFs are downloaded to cache before sending (adds ~1–2 sec delay)
- Some apps (e.g. older versions of WhatsApp) may not accept GIFs from custom keyboards
- Tested on Android 8.0+ (API 26+)

---

## 🔮 Next Steps (Phase 2)

- [ ] Add trending/categories tabs
- [ ] Add a proper text keyboard below the GIF panel
- [ ] Cache management (clear old GIFs from storage)
- [ ] Add haptic feedback on GIF tap
- [ ] Favorite GIFs feature
