## 2024-05-25 - Glide cache fetching in `InputMethodService`
**Learning:** In Android apps handling images/GIFs, especially keyboard apps, images displayed in UI (e.g. `RecyclerView`) are often cached by image libraries like Glide. When "sending" or sharing the image, it's a common anti-pattern to re-download the file directly via URL stream.
**Action:** Always check if the file is already cached by the image library before initiating a network download. You can synchronously get the cached file using `Glide.with(context).asFile().load(url).submit().get()` when running on a background thread.
