package com.bedu.sportstore.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.bedu.sportstore.R
import com.bedu.sportstore.ui.fragments.auth.AuthHomeFragment

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