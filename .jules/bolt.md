## 2024-06-10 - Duplicate Image Downloads When Sharing
**Learning:** In Android apps that display a grid of images (using a library like Glide) and then allow users to share/send those images, redownloading the image from the raw URL when sending is a massive performance bottleneck. The image is likely already in the image loader's cache.
**Action:** Instead of `URL.openStream()`, use the image loader's synchronous cache retrieval methods (e.g., `Glide.with(context).asFile().load(url).submit().get()`) inside an IO coroutine to reuse the cached file and avoid 1-2 second network delays.
