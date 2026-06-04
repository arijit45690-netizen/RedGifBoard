# Bolt Journal
## 2024-05-18 - Replacing generic java.net.URL stream with Glide
**Learning:** Found a performance bottleneck when downloading GIFs before inserting them into a text field. The `GifKeyboardService` was downloading raw bytes via `java.net.URL(url).openStream()` every time. Since Glide is already set up and configured for the project (used in `GifAdapter`), and it's built to cache network requests, switching the download code to use Glide will avoid redownloading the GIF when it was already cached for preview display.
**Action:** Replace `URL.openStream()` with `Glide.with(...).downloadOnly().load(url).submit().get()` to take advantage of Glide's disk caching, meaning the actual GIF insertion doesn't need a second network request if it was already loaded in the grid preview.
