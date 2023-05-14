package com.dnovaes.csgolive.matches.summary.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.dnovaes.csgolive.common.ui.viewstate.UIViewState
import com.dnovaes.csgolive.databinding.FragmentMatchesBinding
import com.dnovaes.csgolive.matches.common.ui.model.GameMatch
import com.dnovaes.csgolive.matches.common.ui.view.MatchesViewModel
import com.dnovaes.csgolive.matches.summary.ui.model.isDoneLoadingSummaryData
import com.dnovaes.csgolive.matches.summary.ui.model.isProcessingLoadSummaryData
import com.dnovaes.csgolive.matches.summary.ui.model.isStartingLoadSummaryData
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MatchesSummaryFragment : Fragment() {

    private var _binding: FragmentMatchesBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: MatchesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMatchesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindElements()
    }

    private val matchObserver: Observer<UIViewState<GameMatch>> = Observer { modelState ->
        when {
            modelState.isStartingLoadSummaryData() -> viewModel.loadSummaryData()
            modelState.isProcessingLoadSummaryData() -> {
                //show spinner
            }
            modelState.isDoneLoadingSummaryData() -> {
                //show data on the screen
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        viewModel.matchesLiveData.removeObserver(matchObserver)
        super.onDestroyView()
    }

    private fun bindElements() {
        viewModel.matchesLiveData.observe(viewLifecycleOwner, matchObserver)
    }
}
