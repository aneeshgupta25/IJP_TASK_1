package com.example.ijptask1

import android.content.Context
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.ijptask1.databinding.ItemviewBinding

class Adapter2(val list: MutableList<SeekbarModel>, val context: Context, val listener: OnClick2) :
    RecyclerView.Adapter<Adapter2.MyViewHolder2>() {

    interface OnClick2 {
        fun onClick(currentProgress: Int, rightValue: Int, position: Int, model: SeekbarModel)
        fun removeModel(prevModel: SeekbarModel, position: Int)
        fun removefirstModel()
    }

    class MyViewHolder2(itemView: ItemviewBinding) : RecyclerView.ViewHolder(itemView.root) {
        var binding: ItemviewBinding = itemView

        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(list: MutableList<SeekbarModel>, listener: OnClick2, position: Int, context: Context) {

            //setting model
            var model : SeekbarModel = list[position]
            binding.leftValue.text = model.left_value.toString()
            binding.rightValue.text = model.right_value.toString()
            binding.seekbar.min = model.left_value
            binding.seekbar.max = model.right_value
            binding.seekbar.progress = model.right_value

            Log.d("Aneesh", position.toString())
            binding.seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
                ) {
                    Log.d("Aneesh", "inside onprogress position $position")
                    binding.rightValue.text = binding.seekbar.progress.toString()
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {

                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {
                    var leftCheck: Boolean = (binding.seekbar.progress - binding.seekbar.min) >= 2
                    var rightCheck: Boolean = (binding.seekbar.max - binding.seekbar.progress) >= 3

                    if (leftCheck && rightCheck) {
                        var newRightValue = model.right_value
                        model.right_value = binding.seekbar.progress
                        listener.onClick(
                            binding.seekbar.progress,
                            newRightValue,
                            position,
                            model
                        )
                    } else if(binding.seekbar.progress == model.left_value){
                        if (position == 0) {
                            listener.removefirstModel()
                        } else {
                            list[position-1].right_value = model.right_value
                            listener.removeModel(list[position-1], position)
                        }
                    } else {
                        binding.rightValue.text = model.right_value.toString()
                        binding.seekbar.progress = model.right_value
                        Toast.makeText(context, "Minimum segment length is 2!", Toast.LENGTH_SHORT).show()
                    }
                }
            })
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder2 {
        return MyViewHolder2(
            ItemviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: MyViewHolder2, position: Int) {
        var model = list[position]
        holder.bind(list, listener, position, context)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun addItem(model: SeekbarModel, position: Int, oldModel: SeekbarModel) {
        list.removeAt(position - 1)
        list.add(position - 1, oldModel)
        list.add(position, model)
        notifyDataSetChanged()
    }

    fun removeItem(prevModel: SeekbarModel, position: Int){
        list.removeAt(position-1)
        list.add(position-1, prevModel)
        list.removeAt(position)
        notifyDataSetChanged()
    }

    fun removeFirstItem(firstModel: SeekbarModel){
        list.clear()
        list.add(firstModel)
        notifyDataSetChanged()
    }

}