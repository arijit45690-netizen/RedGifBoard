## 2024-06-14 - Glide Caching and File Extensions
**Learning:** Glide saves files without extensions in its disk cache. When using these files for sharing/Content Providers, the lack of extension causes the system to determine the wrong MIME type (`application/octet-stream` instead of `image/gif`), breaking compatibility with receiving apps.
**Action:** When using Glide to fetch a cached file for sharing via a FileProvider, always copy the `glideFile` to a new `File` with the correct extension before providing the URI.
