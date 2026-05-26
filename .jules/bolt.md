## 2024-05-26 - Glide cannot decode remote MP4s as GIFs
**Learning:** In Android, `Glide.with(...).asGif().load(mp4_url)` will fail and consume massive bandwidth downloading MP4s only to fail decoding them. RedGifs API returns MP4s for `sd` and `hd` URLs, not GIFs.
**Action:** Use the `thumbnail` URL (which is a lightweight JPEG) in the RecyclerView instead of the `sd` URL to save ~99% bandwidth and prevent Glide decoding failures.
