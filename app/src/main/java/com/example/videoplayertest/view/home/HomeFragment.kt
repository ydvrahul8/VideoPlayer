package com.example.videoplayertest.view.home

import android.content.res.Configuration
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.videoplayertest.R
import com.example.videoplayertest.base.BaseFragment
import com.example.videoplayertest.databinding.FragmentHomeBinding
import com.example.videoplayertest.di.viewmodel.ViewModelProviderFactory
import com.example.videoplayertest.utils.Status
import com.example.videoplayertest.utils.VIDEOLIST
import com.example.videoplayertest.view.adapter.VideoThumbnailAdapter
import kotlinx.android.synthetic.main.fragment_home.*
import javax.inject.Inject


class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    override val layout: Int
        get() = R.layout.fragment_home

    @Inject
    lateinit var factory: ViewModelProviderFactory

    @Inject
    lateinit var adapter: VideoThumbnailAdapter

    private val viewModel: HomeViewModel by activityViewModels { factory }

    override fun init(bind: FragmentHomeBinding) {
        setHasOptionsMenu(true)
        (activity as AppCompatActivity?)!!.setSupportActionBar(toolbar)
        observeVideoList()
        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            val manager = recyclerView.layoutManager as GridLayoutManager
            manager.spanCount = 3
        } else {
            val manager = recyclerView.layoutManager as GridLayoutManager
            manager.spanCount = 2
        }
        observeFilterList()
    }

    private fun observeFilterList() {
        viewModel.filteredList.removeObservers(viewLifecycleOwner)
        viewModel.filteredList.observe(this, androidx.lifecycle.Observer {
            adapter.setData(it)
        })
    }

    private fun observeVideoList() {
        viewModel.videoList.removeObservers(viewLifecycleOwner)
        viewModel.videoList.observe(this, androidx.lifecycle.Observer {
            when (it.status) {
                Status.LOADING -> {
                    adapter?.displayLoading()
                }
                Status.SUCCESS -> {
                    VIDEOLIST = it.data!!
                    val data = it.data
                    for (i in 0 until data?.size!!) {
                        Log.e(
                            "MAinACtivity",
                            "onCreate: videos path --->" + data[i]
                        )
                    }
                    adapter.setData(data)
                    recyclerView.adapter = adapter
                }
                Status.ERROR -> {
                    adapter.displayError(it.message!!)
                }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.home_menu, menu)
        val searchItem: MenuItem = menu.findItem(R.id.action_search)
        val searchView: SearchView = searchItem.actionView as SearchView
        searchView.imeOptions = EditorInfo.IME_ACTION_DONE
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.filterList(newText?.toLowerCase()!!)
                return false
            }
        })
        super.onCreateOptionsMenu(menu, inflater)
    }

}