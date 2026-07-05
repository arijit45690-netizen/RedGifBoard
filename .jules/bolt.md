## 2024-05-24 - Glide Cache Optimization
**Learning:** In a GIF keyboard app, downloading a GIF for sending after it has already been loaded in the UI causes a redundant network request and a 1-2 second delay.
**Action:** Use Glide's disk cache (`Glide.with().asFile().load().submit().get()`) to retrieve the already downloaded image file instead of opening a new network stream.
