package com.example.wecompose.ui.theme

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Stable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color

private val LightColorPalette = WeComposeColors(
    bottomBar = white1,
    background = white2,
    listItem = white,
    divider = white3,
    chatPage = white2,
    textPrimary = black3,
    textPrimaryMe = black3,
    textSecondary = grey1,
    onBackground = grey3,
    icon = black,
    iconCurrent = green3,
    badge = red1,
    onBadge = white,
    bubbleMe = green1,
    bubbleOthers = white,
    textFieldBackground = white,
    more = grey4,
    chatPageBgAlpha = 0f,
)

private val DarkColorPalette = WeComposeColors(
    bottomBar = black1,
    background = black2,
    listItem = black3,
    divider = black4,
    chatPage = black2,
    textPrimary = white4,
    textPrimaryMe = black6,
    textSecondary = grey1,
    onBackground = grey3,
    icon = white5,
    iconCurrent = green3,
    badge = red1,
    onBadge = white,
    bubbleMe = green2,
    bubbleOthers = black5,
    textFieldBackground = black7,
    more = grey5,
    chatPageBgAlpha = 0f,
)

private val NewYearColorPalette = WeComposeColors(
    bottomBar = red4,
    background = red5,
    listItem = red2,
    divider = red3,
    chatPage = red5,
    textPrimary = white,
    textPrimaryMe = black6,
    textSecondary = grey2,
    onBackground = grey2,
    icon = white5,
    iconCurrent = green3,
    badge = yellow1,
    onBadge = black3,
    bubbleMe = green2,
    bubbleOthers = red6,
    textFieldBackground = red7,
    more = red8,
    chatPageBgAlpha = 1f,
)


private val LocalWeComposeColors = compositionLocalOf {
    LightColorPalette
}

object WeComposeTheme {
    val colors: WeComposeColors
        @Composable
        get() = LocalWeComposeColors.current
    enum class Theme {
        Light, Dark, NewYear
    }
}

@Stable
class WeComposeColors(
    bottomBar: Color,
    background: Color,
    listItem: Color,
    divider: Color,
    chatPage: Color,
    textPrimary: Color,
    textPrimaryMe: Color,
    textSecondary: Color,
    onBackground: Color,
    icon: Color,
    iconCurrent: Color,
    badge: Color,
    onBadge: Color,
    bubbleMe: Color,
    bubbleOthers: Color,
    textFieldBackground: Color,
    more: Color,
    chatPageBgAlpha: Float,
) {
    var bottomBar: Color by mutableStateOf(bottomBar)
        private set
    var background: Color by mutableStateOf(background)
        private set
    var listItem: Color by mutableStateOf(listItem)
        private set
    var chatListDivider: Color by mutableStateOf(divider)
        private set
    var chatPage: Color by mutableStateOf(chatPage)
        private set
    var textPrimary: Color by mutableStateOf(textPrimary)
        private set
    var textPrimaryMe: Color by mutableStateOf(textPrimaryMe)
        private set
    var textSecondary: Color by mutableStateOf(textSecondary)
        private set
    var onBackground: Color by mutableStateOf(onBackground)
        private set
    var icon: Color by mutableStateOf(icon)
        private set
    var iconCurrent: Color by mutableStateOf(iconCurrent)
        private set
    var badge: Color by mutableStateOf(badge)
        private set
    var onBadge: Color by mutableStateOf(onBadge)
        private set
    var bubbleMe: Color by mutableStateOf(bubbleMe)
        private set
    var bubbleOthers: Color by mutableStateOf(bubbleOthers)
        private set
    var textFieldBackground: Color by mutableStateOf(textFieldBackground)
        private set
    var more: Color by mutableStateOf(more)
        private set
    var chatPageBgAlpha: Float by mutableFloatStateOf(chatPageBgAlpha)
        private set
}

@Composable
fun WeComposeTheme(
    theme: WeComposeTheme.Theme = WeComposeTheme.Theme.Light,
    content: @Composable () -> Unit
) {
    val targetColors = when(theme) {
        WeComposeTheme.Theme.Light -> LightColorPalette
        WeComposeTheme.Theme.Dark -> DarkColorPalette
        WeComposeTheme.Theme.NewYear -> NewYearColorPalette
    }

    //animateColorAsState 动画方式设置主体变色，颜色渐变效果好
    val bottomBar = animateColorAsState(targetValue = targetColors.bottomBar, TweenSpec(600), label = "MyColorAnimation")
    val background = animateColorAsState(targetValue = targetColors.background, TweenSpec(600), label = "MyColorAnimation")
    val listItem = animateColorAsState(targetValue = targetColors.listItem, TweenSpec(600), label = "MyColorAnimation")
    val chatListDivider = animateColorAsState(targetValue = targetColors.chatListDivider, TweenSpec(600), label = "MyColorAnimation")
    val chatPage = animateColorAsState(targetValue = targetColors.chatPage, TweenSpec(600), label = "MyColorAnimation")
    val textPrimary = animateColorAsState(targetValue = targetColors.textPrimary, TweenSpec(600), label = "MyColorAnimation")
    val textPrimaryMe = animateColorAsState(targetValue = targetColors.textPrimaryMe, TweenSpec(600), label = "MyColorAnimation")
    val textSecondary = animateColorAsState(targetValue = targetColors.textSecondary, TweenSpec(600), label = "MyColorAnimation")
    val onBackground = animateColorAsState(targetValue = targetColors.onBackground, TweenSpec(600), label = "MyColorAnimation")
    val icon = animateColorAsState(targetValue = targetColors.icon, TweenSpec(600), label = "MyColorAnimation")
    val iconCurrent = animateColorAsState(targetValue = targetColors.iconCurrent, TweenSpec(600), label = "MyColorAnimation")
    val badge = animateColorAsState(targetValue = targetColors.badge, TweenSpec(600), label = "MyColorAnimation")
    val onBadge = animateColorAsState(targetValue = targetColors.onBadge, TweenSpec(600), label = "MyColorAnimation")
    val bubbleMe = animateColorAsState(targetValue = targetColors.bubbleMe, TweenSpec(600), label = "MyColorAnimation")
    val bubbleOthers = animateColorAsState(targetValue = targetColors.bubbleOthers, TweenSpec(600), label = "MyColorAnimation")
    val textFieldBackground = animateColorAsState(targetValue = targetColors.textFieldBackground, TweenSpec(600), label = "MyColorAnimation")
    val more = animateColorAsState(targetValue = targetColors.more, TweenSpec(600), label = "MyColorAnimation")
    val chatPageBgAlpha = animateFloatAsState(targetValue = targetColors.chatPageBgAlpha, TweenSpec(600), label = "MyFloatAnimation")

    val colors = WeComposeColors(
        bottomBar = bottomBar.value,
        background = background.value,
        listItem = listItem.value,
        divider = chatListDivider.value,
        chatPage = chatPage.value,
        textPrimary = textPrimary.value,
        textPrimaryMe = textPrimaryMe.value,
        textSecondary = textSecondary.value,
        onBackground = onBackground.value,
        icon = icon.value,
        iconCurrent = iconCurrent.value,
        badge = badge.value,
        onBadge = onBadge.value,
        bubbleMe = bubbleMe.value,
        bubbleOthers = bubbleOthers.value,
        textFieldBackground = textFieldBackground.value,
        more = more.value,
        chatPageBgAlpha = chatPageBgAlpha.value
    )

    CompositionLocalProvider(LocalWeComposeColors provides colors) {
        MaterialTheme(
            shapes = shapes,
            content = content
        )
    }
}