## 2024-05-18 - Avoid redundant network downloads using cache
**Learning:** In Android apps displaying images/GIFs with Glide and later sharing them, the sharing code sometimes redundantly re-downloads the file via `URL().openStream()`. Glide's cache already has the file because it just displayed it.
**Action:** Use `Glide.with(context).downloadOnly().load(url).submit().get()` to pull from cache first, with a network fallback. This skips the redundant network request and speeds up the sharing action significantly.
