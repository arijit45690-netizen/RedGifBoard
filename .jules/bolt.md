## 2024-06-25 - RedGif Board Glide Cache Optimization
**Learning:** Found a network bottleneck where an image displayed via Glide (cached locally) was re-downloaded via `URL(url).openStream()` when sending to another app.
**Action:** Use `Glide.with(context).downloadOnly().load(url).submit().get()` to reuse the local cache instead of making a duplicate network request, significantly speeding up the send operation and reducing data usage.
