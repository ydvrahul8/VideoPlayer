package com.example.videoplayertest.view.adapter

import androidx.databinding.ViewDataBinding
import com.example.videoplayertest.base.BaseViewHolder
import com.example.videoplayertest.databinding.ErrorLayoutBinding

class ErrorViewHolder(private val view: ViewDataBinding) : BaseViewHolder<String>(view) {
    override fun bind(data: String, position: Int?) {
        val binding = view as ErrorLayoutBinding
        binding.string = data
    }
}