package com.example.myapplication.ui.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.Adapters.CatAdapter
import com.example.myapplication.MainViewModel
import com.example.myapplication.R
import com.example.myapplication.ui.NetworkResult
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_main.view.*


@AndroidEntryPoint
class MainFragment : Fragment() {

    private lateinit var mainViewModel: MainViewModel
    private val mAdapter by lazy {CatAdapter()}
    private lateinit var mView: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_main, container, false)

        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)


        setupRecyclerView()
        requestApiData()

        return mView
    }

    private fun requestApiData() {
        mainViewModel.getCat(applyQueries())
        mainViewModel.catResponse.observe(viewLifecycleOwner, { response ->
            when (response) {
                is NetworkResult.Success -> {
                    hideShimmerEffect()
                    response.data?.let { mAdapter.setData(it) }
                }
                is NetworkResult.Error -> {
                    hideShimmerEffect()
                    Toast.makeText(
                        requireContext(),
                        response.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }

                is NetworkResult.Loading -> {
                    showShimmerEffect()
                }
            }

        })
    }

    private fun applyQueries(): HashMap<String, String> {
        val queries : HashMap<String, String> = HashMap()

        queries["id"] = "2"
        queries["url"] = "https://thatcopy.github.io/catAPI/imgs/jpg/2039314.jpg"
        queries["webpurl"] = "https://thatcopy.github.io/catAPI/imgs/webp/2039314.webp"
        queries["x"] = "46.65"
        queries["y"] = "59.01"

        return queries
    }
    private fun setupRecyclerView() {
        mView.recyclerview.adapter = mAdapter
        mView.recyclerview.layoutManager = LinearLayoutManager(requireContext())
        showShimmerEffect()
    }

    private fun showShimmerEffect() {
        mView.recyclerview.showShimmer()
    }

    private fun hideShimmerEffect() {
        mView.recyclerview.hideShimmer()
    }

}