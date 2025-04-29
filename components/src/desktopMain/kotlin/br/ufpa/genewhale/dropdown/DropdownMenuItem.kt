package br.ufpa.genewhale.dropdown

import androidx.compose.foundation.Indication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.takeOrElse
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun DropdownMenuItem(
    text: @Composable () -> Unit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    enabled: Boolean = true,
    colors: MenuItemColors = MenuItemColors(
        textColor = MaterialTheme.colorScheme.onSurface,
        leadingIconColor = MaterialTheme.colorScheme.onSurface,
        trailingIconColor = MaterialTheme.colorScheme.onSurface,
        disabledTextColor = MaterialTheme.colorScheme.onSurface.copy(
            alpha = ListTokens.ListItemDisabledLabelTextOpacity
        ),
        disabledLeadingIconColor = MaterialTheme.colorScheme.onSurface.copy(
            alpha = ListTokens.ListItemDisabledLeadingIconOpacity
        ),
        disabledTrailingIconColor = MaterialTheme.colorScheme.onSurface.copy(
            alpha = ListTokens.ListItemDisabledTrailingIconOpacity
        )
    ),
    contentPadding: PaddingValues = PaddingValues(0.dp),
    interactionSource: MutableInteractionSource? = null,
) {
    DropdownMenuItemContent(
        text = text,
        onClick = onClick,
        modifier = modifier,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        enabled = enabled,
        colors = colors,
        contentPadding = contentPadding,
        interactionSource = interactionSource,
    )
}

@Composable
internal fun DropdownMenuItemContent(
    text: @Composable () -> Unit,
    onClick: () -> Unit,
    modifier: Modifier,
    leadingIcon: @Composable (() -> Unit)?,
    trailingIcon: @Composable (() -> Unit)?,
    enabled: Boolean,
    colors: MenuItemColors,
    contentPadding: PaddingValues,
    interactionSource: MutableInteractionSource?
) {
    Row(
        modifier =
            modifier
                .clickable(
                    enabled = enabled,
                    onClick = onClick,
                    interactionSource = interactionSource,
                    indication = rippleOrFallbackImplementation(true)
                )
                .fillMaxWidth()
                // Preferred min and max width used during the intrinsic measurement.
                .sizeIn(
                    minWidth = DropdownMenuItemDefaultMinWidth,
                    maxWidth = DropdownMenuItemDefaultMaxWidth,
                    minHeight = MenuListItemContainerHeight
                )
                .padding(contentPadding),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // TODO(b/271818892): Align menu list item style with general list item style.
        ProvideTextStyle(MaterialTheme.typography.labelLarge) {
            if (leadingIcon != null) {
                CompositionLocalProvider(
                    LocalContentColor provides colors.leadingIconColor(enabled),
                ) {
                    Box(Modifier.defaultMinSize(minWidth = ListTokens.ListItemLeadingIconSize)) {
                        leadingIcon()
                    }
                }
            }
            CompositionLocalProvider(LocalContentColor provides colors.textColor(enabled)) {
                Box(
                    Modifier.weight(1f)
                        .padding(
                            start =
                                if (leadingIcon != null) {
                                    DropdownMenuItemHorizontalPadding
                                } else {
                                    0.dp
                                },
                            end =
                                if (trailingIcon != null) {
                                    DropdownMenuItemHorizontalPadding
                                } else {
                                    0.dp
                                }
                        )
                ) {
                    text()
                }
            }
            if (trailingIcon != null) {
                CompositionLocalProvider(
                    LocalContentColor provides colors.trailingIconColor(enabled)
                ) {
                    Box(Modifier.defaultMinSize(minWidth = ListTokens.ListItemTrailingIconSize)) {
                        trailingIcon()
                    }
                }
            }
        }
    }
}

