## 2024-07-06 - Image caching for keyboards
**Learning:** Re-downloading images for sharing when they have already been loaded into a UI components via an image loader like Glide wastes time and network bandwidth.
**Action:** Instead of performing a new network request to fetch the raw bytes to share, leverage the image loader's built-in disk cache (e.g. `Glide.with(context).asFile().load(url).submit().get()`) to retrieve the already-downloaded file.
