# KtMediaStore

[<-- Back](../home.md)

`KtMediaStore` provides convenient access to Android's `MediaStore` content providers.

It includes extension functions for accessing media collections, objects containing commonly used column names, and a function for loading image or video thumbnails.

## Usage

You can access the different media collections directly from a `Context`.

```kotlin
val audioUri = ktAudioStore()
val videoUri = ktVideoStore()
val imageUri = ktImageStore()
val downloadUri = ktdownloadStore()
val fileUri = ktFileStore()
```

You can also use the collection objects:

```kotlin
val audioUri = KtAudioStore.URI
val imageUri = KtImageStore.URI
```

## Methods

| Method                            | Description                                    |
| --------------------------------- | ---------------------------------------------- |
| `ktAudioStore()`                  | Returns the external audio collection URI.     |
| `ktVideoStore()`                  | Returns the external video collection URI.     |
| `ktImageStore()`                  | Returns the external image collection URI.     |
| `ktdownloadStore()`               | Returns the external downloads collection URI. |
| `ktFileStore()`                   | Returns the external files collection URI.     |
| `ktThumbnail(uri, width, height)` | Loads a thumbnail for a content URI.           |

## Media Collection Objects

The library provides five objects containing collection URIs and commonly used column names.

These constants can be used when querying media through `ContentResolver`.

### KtAudioStore

Provides access to audio files.

```kotlin
val audioUri = KtAudioStore.URI
```

Available columns:

| Constant        | Description                       |
| --------------- | --------------------------------- |
| `ID`            | Unique media record ID.           |
| `TITLE`         | Audio title.                      |
| `ARTIST`        | Artist name.                      |
| `ALBUM`         | Album name.                       |
| `DURATION`      | Audio duration.                   |
| `SIZE`          | File size.                        |
| `DISPLAY_NAME`  | File display name.                |
| `ALBUM_ID`      | Album record ID.                  |
| `ALBUM_ARTIST`  | Album artist.                     |
| `MIME_TYPE`     | Media MIME type.                  |
| `DATE_ADDED`    | Date the media was added.         |
| `DATE_MODIFIED` | Date the media was last modified. |

### KtVideoStore

Provides access to video files.

```kotlin
val videoUri = KtVideoStore.URI
```

Available columns:

| Constant        | Description                       |
| --------------- | --------------------------------- |
| `ID`            | Unique media record ID.           |
| `TITLE`         | Video title.                      |
| `DURATION`      | Video duration.                   |
| `SIZE`          | File size.                        |
| `WIDTH`         | Video width.                      |
| `HEIGHT`        | Video height.                     |
| `DISPLAY_NAME`  | File display name.                |
| `MIME_TYPE`     | Media MIME type.                  |
| `DATE_ADDED`    | Date the media was added.         |
| `DATE_MODIFIED` | Date the media was last modified. |

### KtImageStore

Provides access to image files.

```kotlin
val imageUri = KtImageStore.URI
```

Available columns:

| Constant        | Description                       |
| --------------- | --------------------------------- |
| `ID`            | Unique media record ID.           |
| `TITLE`         | Image title.                      |
| `DISPLAY_NAME`  | File display name.                |
| `SIZE`          | File size.                        |
| `WIDTH`         | Image width.                      |
| `HEIGHT`        | Image height.                     |
| `MIME_TYPE`     | Image MIME type.                  |
| `DATE_ADDED`    | Date the image was added.         |
| `DATE_MODIFIED` | Date the image was last modified. |

### KtDownloadStore

Provides access to downloaded files.

```kotlin
val downloadUri = KtDownloadStore.URI
```

Available columns:

| Constant        | Description                      |
| --------------- | -------------------------------- |
| `ID`            | Unique media record ID.          |
| `DISPLAY_NAME`  | File display name.               |
| `TITLE`         | File title.                      |
| `SIZE`          | File size.                       |
| `MIME_TYPE`     | File MIME type.                  |
| `DATE_ADDED`    | Date the file was added.         |
| `DATE_MODIFIED` | Date the file was last modified. |

### KtFileStore

Provides access to the general external files collection.

```kotlin
val fileUri = KtFileStore.URI
```

Available columns:

| Constant        | Description                      |
| --------------- | -------------------------------- |
| `ID`            | Unique file record ID.           |
| `DISPLAY_NAME`  | File display name.               |
| `TITLE`         | File title.                      |
| `SIZE`          | File size.                       |
| `MIME_TYPE`     | File MIME type.                  |
| `DATE_ADDED`    | Date the file was added.         |
| `DATE_MODIFIED` | Date the file was last modified. |

## Querying Media

The collection URIs and column constants are designed to work with Android's `ContentResolver`.

For example, to query audio files:

```kotlin
val cursor = contentResolver.query(
    KtAudioStore.URI,
    arrayOf(
        KtAudioStore.ID,
        KtAudioStore.TITLE,
        KtAudioStore.ARTIST
    ),
    null,
    null,
    null
)
```

The query returns a `Cursor` containing the requested columns.

You can read the results using the cursor:

```kotlin
cursor?.use {
    while (it.moveToNext()) {

        val title = it.getString(
            it.getColumnIndexOrThrow(KtAudioStore.TITLE)
        )

        val artist = it.getString(
            it.getColumnIndexOrThrow(KtAudioStore.ARTIST)
        )

        println("$title - $artist")
    }
}
```

You can use the same approach with the video, image, download, and general file collections.

## Loading Thumbnails

`ktThumbnail()` loads a thumbnail from a content URI and returns an Android `Bitmap`.

```kotlin
val thumbnail = ktThumbnail(uri)
```

It accepts three parameters:

| Parameter | Default  | Description                    |
| --------- | -------- | ------------------------------ |
| `uri`     | Required | Content URI of the media item. |
| `width`   | `200`    | Requested thumbnail width.     |
| `height`  | `200`    | Requested thumbnail height.    |

Example with custom dimensions:

```kotlin
val thumbnail = ktThumbnail(
    uri = imageUri,
    width = 300,
    height = 300
)
```

The returned bitmap can be displayed in an `ImageView`:

```kotlin
imageView.setImageBitmap(thumbnail)
```

## Notes

- These functions return collection URIs; they do not directly retrieve media files.
- Use `ContentResolver.query()` to retrieve media records.
- Always close a returned `Cursor`, preferably with `use {}`.
- Access to media depends on Android's storage permissions and access rules.
- `ktThumbnail()` uses `ContentResolver.loadThumbnail()`, which requires Android API 29 or later.
- The requested thumbnail dimensions are not necessarily guaranteed to be the exact final bitmap dimensions.
- The collection objects expose column-name constants to simplify queries; they do not perform queries themselves.
- The external collections refer to media indexed by Android's `MediaStore`, not every file on the device.