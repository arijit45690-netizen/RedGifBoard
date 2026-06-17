## 2024-06-18 - Caching Network Requests

**Learning:** When displaying images via Glide and later needing to use those same image files as inputs (e.g. content sharing via FileProvider), it is an anti-pattern to download the resource a second time manually using `URL.openStream()`. This causes unneccesary duplicate network requests and adds a ~1-2 sec latency in UI response. Glide natively caches resources, allowing retrieval of the underlying file via `Glide.with(context).asFile().load(url).submit().get()`.

**Action:** Whenever a network asset is needed for both UI display (e.g., ImageView preview) and underlying system features (e.g., FileProvider sharing), always leverage the disk caching from the image loading library used for display to satisfy the secondary need, instead of recreating network connections manually.
