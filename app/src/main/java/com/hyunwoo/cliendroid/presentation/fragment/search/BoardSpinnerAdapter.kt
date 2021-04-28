package com.hyunwoo.cliendroid.presentation.fragment.search

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.hyunwoo.cliendroid.R
import com.hyunwoo.cliendroid.domain.model.Board

class BoardSpinnerAdapter(
    context: Context,
) : ArrayAdapter<Board>(context, R.layout.item_board_spinner) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val textView = super.getView(position, convertView, parent) as TextView
        textView.text = getItem(position)?.name
        return textView
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val textView = super.getDropDownView(position, convertView, parent) as TextView
        textView.text = getItem(position)?.name
        return textView
    }

    override fun getItem(position: Int): Board? {
        return super.getItem(position)
    }
}
