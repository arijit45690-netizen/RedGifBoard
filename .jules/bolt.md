## 2024-06-18 - Avoid redundant network downloads by reusing Glide disk cache
**Learning:** When displaying images via Glide (or Picasso/Coil) in a list/grid, those images are heavily cached on disk. If you need the actual File object later (e.g., to share via a content provider or attach to an email), do not download it again using raw `URL.openStream()`.
**Action:** Use the image loading library's synchronous disk cache retrieval method (e.g., `Glide.with(context).asFile().load(url).submit().get()`) in a background thread to instantly get the file, bypassing the network entirely.
