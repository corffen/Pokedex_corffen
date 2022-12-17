package com.skydoves.pokedex.ui.flow

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.skydoves.pokedex.R
import com.skydoves.pokedex.databinding.ItemButtonBinding
import com.skydoves.pokedex.navigator.Screens
import javax.inject.Inject

/**
 *
 *
 * @author : Gordon
 * @date : 2022/12/17
 */
class ButtonAdapter @Inject constructor() : BaseQuickAdapter<Screens, BaseDataBindingHolder<ItemButtonBinding>>(R.layout.item_button) {

  override fun convert(holder: BaseDataBindingHolder<ItemButtonBinding>, item: Screens) {
    holder.dataBinding?.let {
      it.item = item
      it.executePendingBindings()
    }
  }
}