package com.marcocaloiaro.remotephotofetch.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.marcocaloiaro.remotephotofetch.data.local.LocalPhoto
import com.marcocaloiaro.remotephotofetch.presentation.theme.RemotePhotoFetchTheme

@Composable
fun BottomSheetContent(
    triggerIntent: (MainIntent) -> Unit,
    selectedPhoto: LocalPhoto
) {
    Column(
        modifier = Modifier.background(Color.Black.copy(alpha = 0.2f))
    ) {
        IconButton(
            onClick = { triggerIntent(MainIntent.CloseSheetClicked) }
        ) {
            Icon(
                Icons.Filled.Close,
                contentDescription = null
            )
        }
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(200.dp, 300.dp)
                .padding(
                    vertical = RemotePhotoFetchTheme.dimens.padding_double,
                )
                .padding(
                    horizontal = RemotePhotoFetchTheme.dimens.padding
                ),
            elevation = 8.dp,
            shape = RoundedCornerShape(RemotePhotoFetchTheme.dimens.radius_shape),
        ) {
            AsyncImage(
                model = selectedPhoto.url,
                contentDescription = null,
                contentScale = ContentScale.None
            )
        }
        if (selectedPhoto.description != null) {
            Text(
                selectedPhoto.description,
                style = MaterialTheme.typography.h4,
                modifier = Modifier
                    .padding(bottom = RemotePhotoFetchTheme.dimens.padding_half)
                    .padding(horizontal = RemotePhotoFetchTheme.dimens.padding)
                    .fillMaxWidth()
            )
        }
        if (selectedPhoto.location != null) {
            Text(
                selectedPhoto.location,
                style = MaterialTheme.typography.body1,
                modifier = Modifier
                    .padding(vertical = RemotePhotoFetchTheme.dimens.padding)
                    .padding(horizontal = RemotePhotoFetchTheme.dimens.padding)
                    .fillMaxWidth()
            )
        }
    }
}