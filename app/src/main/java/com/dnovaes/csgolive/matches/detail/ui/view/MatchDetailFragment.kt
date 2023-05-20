package com.dnovaes.csgolive.matches.detail.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.dnovaes.csgolive.R
import com.dnovaes.csgolive.common.ui.views.BaseFragment
import com.dnovaes.csgolive.common.ui.viewstate.UIViewState
import com.dnovaes.csgolive.common.utilities.extensions.getMatchTimeLabel
import com.dnovaes.csgolive.databinding.FragmentMatchDetailBinding
import com.dnovaes.csgolive.matches.common.data.model.MatchDetail
import com.dnovaes.csgolive.matches.common.data.model.MatchOpponentGroupResponse
import com.dnovaes.csgolive.matches.common.data.model.TeamPlayerResponse
import com.dnovaes.csgolive.matches.common.data.model.getImageUrlOrNull
import com.dnovaes.csgolive.matches.common.data.model.getItemNameOrDefault
import com.dnovaes.csgolive.matches.common.ui.model.Matches
import com.dnovaes.csgolive.matches.common.ui.view.MatchesViewModel
import com.dnovaes.csgolive.matches.detail.ui.model.isDoneLoadingMatchDetail
import com.dnovaes.csgolive.matches.detail.ui.model.isProcessingLoadMatchDetail
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MatchDetailFragment : BaseFragment<FragmentMatchDetailBinding>() {

    private val viewModel: MatchesViewModel by activityViewModels()
    private val args: MatchDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMatchDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObservers()
        bindElements()
        val teamIds = listOf(args.team1, args.team2)
        viewModel.loadMatchDetail(teamIds)
    }

    private fun setObservers() {
        viewModel.matchesLiveData.observe(viewLifecycleOwner, matchesObserver)
        viewModel.matchDetailLiveData.observe(viewLifecycleOwner, matchDetailObserver)
    }

    override fun onDestroyView() {
        viewModel.matchesLiveData.removeObserver(matchesObserver)
        viewModel.matchDetailLiveData.removeObserver(matchDetailObserver)
        super.onDestroyView()
    }

    private fun bindElements() {
        binding.matchDetailBtBack.setOnClickListener {
            findNavController().popBackStack()
            viewModel.userLeftMatchDetailScreen()
        }
    }

    private val matchesObserver: Observer<UIViewState<Matches>> = Observer { modelState ->
        modelState.result?.let { modelData ->
            if (modelData.data.isEmpty()) return@Observer
            val match = modelData.data.firstOrNull { it.id.toInt() == args.matchId } ?: return@Observer
            bindTeamInfo(match.opponents)
            bindSerieLeagueName(match.serie.name, match.league.name)
            binding.matchDetailTime.text = match.beginAt?.getMatchTimeLabel(requireContext())
                ?: context?.getString(R.string.to_be_defined)
        }
    }

    private val matchDetailObserver: Observer<UIViewState<MatchDetail>> = Observer { modelState ->
        when {
            modelState.isProcessingLoadMatchDetail() -> {
                showLoadingSpinner()
            }
            modelState.isDoneLoadingMatchDetail() -> {
                hideLoadingSpinner()
                modelState.result?.let { matchDetail ->
                    bindPlayersData(matchDetail.team1.players, matchDetail.team2.players)
                }
            }
        }
    }

    private fun bindTeamInfo(opponents: List<MatchOpponentGroupResponse>) {
        val team1TextView = binding.matchTeam1Textview
        val team1ImgView = binding.matchTeam1Imgview

        team1TextView.text = opponents.getItemNameOrDefault(0)
        opponents.getImageUrlOrNull(0)?.let { url ->
            team1ImgView.load(url) {
                crossfade(true)
                error(R.drawable.match_img_placeholder)
            }
        } ?: team1ImgView.load(R.drawable.match_img_placeholder)

        val team2TextView = binding.matchTeam2Textview
        val team2ImgView = binding.matchTeam2Imgview

        team2TextView.text = opponents.getItemNameOrDefault(1)
        opponents.getImageUrlOrNull(1)?.let { url ->
            team2ImgView.load(url) {
                crossfade(true)
                error(R.drawable.match_img_placeholder)
            }
        }?: team2ImgView.load(R.drawable.match_img_placeholder)
    }

    private fun bindSerieLeagueName(serieName: String?, leagueName: String) {
        serieName?.let { serieNameNotNull ->
            binding.matchDetailTitle.text = "$leagueName - $serieNameNotNull"
        } ?: run {
            binding.matchDetailTitle.text = leagueName
        }
    }

    private fun bindPlayersData(
        players1: List<TeamPlayerResponse>,
        players2: List<TeamPlayerResponse>
    ) {
        bindTeam(
            players1,
            R.layout.recycler_view_match_detail_player_item_left,
            binding.team1RecyclerView
        )
        bindTeam(
            players2,
            R.layout.recycler_view_match_detail_player_item_right,
            binding.team2RecyclerView
        )
    }

    private fun bindTeam(
        players: List<TeamPlayerResponse>,
        layoutId: Int,
        recyclerView: RecyclerView
    ) {
        val matchDetailPlayersAdapter = MatchDetailPlayersAdapter(
            players,
            layoutId
        )
        recyclerView.adapter = matchDetailPlayersAdapter
        recyclerView.layoutManager = LinearLayoutManager(this.context)
    }
}
