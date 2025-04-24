package br.ufpa.genewhale.ui.navigation

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.navigation.NavBackStackEntry

private const val TIME_TRANSITION = 500

fun exitTransition(): AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition =
    {
        fadeOut(animationSpec = tween(TIME_TRANSITION)) +
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(TIME_TRANSITION)
                )
    }

fun popExitTransition(): AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition =
    {
        fadeOut(animationSpec = tween(TIME_TRANSITION)) +
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(TIME_TRANSITION)
                )
    }

fun enterTransition(): AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition =
    {
        fadeIn(animationSpec = tween(TIME_TRANSITION)) +
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(TIME_TRANSITION)
                )
    }

fun popEnterTransition(): AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition =
    {
        fadeIn(animationSpec = tween(TIME_TRANSITION)) +
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(TIME_TRANSITION)
                )
    }
