package com.example.boxgame

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.boxgame.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.next.setOnClickListener {
            val number = binding.textNumber.text.toString().toIntOrNull()

            if(number != null){
                if(number <5){
                    Toast.makeText(this@MainActivity, "Minimum Input should be 5.", Toast.LENGTH_SHORT).show()
                }else{
                    if(number >= 200){
                        Toast.makeText(this@MainActivity, "Maximum Input should be 200.", Toast.LENGTH_SHORT).show()
                    }else{
                        val intent = Intent(this, GameActivity::class.java)
                        intent.putExtra("number", number)
                        startActivity(intent)
                    }
                }
            }else{
                Toast.makeText(this@MainActivity, "Please enter a valid number!", Toast.LENGTH_SHORT).show()
            }
        }

    }
}