package com.bedu.sportstore.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bedu.sportstore.R

class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        //inflar el fragemnt de forma manual
        /*supportFragmentManager.commit {
            setReorderingAllowed(true)
            add(R.id.authFragmentContainer, AuthHomeFragment())
        }*/

    }
}