@Immutable
class MenuItemColors(
    val textColor: Color,
    val leadingIconColor: Color,
    val trailingIconColor: Color,
    val disabledTextColor: Color,
    val disabledLeadingIconColor: Color,
    val disabledTrailingIconColor: Color,
) {

    /**
     * Returns a copy of this MenuItemColors, optionally overriding some of the values. This uses
     * the Color.Unspecified to mean “use the value from the source”
     */
    fun copy(
        textColor: Color = this.textColor,
        leadingIconColor: Color = this.leadingIconColor,
        trailingIconColor: Color = this.trailingIconColor,
        disabledTextColor: Color = this.disabledTextColor,
        disabledLeadingIconColor: Color = this.disabledLeadingIconColor,
        disabledTrailingIconColor: Color = this.disabledTrailingIconColor,
    ) =
        MenuItemColors(
            textColor.takeOrElse { this.textColor },
            leadingIconColor.takeOrElse { this.leadingIconColor },
            trailingIconColor.takeOrElse { this.trailingIconColor },
            disabledTextColor.takeOrElse { this.disabledTextColor },
            disabledLeadingIconColor.takeOrElse { this.disabledLeadingIconColor },
            disabledTrailingIconColor.takeOrElse { this.disabledTrailingIconColor },
        )

    /**
     * Represents the text color for a menu item, depending on its [enabled] state.
     *
     * @param enabled whether the menu item is enabled
     */
    @Stable
    internal fun textColor(enabled: Boolean): Color = if (enabled) textColor else disabledTextColor

    /**
     * Represents the leading icon color for a menu item, depending on its [enabled] state.
     *
     * @param enabled whether the menu item is enabled
     */
    @Stable
    internal fun leadingIconColor(enabled: Boolean): Color =
        if (enabled) leadingIconColor else disabledLeadingIconColor

    /**
     * Represents the trailing icon color for a menu item, depending on its [enabled] state.
     *
     * @param enabled whether the menu item is enabled
     */
    @Stable
    internal fun trailingIconColor(enabled: Boolean): Color =
        if (enabled) trailingIconColor else disabledTrailingIconColor

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || other !is MenuItemColors) return false

        if (textColor != other.textColor) return false
        if (leadingIconColor != other.leadingIconColor) return false
        if (trailingIconColor != other.trailingIconColor) return false
        if (disabledTextColor != other.disabledTextColor) return false
        if (disabledLeadingIconColor != other.disabledLeadingIconColor) return false
        if (disabledTrailingIconColor != other.disabledTrailingIconColor) return false

        return true
    }

    override fun hashCode(): Int {
        var result = textColor.hashCode()
        result = 31 * result + leadingIconColor.hashCode()
        result = 31 * result + trailingIconColor.hashCode()
        result = 31 * result + disabledTextColor.hashCode()
        result = 31 * result + disabledLeadingIconColor.hashCode()
        result = 31 * result + disabledTrailingIconColor.hashCode()
        return result
    }
}


@Suppress("DEPRECATION_ERROR")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun rippleOrFallbackImplementation(
    bounded: Boolean = true,
    radius: Dp = Dp.Unspecified,
    color: Color = Color.Unspecified
): Indication {
    return if (LocalUseFallbackRippleImplementation.current) {
        androidx.compose.material.ripple.rememberRipple(bounded, radius, color)
    } else {
        ripple(bounded, radius, color)
    }
}

