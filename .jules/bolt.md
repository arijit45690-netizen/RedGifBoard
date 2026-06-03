## 2024-05-18 - Avoid Redundant Downloads
**Learning:** Found a case where a file already fetched and displayed by an image loader (Glide) was being downloaded again via `URL().openStream()` when user requested to use it.
**Action:** Replaced redundant manual download with `Glide.with(context).downloadOnly().load(url).submit().get()` to hit the disk cache immediately, saving time, bandwidth, and battery.
