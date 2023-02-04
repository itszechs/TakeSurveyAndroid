package zechs.takesurvey.ui.result.adapter

import androidx.recyclerview.widget.DiffUtil

class OptionItemDiffCallback : DiffUtil.ItemCallback<ItemOption>() {

    override fun areItemsTheSame(
        oldItem: ItemOption,
        newItem: ItemOption
    ): Boolean = oldItem.title == newItem.title

    override fun areContentsTheSame(
        oldItem: ItemOption, newItem: ItemOption
    ) = oldItem == newItem

}