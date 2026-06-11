package com.example.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.OutputStream

object ImageUtils {

    suspend fun resizeAndCompressImage(
        context: Context,
        sourceUri: Uri,
        outputStream: OutputStream,
        maxWidth: Int = 1080,
        maxHeight: Int = 1080,
        quality: Int = 80
    ) {
        withContext(Dispatchers.IO) {
            val inputStream = context.contentResolver.openInputStream(sourceUri)
            val originalBitmap = BitmapFactory.decodeStream(inputStream)
            inputStream?.close()

            if (originalBitmap != null) {
                // Resize
                val width = originalBitmap.width
                val height = originalBitmap.height

                var newWidth = width
                var newHeight = height

                if (width > maxWidth || height > maxHeight) {
                    val aspectRatio = width.toFloat() / height.toFloat()
                    if (aspectRatio > 1) {
                        newWidth = maxWidth
                        newHeight = (maxWidth / aspectRatio).toInt()
                    } else {
                        newHeight = maxHeight
                        newWidth = (maxHeight * aspectRatio).toInt()
                    }
                }

                val scaledBitmap = Bitmap.createScaledBitmap(originalBitmap, newWidth, newHeight, true)
                
                // Compress
                scaledBitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)
                
                if (scaledBitmap != originalBitmap) {
                    scaledBitmap.recycle()
                }
                originalBitmap.recycle()
            }
        }
    }
}
