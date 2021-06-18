package org.d3if4118.assessment2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import coil.load
import org.d3if4118.assessment2.databinding.ActivityNewsDetailBinding
import org.d3if4118.assessment2.model.News
import org.d3if4118.assessment2.utils.Constant.EXTRA_PARCELABLE

class NewsDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNewsDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        val news = intent.getParcelableExtra<News>(EXTRA_PARCELABLE)
//        news?.let {
////            binding.image.load(it.image)
////            binding.textTitle.text = it.title
////            binding.textDesc.text = it.desc
//        }
    }
}