## 2026-07-04 - Optimize GIF download to use Glide local cache
**Learning:** In Android apps using Glide, redownloading images via standard network calls (like `URL.openStream()`) wastes time if the image was just displayed in the UI. Glide's disk cache can be queried synchronously on background threads using `.downloadOnly().load(url).submit().get()` to return the `File` instantly.
**Action:** Whenever a UI-displayed image needs to be downloaded or shared, use the image loading library's (Glide/Coil/Picasso) cache instead of making raw network requests.
