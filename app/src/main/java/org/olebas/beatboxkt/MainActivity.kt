package org.olebas.beatboxkt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.olebas.beatboxkt.databinding.ActivityMainBinding
import org.olebas.beatboxkt.databinding.ListItemSoundBinding

class MainActivity : AppCompatActivity() {

    private lateinit var speedTextView: TextView
    private lateinit var speedSeekBar: SeekBar

    private val beatBoxViewModel: BeatBoxViewModel by lazy {
        ViewModelProvider(this).get(BeatBoxViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (beatBoxViewModel.beatBox == null) {
            beatBoxViewModel.beatBox = BeatBox(assets)
        }

        val binding: ActivityMainBinding = DataBindingUtil
                .setContentView(this, R.layout.activity_main)

        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(context, 3)
            adapter = SoundAdapter(beatBoxViewModel.beatBox!!.sounds)
        }

        speedTextView = binding.playbackSpeed
        speedTextView.text = getString(R.string.playback_speed_text, (beatBoxViewModel.beatBox?.playbackSpeed?.toInt()?.times(100)).toString())
        speedSeekBar = binding.speedSeekBar

        speedSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                beatBoxViewModel.beatBox?.playbackSpeed = progress.toFloat() / 100
                speedTextView.text = getString(R.string.playback_speed_text, progress.toString())
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

        })
    }

    private inner class SoundHolder(private val binding: ListItemSoundBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.viewModel = SoundViewModel(beatBoxViewModel.beatBox!!)
        }

        fun bind(sound: Sound) {
            binding.apply {
                viewModel?.sound = sound
                executePendingBindings()
            }
        }
    }

    private inner class SoundAdapter(private val sounds: List<Sound>) : RecyclerView.Adapter<SoundHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SoundHolder {
            val binding = DataBindingUtil.inflate<ListItemSoundBinding>(
                    layoutInflater,
                    R.layout.list_item_sound,
                    parent,
                    false
            )
            return SoundHolder(binding)
        }

        override fun onBindViewHolder(holder: SoundHolder, position: Int) {
            val sound = sounds[position]
            holder.bind(sound)
        }

        override fun getItemCount(): Int = sounds.size

    }
}