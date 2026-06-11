package com.example

import android.app.Application
import com.tom_roush.pdfbox.android.PDFBoxResourceLoader

class PdfMasterApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        PDFBoxResourceLoader.init(applicationContext)
    }
}
