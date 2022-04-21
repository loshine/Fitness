package io.github.loshine.fitness

import android.app.Activity
import android.content.Intent

fun Activity.startActivity(clazz: Class<*>) = startActivity(Intent(this, clazz))