internal object ListTokens {
    val DividerLeadingSpace = 16.0.dp
    val DividerTrailingSpace = 16.0.dp
    val FocusIndicatorColor = ColorSchemeKeyTokens.Secondary
    val ListItemContainerColor = ColorSchemeKeyTokens.Surface
    val ListItemContainerElevation = ElevationTokens.Level0
    val ListItemContainerShape = ShapeKeyTokens.CornerNone
    val ListItemDisabledLabelTextColor = ColorSchemeKeyTokens.OnSurface
    val ListItemDisabledLabelTextOpacity = 0.38f
    val ListItemDisabledLeadingIconColor = ColorSchemeKeyTokens.OnSurface
    val ListItemDisabledLeadingIconOpacity = 0.38f
    val ListItemDisabledTrailingIconColor = ColorSchemeKeyTokens.OnSurface
    val ListItemDisabledTrailingIconOpacity = 0.38f
    val ListItemDraggedContainerElevation = ElevationTokens.Level4
    val ListItemDraggedLabelTextColor = ColorSchemeKeyTokens.OnSurface
    val ListItemDraggedLeadingIconColor = ColorSchemeKeyTokens.OnSurfaceVariant
    val ListItemDraggedTrailingIconColor = ColorSchemeKeyTokens.OnSurfaceVariant
    val ListItemFocusLabelTextColor = ColorSchemeKeyTokens.OnSurface
    val ListItemFocusLeadingIconColor = ColorSchemeKeyTokens.OnSurfaceVariant
    val ListItemFocusTrailingIconColor = ColorSchemeKeyTokens.OnSurfaceVariant
    val ListItemHoverLabelTextColor = ColorSchemeKeyTokens.OnSurface
    val ListItemHoverLeadingIconColor = ColorSchemeKeyTokens.OnSurfaceVariant
    val ListItemHoverTrailingIconColor = ColorSchemeKeyTokens.OnSurfaceVariant
    val ListItemLabelTextColor = ColorSchemeKeyTokens.OnSurface
    val ListItemLabelTextFont = TypographyKeyTokens.BodyLarge
    val ListItemLargeLeadingVideoHeight = 69.0.dp
    val ListItemLeadingAvatarColor = ColorSchemeKeyTokens.PrimaryContainer
    val ListItemLeadingAvatarLabelColor = ColorSchemeKeyTokens.OnPrimaryContainer
    val ListItemLeadingAvatarLabelFont = TypographyKeyTokens.TitleMedium
    val ListItemLeadingAvatarShape = ShapeKeyTokens.CornerFull
    val ListItemLeadingAvatarSize = 40.0.dp
    val ListItemLeadingIconColor = ColorSchemeKeyTokens.OnSurfaceVariant
    val ListItemLeadingIconSize = 24.0.dp
    val ListItemLeadingImageHeight = 56.0.dp
    val ListItemLeadingImageShape = ShapeKeyTokens.CornerNone
    val ListItemLeadingImageWidth = 56.0.dp
    val ListItemLeadingSpace = 16.0.dp
    val ListItemLeadingVideoShape = ShapeKeyTokens.CornerNone
    val ListItemLeadingVideoWidth = 100.0.dp
    val ListItemOneLineContainerHeight = 56.0.dp
    val ListItemOverlineColor = ColorSchemeKeyTokens.OnSurfaceVariant
    val ListItemOverlineFont = TypographyKeyTokens.LabelSmall
    val ListItemPressedLabelTextColor = ColorSchemeKeyTokens.OnSurface
    val ListItemPressedLeadingIconColor = ColorSchemeKeyTokens.OnSurfaceVariant
    val ListItemPressedTrailingIconColor = ColorSchemeKeyTokens.OnSurfaceVariant
    val ListItemSelectedTrailingIconColor = ColorSchemeKeyTokens.Primary
    val ListItemSmallLeadingVideoHeight = 56.0.dp
    val ListItemSupportingTextColor = ColorSchemeKeyTokens.OnSurfaceVariant
    val ListItemSupportingTextFont = TypographyKeyTokens.BodyMedium
    val ListItemThreeLineContainerHeight = 88.0.dp
    val ListItemTrailingIconColor = ColorSchemeKeyTokens.OnSurfaceVariant
    val ListItemTrailingIconSize = 24.0.dp
    val ListItemTrailingSpace = 16.0.dp
    val ListItemTrailingSupportingTextColor = ColorSchemeKeyTokens.OnSurfaceVariant
    val ListItemTrailingSupportingTextFont = TypographyKeyTokens.LabelSmall
    val ListItemTwoLineContainerHeight = 72.0.dp
    val ListItemUnselectedTrailingIconColor = ColorSchemeKeyTokens.OnSurface
}

internal enum class ColorSchemeKeyTokens {
    Background,
    Error,
    ErrorContainer,
    InverseOnSurface,
    InversePrimary,
    InverseSurface,
    OnBackground,
    OnError,
    OnErrorContainer,
    OnPrimary,
    OnPrimaryContainer,
    OnPrimaryFixed,
    OnPrimaryFixedVariant,
    OnSecondary,
    OnSecondaryContainer,
    OnSecondaryFixed,
    OnSecondaryFixedVariant,
    OnSurface,
    OnSurfaceVariant,
    OnTertiary,
    OnTertiaryContainer,
    OnTertiaryFixed,
    OnTertiaryFixedVariant,
    Outline,
    OutlineVariant,
    Primary,
    PrimaryContainer,
    PrimaryFixed,
    PrimaryFixedDim,
    Scrim,
    Secondary,
    SecondaryContainer,
    SecondaryFixed,
    SecondaryFixedDim,
    Surface,
    SurfaceBright,
    SurfaceContainer,
    SurfaceContainerHigh,
    SurfaceContainerHighest,
    SurfaceContainerLow,
    SurfaceContainerLowest,
    SurfaceDim,
    SurfaceTint,
    SurfaceVariant,
    Tertiary,
    TertiaryContainer,
    TertiaryFixed,
    TertiaryFixedDim,
}

internal enum class TypographyKeyTokens {
    BodyLarge,
    BodyMedium,
    BodySmall,
    DisplayLarge,
    DisplayMedium,
    DisplaySmall,
    HeadlineLarge,
    HeadlineMedium,
    HeadlineSmall,
    LabelLarge,
    LabelMedium,
    LabelSmall,
    TitleLarge,
    TitleMedium,
    TitleSmall,
}

internal enum class ShapeKeyTokens {
    CornerExtraLarge,
    CornerExtraLargeTop,
    CornerExtraSmall,
    CornerExtraSmallTop,
    CornerFull,
    CornerLarge,
    CornerLargeEnd,
    CornerLargeTop,
    CornerMedium,
    CornerNone,
    CornerSmall,
}

internal object ElevationTokens {
    val Level0 = 0.0.dp
    val Level1 = 1.0.dp
    val Level2 = 3.0.dp
    val Level3 = 6.0.dp
    val Level4 = 8.0.dp
    val Level5 = 12.0.dp
}


