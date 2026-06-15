## 2024-06-15 - RedGifs RecyclerView Network Bottleneck
**Learning:** The RedGifs API returns MP4 videos for its `.sd` URL field. Using Glide to load these MP4s directly into a RecyclerView grid (and worse, forcing `.asGif()`) causes massive bandwidth consumption (~3MB per item) and UI sluggishness as Glide struggles to fetch and extract video frames.
**Action:** Always use the dedicated static image endpoints (`.thumbnail` or `.poster`) for grid previews to reduce payload size by ~99% and ensure instant loading.
