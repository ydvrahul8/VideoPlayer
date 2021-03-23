package com.example.videoplayertest.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.videoplayertest.R
import com.example.videoplayertest.base.BaseViewHolder
import com.example.videoplayertest.base.OnClickHandler
import com.example.videoplayertest.databinding.ErrorLayoutBinding
import com.example.videoplayertest.databinding.ItemVideoBinding
import com.example.videoplayertest.databinding.LoadingLayoutBinding
import com.example.videoplayertest.model.VideoFileModel
import com.example.videoplayertest.utils.*
import javax.inject.Inject

class VideoThumbnailAdapter @Inject constructor(val onClickHandler: OnClickHandler) :
    RecyclerView.Adapter<BaseViewHolder<*>>(), Filterable {


    private val list = ArrayList<VideoFileModel>()
    private val fullVideoList = ArrayList<VideoFileModel>()

    public fun setData(list: ArrayList<VideoFileModel>) {
        this.list.clear()
        this.list.addAll(list)
        fullVideoList.clear()
        fullVideoList.addAll(list)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemViewType(position: Int): Int {
        return when {
            list[position].fileName.equals(LOADING, true) -> {
                LOADING_VIEW
            }
            list[position].fileName.equals(ERROR, true) -> {
                ERROR_VIEW
            }
            else -> DATA_VIEW
        }

    }

    private fun isLoading(): Boolean {
        if (list.size > 0) {
            if (list[0].fileName.equals(LOADING, true))
                return true
        }
        return false
    }

    private fun isError(): Boolean {
        if (list.size > 0) {
            if (list[0].fileName.equals(ERROR, true))
                return true
        }
        return false
    }

    public fun displayLoading() {
        if (!isLoading()) {
            setTag(LOADING)
        }
    }

    public fun displayError(string: String) {
        if (!isError()) {
            setTag(ERROR)
        }
    }

    private fun setTag(string: String) {
        val tag = VideoFileModel("", "", string)
        val list = ArrayList<VideoFileModel>()
        list.add(tag)
        setData(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        return when (viewType) {
            LOADING_VIEW -> {
                val view = LayoutInflater.from(parent.context)
                val binding = DataBindingUtil.inflate<LoadingLayoutBinding>(
                    view,
                    R.layout.loading_layout,
                    parent,
                    false
                )
                return LoadingViewHolder(binding)
            }
            ERROR_VIEW -> {
                val view = LayoutInflater.from(parent.context)
                val binding = DataBindingUtil.inflate<ErrorLayoutBinding>(
                    view,
                    R.layout.error_layout,
                    parent,
                    false
                )
                return ErrorViewHolder(binding)
            }
            DATA_VIEW -> {
                val view = LayoutInflater.from(parent.context)
                val binding = DataBindingUtil.inflate<ItemVideoBinding>(
                    view,
                    R.layout.item_video,
                    parent,
                    false
                )
                return VideoViewHolder(binding)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        val item = list[position]
        when (holder) {
            is LoadingViewHolder -> holder.bind("item" as String)
            is ErrorViewHolder -> holder.bind(item.fileName as String)
            is VideoViewHolder -> holder.bind(item, position)
        }
    }

    override fun getFilter(): Filter {
        return myFilter
    }

    private val myFilter: Filter = object : Filter() {
        override fun performFiltering(constraint: CharSequence): FilterResults {
            val filteredList: MutableList<VideoFileModel> = ArrayList()
            if (constraint.isEmpty()) {
                filteredList.addAll(fullVideoList)
            } else {
                val filterPattern =
                    constraint.toString().toLowerCase().trim { it <= ' ' }
                for (item in fullVideoList) {
                    if (item.fileName.toLowerCase().contains(filterPattern)) {
                        filteredList.add(item)
                    }
                }
            }
            val results = FilterResults()
            results.values = filteredList
            return results
        }

        override fun publishResults(
            constraint: CharSequence,
            results: FilterResults
        ) {
            list.clear()
            list.addAll(results.values as List<VideoFileModel>)
            notifyDataSetChanged()
        }
    }

    inner class VideoViewHolder(private val binding: ItemVideoBinding) :
        BaseViewHolder<VideoFileModel>(binding) {
        override fun bind(item: VideoFileModel, position: Int?) {
            binding.videoFileModel = item
            binding.listener = onClickHandler
            binding.position = position
        }
    }
}