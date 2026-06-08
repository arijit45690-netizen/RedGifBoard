## 2024-05-24 - Found optimization in GIF download
**Learning:** The app downloads GIFs using `URL(gif.urls.sd).openStream().use { input -> file.outputStream().use { output -> input.copyTo(output) } }` every time a GIF is sent if it's not cached. It could use `Glide` to download it, which might take advantage of its internal disk cache and connection pooling, or at least share the download with the image display logic if the image is already in Glide's cache.
**Action:** Replace `URL.openStream()` with Glide's download logic to leverage existing caches and connection pools.
