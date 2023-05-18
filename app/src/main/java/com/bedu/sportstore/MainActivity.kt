package com.bedu.sportstore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bedu.sportstore.databinding.ActivityMainBinding
import com.bedu.sportstore.ui.fragments.main.FormaPagoFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(Home())

        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> replaceFragment(Home())
                R.id.search -> replaceFragment(Search())
                R.id.car -> replaceFragment(Car())
                R.id.settings -> replaceFragment(Settings())
                else -> Toast.makeText(this, getString(R.string.not_item_selected), Toast.LENGTH_SHORT).show()

            }

            true
        }
        /*binding.bottomNavigationView.setOnItemReselectedListener {

            when(it.itemId){

                R.id.home -> replaceFragment(Home())
                R.id.search -> replaceFragment(Search())
                R.id.car -> replaceFragment(Car())
                R.id.settings -> replaceFragment(Settings())

                else ->{

                }
            }
        }*/



    }

    private fun replaceFragment(fragment : Fragment){

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_Layout,fragment)
        fragmentTransaction.commit()

    }
}