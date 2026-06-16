## 2024-06-16 - Glide Cache Reuse for Keyboard Sends
**Learning:** The keyboard extension sends GIFs using the same URL (`gif.urls.sd`) that was loaded in the RecyclerView via Glide. By default, the app was making a second network request (`URL().openStream()`) to redownload the GIF before sending.
**Action:** Always check if an image loading library (like Glide) has already cached the asset on disk before initiating a redundant HTTP download, especially in UI components like keyboards where latency is critical.
