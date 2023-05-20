package com.dnovaes.csgolive.matches.detail.ui.view

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
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
        holder.bindPlayerImage(player.imageUrl)
    }


    fun update(items: List<TeamPlayerResponse>) {
        this.players = items
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return players.size
    }

}

class MatchDetailPlayerViewHolder(itemLayout: ViewGroup) : RecyclerView.ViewHolder(itemLayout) {

    private val playerName: TextView = itemLayout.findViewById(R.id.player_name_textview)
    private val playerNickName: TextView = itemLayout.findViewById(R.id.player_nickname_textview)
    private val playerImageView: ImageView = itemLayout.findViewById(R.id.player_imgview)

    fun bindPlayerInfo(player: TeamPlayerResponse) {
        playerNickName.text = player.nickName ?: "Not Defined"
        playerName.text = "${player.firstName ?: "-"} ${player.lastName ?: ""}"
    }

    fun bindPlayerImage(imageUrl: String?) {
        imageUrl?.let { url ->
            val roundedCornersTransformation = RoundedCornersTransformation(16f)
            playerImageView.load(url) {
                transformations(roundedCornersTransformation)
                crossfade(true)
                error(R.drawable.match_detail_player_img_placeholder)
            }
        } ?: playerImageView.load(R.drawable.match_detail_player_img_placeholder)
    }
}
