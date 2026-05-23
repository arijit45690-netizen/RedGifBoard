## 2024-05-18 - Glide Cache Reuse for Sending

**Learning:** When building an Android component that displays images (like a keyboard with an adapter) AND sends them, the image library (like Glide) has already downloaded the image for display. Downloading it a second time via `URL(url).openStream()` when sending is a massive, redundant performance hit.
**Action:** Always check if an image library like Glide is already in use for a URL. If it is, use its disk cache directly (e.g., `Glide.with(context).downloadOnly().load(url).submit().get()`) to fetch the already-downloaded file instead of making a duplicate network request.
