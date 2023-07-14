package com.example.horizona

import android.net.Uri
import java.io.File

sealed class ImageState {
    object Initial: ImageState()
    class ImageSelected(val imageUri: Uri): ImageState()
    object ImageUploaded: ImageState()
    class ImageDownloaded(val downloadedImageFile: File): ImageState()
}