package com.skydoves.pokedex.ui.flow

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.skydoves.pokedex.R
import com.skydoves.pokedex.core.model.ApiUser
import com.skydoves.pokedex.databinding.ItemLayoutBinding
import javax.inject.Inject

class ApiUserAdapter @Inject constructor()
  : BaseQuickAdapter<ApiUser, BaseDataBindingHolder<ItemLayoutBinding>>(R.layout.item_layout) {
  override fun convert(holder: BaseDataBindingHolder<ItemLayoutBinding>, item: ApiUser) {
    holder.dataBinding?.let {
      it.item = item
      it.executePendingBindings()
    }
  }
}