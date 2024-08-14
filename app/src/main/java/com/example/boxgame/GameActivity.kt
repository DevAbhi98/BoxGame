package com.example.boxgame

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.boxgame.databinding.ActivityGameBinding

class GameActivity : AppCompatActivity() {

    lateinit var binding: ActivityGameBinding
    lateinit var mainViewModel: MainViewModel
    lateinit var adapter: GridAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]

        if (mainViewModel._totalBoxes.value == null) {
            if (intent.extras != null) {
                val number = intent.getIntExtra("number", -1)
                if (number == -1) {
                    Toast.makeText(
                        this@GameActivity,
                        "There was a proble creating the game!",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    mainViewModel.setTotalBoxes(number)
                }
            } else {
                Toast.makeText(
                    this@GameActivity,
                    "There was a proble creating the game!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        adapter = GridAdapter(ArrayList()) {
            Log.i("ItemClicked", "True")
            mainViewModel.selectRandomElement()
            adapter.notifyDataSetChanged()
        }

        binding.mainRecycler.layoutManager = GridLayoutManager(this, 5)
        binding.mainRecycler.adapter = adapter

        mainViewModel._gameFinished.observe(this) {
            if (it) {
                mainViewModel.totalBoxes.postValue(null)
                mainViewModel.gameFinished.postValue(false)
                Toast.makeText(this@GameActivity, "You have successfully completed the Game!", Toast.LENGTH_SHORT).show()
                onBackPressed()
            }
        }

        mainViewModel._totalBoxes.observe(this) {
            if (it != null) {
                mainViewModel.setupList(it)
                adapter.setNewList(mainViewModel.list)
                adapter.notifyDataSetChanged()
            }
        }


    }
}