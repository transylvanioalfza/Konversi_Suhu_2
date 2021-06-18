package org.d3if4118.assessment2.ui.news

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import org.d3if4118.assessment2.databinding.FragmentDetailNewsBinding
import org.d3if4118.assessment2.utils.DATA_NEWS

class DetailNewsFragment : Fragment() {
    lateinit var binding: FragmentDetailNewsBinding
    val item = DATA_NEWS
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailNewsBinding.inflate(layoutInflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvJudulnews.text = item.title
        binding.tvSource.text = item.source!!.name
        binding.tvDeskripsi.text = item.description
        binding.btButtonnews.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(item.url)
            startActivity(intent)
        }
        if (item.author == "") {
            binding.tvAuthor.visibility = View.GONE
        } else {
            binding.tvAuthor.text = item.author
        }
        val requestOptions = RequestOptions()
        Glide.with(requireActivity()).load(item.urlToImage)
            .apply(requestOptions.frame(1))
            .into(binding.ivNewsDetail)
    }
}