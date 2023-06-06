package com.smsparatodos.smsparatodos.util

import android.widget.Toast
import androidx.annotation.IntRange
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

/**
 * Created by Irvin Rosas on June 29, 2020
 */

/**
 * The `fragment` is added to the container view with id `frameId`. The operation is
 * performed by the `fragmentManager`.
 */
fun AppCompatActivity.replaceFragmentInActivity(fragment: Fragment, frameId: Int) {
    supportFragmentManager.transact {
        replace(frameId, fragment)
    }
}

/**
 * Runs a FragmentTransaction, then calls commit().
 */
private inline fun FragmentManager.transact(action: FragmentTransaction.() -> Unit) {
    beginTransaction().apply {
        action()
    }.commit()
}

fun AppCompatActivity.executePermissionRequest(
    @NonNull permissions: Array<String>,
    @IntRange(from = 0) requestCode: Int
) {
    ActivityCompat.requestPermissions(this, permissions, requestCode)
}

fun AppCompatActivity.toast(message: String, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, length).show()
}