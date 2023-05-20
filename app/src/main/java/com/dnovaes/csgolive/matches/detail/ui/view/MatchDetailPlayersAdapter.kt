package com.dnovaes.csgolive.matches.detail.ui.view

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dnovaes.csgolive.R
import com.dnovaes.csgolive.matches.common.data.model.TeamPlayerResponse

class MatchDetailPlayersAdapter(
    private var players: List<TeamPlayerResponse>,
    private val layoutId: Int
): RecyclerView.Adapter<MatchDetailPlayerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchDetailPlayerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            layoutId,
            parent,
            false
        ) as ViewGroup
        return MatchDetailPlayerViewHolder(view)
    }

    override fun onBindViewHolder(holder: MatchDetailPlayerViewHolder, position: Int) {
        val player = players[position]
        holder.bindPlayerInfo(player)
    }


    fun update(items: List<TeamPlayerResponse>) {
        this.players = items
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return players.size
    }

}

class MatchDetailPlayerViewHolder(private val itemLayout: ViewGroup) : RecyclerView.ViewHolder(itemLayout) {

    private val playerName: TextView = itemLayout.findViewById(R.id.player_name_textview)
    private val playerNickName: TextView = itemLayout.findViewById(R.id.player_nickname_textview)

    fun bindPlayerInfo(player: TeamPlayerResponse) {
        playerNickName.text = player.nickName
        playerName.text = "${player.firstName} ${player.lastName}"
    }
}
