## 2024-05-14 - Optimize Image Downloading

**Learning:** Glide natively supports downloading image caches straight from the filesystem without needing network bandwidth to re-download.

**Action:** Use `Glide.with(context).downloadOnly().load(url).submit().get()` to copy image resources efficiently straight from caches when they have already been cached prior by Glide rendering.
