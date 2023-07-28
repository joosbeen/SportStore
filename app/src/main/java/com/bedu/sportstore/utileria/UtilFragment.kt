package com.bedu.sportstore.utileria

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.bedu.sportstore.R

class UtilFragment {

    fun replaceFragmetnMain(supportFragmentManager: FragmentManager, fragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.nav_host_fragment_main, fragment)
        fragmentTransaction.commit()
    }

}