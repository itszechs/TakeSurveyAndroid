package zechs.takesurvey.ui.result.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import zechs.takesurvey.R
import zechs.takesurvey.databinding.ItemOptionBinding

class OptionsAdapter : ListAdapter<ItemOption, OptionsViewHolder>(OptionItemDiffCallback()) {

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): OptionsViewHolder {
        return OptionsViewHolder(
            itemBinding = ItemOptionBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    override fun onBindViewHolder(
        holder: OptionsViewHolder,
        position: Int
    ) {
        val item = getItem(position)
        holder.bind(item)
    }

    override fun getItemViewType(
        position: Int
    ) = R.layout.item_option

}
