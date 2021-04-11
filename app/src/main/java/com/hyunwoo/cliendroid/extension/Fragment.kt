package com.hyunwoo.cliendroid.extension

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.fragment.findNavController

fun Fragment.navigateGraph(
    @IdRes actionId: Int,
    args: Bundle? = null,
    navOptions: NavOptions? = null,
    navigatorExtras: Navigator.Extras? = null
): Boolean {
    val navController = findNavController()
    return if (navController.currentDestination?.getAction(actionId) != null) {
        navController.navigate(actionId, args, navOptions, navigatorExtras)
        true
    } else {
        false
    }
}
