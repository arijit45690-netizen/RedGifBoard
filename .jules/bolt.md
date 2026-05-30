## 2024-05-19 - Network Request Optimization
**Learning:** Redundant network requests can happen when an image downloading library (like Glide) displays an image, but a separate `URL.openStream()` call downloads it again for sharing/saving.
**Action:** When saving or sharing a file that is already loaded via an image loading library, use the library's disk caching feature (e.g., `Glide.with(context).asFile().load(url).submit().get()`) to retrieve the file from cache rather than downloading it again.
