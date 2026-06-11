## 2026-06-11 - Glide and RedGifs MP4s
**Learning:** The RedGifs API returns MP4 URLs for the `sd` quality, but the `GifAdapter` was trying to load them into an `ImageView` using Glide's `.asGif()`. This fails because Glide cannot parse MP4 streams as GIFs, resulting in broken images, unnecessary decoding overhead, and massive bandwidth waste (1.7MB per item instead of a thumbnail).
**Action:** Always parse and use the provided `.jpg` `thumbnail` URL from the API for grid previews to save bandwidth and ensure images actually render quickly.
