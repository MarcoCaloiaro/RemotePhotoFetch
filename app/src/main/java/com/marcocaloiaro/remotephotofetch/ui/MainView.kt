package com.marcocaloiaro.remotephotofetch.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material.rememberBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.marcocaloiaro.remotephotofetch.R
import com.marcocaloiaro.remotephotofetch.presentation.theme.RemotePhotoFetchTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainView(
    triggerIntent: (MainIntent) -> Unit,
    viewState: MainViewState,
    effects: SharedFlow<MainEffect>
) {
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberBottomSheetState(
            initialValue = BottomSheetValue.Collapsed
        )
    )

    val coroutineScope = rememberCoroutineScope()

    val lazyGridState = rememberLazyGridState()

    LaunchedEffect(key1 = true) {
        triggerIntent(MainIntent.ScreenLaunched)
        effects.collect { effect ->
            when (effect) {
                MainEffect.CloseSheet -> if (scaffoldState.bottomSheetState.isExpanded) {
                    scaffoldState.bottomSheetState.collapse()
                }
            }
        }
    }

    BottomSheetScaffold(
        sheetContent = {
            BackHandler(enabled = scaffoldState.bottomSheetState.isExpanded) {
                coroutineScope.launch {
                    scaffoldState.bottomSheetState.collapse()
                }
            }
            if (viewState.selectedPhoto != null) {
                BottomSheetContent(
                    triggerIntent,
                    viewState.selectedPhoto
                )
            }
        },
        sheetShape = RoundedCornerShape(RemotePhotoFetchTheme.dimens.radius_shape),
        sheetPeekHeight = 0.dp,
        scaffoldState = scaffoldState
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            if (viewState.shouldLoad) {
                CircularProgressIndicator(
                    color = Color.Black,
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier
                        .padding(bottom = RemotePhotoFetchTheme.dimens.padding_half)
                        .fillMaxSize(),
                    state = lazyGridState
                ) {
                    items(viewState.photos) { photo ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                                .padding(
                                    top = RemotePhotoFetchTheme.dimens.padding
                                )
                                .padding(
                                    horizontal = RemotePhotoFetchTheme.dimens.padding
                                )
                                .clickable {
                                    triggerIntent(MainIntent.PhotoSelected(photo))
                                    coroutineScope.launch {
                                        // delay needed because by the time we check if a photo is selected, viewState might not be updated yet.
                                        delay(300)
                                        if (scaffoldState.bottomSheetState.isCollapsed) {
                                            scaffoldState.bottomSheetState.expand()
                                        }
                                    }
                                },
                            elevation = 8.dp,
                            shape = RoundedCornerShape(RemotePhotoFetchTheme.dimens.radius_shape),
                        ) {
                            AsyncImage(
                                model = photo.url,
                                contentDescription = null,
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                }
            }
            FloatingActionButton(
                onClick = {
                    triggerIntent(MainIntent.AddPhotoClicked)
                    coroutineScope.launch {
                        lazyGridState.scrollToItem(
                            viewState.photos.size
                        )
                    }
                },
                modifier = Modifier
                    .padding(bottom = RemotePhotoFetchTheme.dimens.padding)
                    .padding(end = RemotePhotoFetchTheme.dimens.padding)
                    .align(Alignment.BottomEnd),
                backgroundColor = Color.Black.copy(alpha = 0.8f)
            ) {
                Row {
                    Text(
                        text = stringResource(id = R.string.add_photos),
                        style = MaterialTheme.typography.button,
                        modifier = Modifier.padding(start = RemotePhotoFetchTheme.dimens.padding_half)
                    )
                    Icon(
                        Icons.Filled.Add,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.padding(end = RemotePhotoFetchTheme.dimens.padding_half)
                    )
                }
            }
        }
    }

}