package com.dnovaes.csgolive.matches.detail.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.dnovaes.csgolive.R
import com.dnovaes.csgolive.common.ui.views.BaseFragment
import com.dnovaes.csgolive.common.ui.viewstate.UIViewState
import com.dnovaes.csgolive.common.utilities.extensions.getMatchTimeLabel
import com.dnovaes.csgolive.databinding.FragmentMatchDetailBinding
import com.dnovaes.csgolive.matches.common.data.model.MatchOpponentGroupResponse
import com.dnovaes.csgolive.matches.common.data.model.getImageUrlOrNull
import com.dnovaes.csgolive.matches.common.data.model.getItemNameOrDefault
import com.dnovaes.csgolive.matches.common.ui.model.Matches
import com.dnovaes.csgolive.matches.common.ui.view.MatchesViewModel
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
    }

    private fun setObservers() {
        viewModel.matchesLiveData.observe(viewLifecycleOwner, matchesObserver)
    }

    private fun bindElements() {
        binding.matchDetailBtBack.setOnClickListener {
            findNavController().popBackStack()
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
}
