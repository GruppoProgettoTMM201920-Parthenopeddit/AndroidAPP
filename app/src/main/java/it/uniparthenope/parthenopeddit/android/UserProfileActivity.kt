package it.uniparthenope.parthenopeddit.android

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import it.uniparthenope.parthenopeddit.LoginRequiredActivity
import it.uniparthenope.parthenopeddit.R
import it.uniparthenope.parthenopeddit.api.requests.UserRequests
import it.uniparthenope.parthenopeddit.model.User

class UserProfileActivity : LoginRequiredActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)
    }
}