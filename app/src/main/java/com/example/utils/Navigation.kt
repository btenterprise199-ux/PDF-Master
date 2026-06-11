package com.example.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ui.screens.DashboardScreen
import com.example.ui.screens.JpgToPdfScreen
import com.example.ui.screens.PhotoCompressScreen
import com.example.ui.screens.PdfMergeScreen
import com.example.ui.screens.PdfCompressScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "dashboard") {
        composable("dashboard") {
            DashboardScreen(
                onNavigateToJpgToPdf = { navController.navigate("jpg_to_pdf") },
                onNavigateToPhotoCompress = { navController.navigate("photo_compress") },
                onNavigateToPdfMerge = { navController.navigate("pdf_merge") },
                onNavigateToPdfCompress = { navController.navigate("pdf_compress") }
            )
        }
        composable("jpg_to_pdf") {
            JpgToPdfScreen(onBack = { navController.popBackStack() })
        }
        composable("photo_compress") {
            PhotoCompressScreen(onBack = { navController.popBackStack() })
        }
        composable("pdf_merge") {
            PdfMergeScreen(onBack = { navController.popBackStack() })
        }
        composable("pdf_compress") {
            PdfCompressScreen(onBack = { navController.popBackStack() })
        }
    }
}
