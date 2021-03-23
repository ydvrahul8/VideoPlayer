package com.example.videoplayertest.base

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import java.text.FieldPosition

abstract class BaseViewHolder<T>(itemView: ViewDataBinding, position: Integer? = null) :
    RecyclerView.ViewHolder(itemView.root) {
    abstract fun bind(item: T, position: Int? = null);
}