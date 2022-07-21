package com.example.ijptask1

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.SeekBar
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ijptask1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), Adapter2.OnClick2{

    private lateinit var binding : ActivityMainBinding
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var adapter2: Adapter2
    private lateinit var firstModel : SeekbarModel
    private lateinit var list : ArrayList<SeekbarModel>

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //initialising list
        firstModel = SeekbarModel(0, 100)
        list = ArrayList()

        list.add(firstModel)

        layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        adapter2 = Adapter2(list,this, this)
        binding.recyclerView.adapter = adapter2
        binding.recyclerView.layoutManager = layoutManager
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onClick(currentProgress: Int, rightValue: Int, position: Int, model: SeekbarModel) {
        val newModel = SeekbarModel(currentProgress + 1, rightValue)
        adapter2.addItem(newModel, position + 1, model)
    }

    override fun removeModel(prevModel: SeekbarModel, position: Int) {
        adapter2.removeItem(prevModel, position)
    }

    override fun removefirstModel() {
        val newModel = SeekbarModel(0, 100)
        adapter2.removeFirstItem(newModel)
    }
}