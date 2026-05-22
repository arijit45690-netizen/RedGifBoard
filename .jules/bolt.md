## 2024-05-24 - Glide Cache Reuse for Sharing
**Learning:** The app displays GIFs in a `RecyclerView` using Glide, meaning Glide caches them on disk. However, the `sendGif` sharing function was naively re-downloading the raw file using `URL(...).openStream()`.
**Action:** When a file needs to be shared or manipulated after being displayed, always query the image loader's cache (e.g., `Glide.with(context).asFile().load(url).submit().get()`) before attempting a direct network download to save bandwidth and drastically reduce latency.
