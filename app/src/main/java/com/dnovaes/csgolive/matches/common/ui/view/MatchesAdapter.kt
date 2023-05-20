package com.dnovaes.csgolive.matches.common.ui.view

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.dnovaes.csgolive.R
import com.dnovaes.csgolive.common.utilities.extensions.getMatchTimeLabel
import com.dnovaes.csgolive.matches.common.data.model.MatchGameStatus
import com.dnovaes.csgolive.matches.common.data.model.MatchLeagueResponse
import com.dnovaes.csgolive.matches.common.data.model.MatchOpponentGroupResponse
import com.dnovaes.csgolive.matches.common.data.model.MatchResponse
import com.dnovaes.csgolive.matches.common.data.model.MatchSerieResponse
import com.dnovaes.csgolive.matches.common.data.model.getImageUrlOrNull
import com.dnovaes.csgolive.matches.common.data.model.getItemNameOrDefault
import java.time.LocalDateTime

class MatchesAdapter(
    private var matches: List<MatchResponse>,
    private val onItemClick: (Int, List<Int>) -> Unit
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
        holder.bindItemClick(item, onItemClick)
        holder.bindLeagueInfo(item.league)
        holder.bindTimeInfo(item)
        holder.bindTeamInfo(item.opponents)
        holder.bindSerieInfo(item.serie)
    }


    fun update(items: List<MatchResponse>) {
        this.matches = items
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return matches.size
    }

}

class MatchesItemViewHolder(private val itemLayout: ViewGroup) : RecyclerView.ViewHolder(itemLayout) {
    private val matchTimeTextView: TextView = itemLayout.findViewById(R.id.match_time_textview)
    private val matchTimeBackgroundView: ImageView = itemLayout.findViewById(R.id.match_time_background_img)

    private val team1TextView: TextView = itemLayout.findViewById(R.id.match_team1_textview)
    private val team1ImgView: ImageView = itemLayout.findViewById(R.id.match_team1_imgview)
    private val team2TextView: TextView = itemLayout.findViewById(R.id.match_team2_textview)
    private val team2ImgView: ImageView = itemLayout.findViewById(R.id.match_team2_imgview)

    private val leagueTitleView: TextView = itemLayout.findViewById(R.id.match_league_title)
    private val leagueImgLogoView: ImageView = itemLayout.findViewById(R.id.match_league_logo_img)
    private val serieTitleView: TextView = itemLayout.findViewById(R.id.match_serie_title)

    fun bindItemClick(item: MatchResponse, onItemClick: (Int, List<Int>) -> Unit) {
        val teamIds = mutableListOf<Int>()
        item.opponents.forEach {
            teamIds.add(it.opponent.id.toInt())
        }
        itemLayout.setOnClickListener {
            println("log teamIds for the item selected: $teamIds")
            if (teamIds.size == 2) {
                onItemClick(item.id.toInt(), teamIds)
            }
        }
    }

    fun bindLeagueInfo(league: MatchLeagueResponse) {
        leagueTitleView.text = league.name
        league.imageUrl?.let { url ->
            leagueImgLogoView.load(url) {
                crossfade(true)
                error(R.drawable.match_img_placeholder)
            }
        } ?: leagueImgLogoView.load(R.drawable.match_img_placeholder)
    }

    fun bindTeamInfo(opponents: List<MatchOpponentGroupResponse>) {
        team1TextView.text = opponents.getItemNameOrDefault(0)
        opponents.getImageUrlOrNull(0)?.let { url ->
            team1ImgView.load(url) {
                crossfade(true)
                error(R.drawable.match_img_placeholder)
            }
        } ?: team1ImgView.load(R.drawable.match_img_placeholder)

        team2TextView.text = opponents.getItemNameOrDefault(1)
        opponents.getImageUrlOrNull(1)?.let { url ->
            team2ImgView.load(url) {
                crossfade(true)
                error(R.drawable.match_img_placeholder)
            }
        }?: team2ImgView.load(R.drawable.match_img_placeholder)
    }

    fun bindTimeInfo(item: MatchResponse) {
        if (item.status == MatchGameStatus.RUNNING) {
            bindTimeNowEffect()
        } else {
            bindTimeInfoByWeekDay(item.beginAt)
        }
    }

    private fun bindTimeNowEffect() {
        val context = itemLayout.context
        matchTimeTextView.text = context.getString(R.string.now).uppercase()
        matchTimeBackgroundView.setImageDrawable(
            ContextCompat.getDrawable(context, R.drawable.match_time_background_now)
        )
    }

    private fun bindTimeInfoByWeekDay(beginAt: LocalDateTime?) {
        val context = itemLayout.context
        beginAt?.let { beginAt ->
            matchTimeTextView.text = beginAt.getMatchTimeLabel(context)
        } ?: run {
            matchTimeTextView.text = context.getString(R.string.to_be_defined)
        }
        matchTimeBackgroundView.setImageDrawable(
            ContextCompat.getDrawable(context, R.drawable.match_time_background_scheduled)
        )
    }

    fun bindSerieInfo(serie: MatchSerieResponse) {
        serie.name?.let {
            serieTitleView.text = " - ${serie.name}"
        } ?: run {
            serieTitleView.text = ""
        }
    }
}
