package com.dnovaes.csgolive.matches.common.ui.view

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dnovaes.csgolive.R
import com.dnovaes.csgolive.matches.common.data.model.MatchResponse
import com.dnovaes.csgolive.matches.common.data.model.getItemNameOrDefault

class MatchesAdapter(
    private var matches: List<MatchResponse>
): RecyclerView.Adapter<MatchesItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchesItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.recycler_view_match_layout_item,
            parent,
            false
        ) as LinearLayout
        return MatchesItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: MatchesItemViewHolder, position: Int) {
        val item = matches[position]
        holder.team1TitleView.text = item.opponents.getItemNameOrDefault(0)
        holder.team2TitleView.text = item.opponents.getItemNameOrDefault(1)
    }

    fun update(items: List<MatchResponse>) {
        this.matches = items
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return matches.size
    }

}

class MatchesItemViewHolder(itemLayout: LinearLayout) : RecyclerView.ViewHolder(itemLayout) {
    val team1TitleView: TextView = itemLayout.findViewById(R.id.match_team1_title)
    val team2TitleView: TextView = itemLayout.findViewById(R.id.match_team2_title)
}