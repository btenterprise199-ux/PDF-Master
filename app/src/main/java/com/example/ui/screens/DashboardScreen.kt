package com.example.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Compress
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.MergeType
import androidx.compose.material.icons.filled.PictureAsPdf
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    onNavigateToJpgToPdf: () -> Unit,
    onNavigateToPhotoCompress: () -> Unit,
    onNavigateToPdfMerge: () -> Unit,
    onNavigateToPdfCompress: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("PDF Master") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            DashboardItem(
                title = "JPG to PDF",
                description = "Convert multiple format images to a single PDF",
                icon = Icons.Default.PictureAsPdf,
                onClick = onNavigateToJpgToPdf,
                modifier = Modifier.testTag("nav_jpg_to_pdf")
            )
            DashboardItem(
                title = "Photo Resizer & Compressor",
                description = "Reduce image dimensions and file size",
                icon = Icons.Default.Image,
                onClick = onNavigateToPhotoCompress,
                modifier = Modifier.testTag("nav_photo_compress")
            )
            DashboardItem(
                title = "PDF Merger",
                description = "Combine multiple PDF files sequentially",
                icon = Icons.Default.MergeType,
                onClick = onNavigateToPdfMerge,
                modifier = Modifier.testTag("nav_pdf_merge")
            )
            DashboardItem(
                title = "PDF Compressor",
                description = "Reduce the file size of existing documents",
                icon = Icons.Default.Compress,
                onClick = onNavigateToPdfCompress,
                modifier = Modifier.testTag("nav_pdf_compress")
            )
        }
    }
}

@Composable
fun DashboardItem(
    title: String,
    description: String,
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                modifier = Modifier.size(48.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
