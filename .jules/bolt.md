## 2024-06-28 - Image Loading Library Disk Cache Reuse
**Learning:** Reusing the disk cache of an image loading library (like Glide) instead of manually re-downloading image assets saves a significant amount of network requests and reduces latency, especially in contexts like custom keyboards where images are displayed before being sent.
**Action:** When working with code that fetches media to display and then subsequently needs to process or send that media, always check if the display library provides a mechanism to access its disk cache directly.
