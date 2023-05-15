package com.dnovaes.csgolive.matches.common.ui.view

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
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
        ) as ViewGroup
        return MatchesItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: MatchesItemViewHolder, position: Int) {
        val item = matches[position]
        item.league.imageUrl?.let {
            holder.leagueImgLogoView.setImageURI(Uri.parse(it))
        }
        holder.matchTimeTextView.text = item.beginAt
        //holder.matchTimeBackgroundView.text = item.beginAt

        holder.team1TextView.text = item.opponents.getItemNameOrDefault(0)
        holder.team2TextView.text = item.opponents.getItemNameOrDefault(1)

        holder.leagueTitleView.text = item.league.name
        holder.serieTitleView.text = " - ${item.serie.name}"
    }

    fun update(items: List<MatchResponse>) {
        this.matches = items
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return matches.size
    }

}

class MatchesItemViewHolder(itemLayout: ViewGroup) : RecyclerView.ViewHolder(itemLayout) {
    val matchTimeTextView: TextView = itemLayout.findViewById(R.id.match_time_textview)
    val matchTimeBackgroundView: ImageView = itemLayout.findViewById(R.id.match_time_background_img)

    val team1TextView: TextView = itemLayout.findViewById(R.id.match_team1_textview)
    val team2TextView: TextView = itemLayout.findViewById(R.id.match_team2_textview)

    val leagueImgLogoView: ImageView = itemLayout.findViewById(R.id.match_league_logo_img)
    val leagueTitleView: TextView = itemLayout.findViewById(R.id.match_league_title)
    val serieTitleView: TextView = itemLayout.findViewById(R.id.match_serie_title)
}
