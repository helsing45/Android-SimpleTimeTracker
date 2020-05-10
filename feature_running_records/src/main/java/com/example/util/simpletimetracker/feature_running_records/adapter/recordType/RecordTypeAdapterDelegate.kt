package com.example.util.simpletimetracker.feature_running_records.adapter.recordType

import android.view.ViewGroup
import com.example.util.simpletimetracker.core.adapter.BaseRecyclerAdapterDelegate
import com.example.util.simpletimetracker.core.adapter.BaseRecyclerViewHolder
import com.example.util.simpletimetracker.core.adapter.ViewHolderType
import com.example.util.simpletimetracker.feature_running_records.R
import kotlinx.android.synthetic.main.record_type_item_layout.view.*

class RecordTypeAdapterDelegate(
    private val onItemClick: ((RecordTypeViewData) -> Unit),
    private val onItemLongClick: ((RecordTypeViewData) -> Unit)
) : BaseRecyclerAdapterDelegate() {

    override fun onCreateViewHolder(parent: ViewGroup): BaseRecyclerViewHolder =
        RunningRecordsViewHolder(parent)

    inner class RunningRecordsViewHolder(parent: ViewGroup) :
        BaseRecyclerViewHolder(parent, R.layout.record_type_item_layout) {

        override fun bind(item: ViewHolderType) = with(itemView.viewRecordTypeItem) {
            item as RecordTypeViewData

            setColor(item.color)
            setIcon(item.iconId)
            setName(item.name)
            setOnClickListener {
                onItemClick.invoke(item)
            }
            setOnLongClickListener {
                onItemLongClick.invoke(item)
                true
            }
        }
    }
}