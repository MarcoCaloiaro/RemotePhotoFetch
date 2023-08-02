package com.marcocaloiaro.remotephotofetch.presentation.theme


import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf


@Composable
fun RemotePhotoFetchTheme(
    content: @Composable () -> Unit
) {
    val dimensions = appDimensions

    ProvideDimens(dimensions = dimensions) {
        MaterialTheme(
            typography = remotePhotoFetchTypography,
            content = content
        )
    }
}

object RemotePhotoFetchTheme {
    val dimens: Dimens
        @Composable
        get() = LocalAppDimens.current
}

@Composable
fun ProvideDimens(
    dimensions: Dimens,
    content: @Composable () -> Unit
) {
    val dimensionSet = remember { dimensions }
    CompositionLocalProvider(LocalAppDimens provides dimensionSet, content = content)
}

private val LocalAppDimens = staticCompositionLocalOf {
    appDimensions
}
