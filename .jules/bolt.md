## 2024-05-19 - Network Request Duplication with Image Loaders
**Learning:** Found a common anti-pattern where a displayed image is re-downloaded manually via `URL.openStream()` when it needs to be shared or manipulated. Image loaders like Glide already cache these files.
**Action:** When a file needs to be shared/downloaded that is already rendered on screen, use the image loader's synchronous disk cache retrieval (e.g., `Glide.with(context).asFile().load(url).submit().get()`) inside an IO thread instead of making a fresh network request.
