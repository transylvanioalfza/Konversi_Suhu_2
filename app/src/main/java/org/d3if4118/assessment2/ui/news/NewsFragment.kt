package org.d3if4118.assessment2.ui.news

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_rv_news.view.*
import org.d3if4118.assessment2.NewsDetailActivity
import org.d3if4118.assessment2.R
import org.d3if4118.assessment2.databinding.FragmentNewsBinding
import org.d3if4118.assessment2.model.Articles
import org.d3if4118.assessment2.network.ApiStatus
import org.d3if4118.assessment2.utils.AdapterUtil
import org.d3if4118.assessment2.utils.Constant.EXTRA_PARCELABLE
import org.d3if4118.assessment2.utils.DATA_NEWS

class NewsFragment : Fragment() {
    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: AdapterUtil<Articles>
    private lateinit var viewModel: NewsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this).get(NewsViewModel::class.java)
        _binding = FragmentNewsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getData().observe(viewLifecycleOwner, {

            adapter = AdapterUtil(R.layout.item_rv_news, it, { position, itemView, item ->
                itemView.tvTitle.text = item.title

                Glide.with(requireContext())
                    .load(item.urlToImage)
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .dontAnimate()
                    .into(itemView.ivNews)

                //view image
            }, { _, item ->
                DATA_NEWS = item
                findNavController().navigate(R.id.action_navigation_news_to_detailNewsFragment)
            })

            binding.rvNews.apply {
                this.layoutManager = LinearLayoutManager(requireContext())
                this.adapter = this@NewsFragment.adapter
            }
        })
        //untuk view model progress bar nya
        viewModel.getStatus().observe(requireActivity(), {

            //it adalah data dari view model
            updateProgress(it)
        })

    }

    private fun updateProgress(status: ApiStatus) {
        when (status) {
            ApiStatus.LOADING -> {
                binding.progressBar.visibility = View.VISIBLE
            }
            ApiStatus.SUCCESS -> {
                binding.progressBar
                    .visibility = View.GONE
            }
            ApiStatus.FAILED -> {
                binding.progressBar.visibility = View.GONE
                binding.networkError.visibility = View.VISIBLE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}