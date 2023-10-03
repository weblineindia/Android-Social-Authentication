package com.example.authlib.google

import com.google.android.gms.tasks.Task

interface GoogleLogOutCallback {
    fun onSignOut(task: Task<Void>)
}