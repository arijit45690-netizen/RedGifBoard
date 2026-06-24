## 2024-05-19 - Optimizing Redundant Network Calls with Glide Cache

**Learning:** Android image loading libraries like Glide automatically cache downloaded images. When you need to do further processing or sharing of an image that is already displayed, re-downloading it via `URL.openStream()` or similar means is a significant performance anti-pattern.
**Action:** When working with images that have already been rendered in a view, always check if the caching library (Glide, Coil, Picasso) can provide the local file directly. Use `Glide.with().downloadOnly().load(url).submit().get()` (or its equivalent) to fetch from the local cache instead of making a redundant network request.
