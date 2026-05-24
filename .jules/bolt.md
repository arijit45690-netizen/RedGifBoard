## 2024-05-18 - Disconnected Clients Cause Redundant Downloads
**Learning:** If the UI uses an image loading library (like Glide) which caches media, using a separate HTTP client or manual raw URL download (`URL(url).openStream()`) for secondary operations (like downloading to share/export) will bypass the cache and trigger a redundant network request.
**Action:** Always utilize the existing image loading library's cache mechanism (e.g., `Glide.with().downloadOnly()`) for secondary operations to instantly resolve the request from the disk cache.
