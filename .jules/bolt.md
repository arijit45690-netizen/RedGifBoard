## 2024-05-15 - Reuse Glide cache to avoid duplicate network calls
**Learning:** `Glide.with(context).asFile().load(url).submit().get()` is an effective way to retrieve already-cached images/GIFs synchronously in Coroutines, preventing duplicate network calls when sending files that were previously displayed in a RecyclerView.
**Action:** Always check if an image is already cached by an image loading library before downloading it manually via `URL.openStream()`.
