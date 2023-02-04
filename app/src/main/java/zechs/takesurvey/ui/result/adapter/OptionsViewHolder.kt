package zechs.takesurvey.ui.result.adapter

import androidx.recyclerview.widget.RecyclerView
import zechs.takesurvey.databinding.ItemOptionBinding

class OptionsViewHolder(
    private val itemBinding: ItemOptionBinding
) : RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(item: ItemOption) {
        val votes = if (item.vote > 1) {
            "${item.vote} votes"
        } else "${item.vote} vote"

        val percentageText = "${item.percentage}%"

        itemBinding.apply {
            tvOption.text = item.title
            chipVote.text = votes
            percentage.progress = item.percentage
            this.percentageText.text = percentageText
        }
    }

}