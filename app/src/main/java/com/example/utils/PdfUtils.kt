package com.example.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import com.tom_roush.pdfbox.pdmodel.PDDocument
import com.tom_roush.pdfbox.pdmodel.PDPage
import com.tom_roush.pdfbox.pdmodel.PDPageContentStream
import com.tom_roush.pdfbox.pdmodel.graphics.image.JPEGFactory
import com.tom_roush.pdfbox.pdmodel.graphics.image.LosslessFactory
import com.tom_roush.pdfbox.multipdf.PDFMergerUtility
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.io.OutputStream

object PdfUtils {

    suspend fun createPdfFromImages(context: Context, imageUris: List<Uri>, outputStream: OutputStream) {
        withContext(Dispatchers.IO) {
            val document = PDDocument()
            try {
                for (uri in imageUris) {
                    val page = PDPage()
                    document.addPage(page)
                    val inputStream = context.contentResolver.openInputStream(uri)
                    val bitmap = BitmapFactory.decodeStream(inputStream)
                    inputStream?.close()

                    if (bitmap != null) {
                        val pbImage = JPEGFactory.createFromImage(document, bitmap)
                        // Scale image to fit page, keeping aspect ratio
                        val pbBox = page.mediaBox
                        val scale = minOf(pbBox.width / bitmap.width.toFloat(), pbBox.height / bitmap.height.toFloat())
                        val width = bitmap.width * scale
                        val height = bitmap.height * scale
                        
                        val startX = (pbBox.width - width) / 2
                        val startY = (pbBox.height - height) / 2

                        val contentStream = PDPageContentStream(document, page)
                        contentStream.drawImage(pbImage, startX, startY, width, height)
                        contentStream.close()
                    }
                }
                document.save(outputStream)
            } finally {
                document.close()
            }
        }
    }

    suspend fun mergePdfs(context: Context, pdfUris: List<Uri>, outputStream: OutputStream) {
        withContext(Dispatchers.IO) {
            val merger = PDFMergerUtility()
            merger.destinationStream = outputStream
            for (uri in pdfUris) {
                val inputStream = context.contentResolver.openInputStream(uri)
                if (inputStream != null) {
                    merger.addSource(inputStream)
                }
            }
            merger.mergeDocuments(null)
        }
    }

    suspend fun compressPdf(context: Context, sourceUri: Uri, outputStream: OutputStream) {
        withContext(Dispatchers.IO) {
            // PDFBox doesn't have a simple "compress" method without rasterizing or iterating over XObjects.
            // A simple rewrite can sometimes compress file size by removing unreferenced objects.
            val inputStream = context.contentResolver.openInputStream(sourceUri)
            if (inputStream != null) {
                val document = PDDocument.load(inputStream)
                try {
                    document.save(outputStream)
                } finally {
                    document.close()
                    inputStream.close()
                }
            }
        }
    }
}
