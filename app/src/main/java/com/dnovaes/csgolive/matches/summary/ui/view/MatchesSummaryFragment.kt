package com.dnovaes.csgolive.matches.summary.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dnovaes.csgolive.common.ui.views.BaseFragment
import com.dnovaes.csgolive.common.ui.viewstate.UIViewState
import com.dnovaes.csgolive.common.utilities.ui.listeners.InfiniteScrollListener
import com.dnovaes.csgolive.databinding.FragmentMatchesBinding
import com.dnovaes.csgolive.matches.common.data.model.MatchResponse
import com.dnovaes.csgolive.matches.common.ui.model.Matches
import com.dnovaes.csgolive.matches.common.ui.view.MatchesAdapter
import com.dnovaes.csgolive.matches.common.ui.view.MatchesViewModel
import com.dnovaes.csgolive.matches.summary.ui.model.isDoneLoadingSummaryData
import com.dnovaes.csgolive.matches.summary.ui.model.isDoneLoadingSummaryDataFromPage
import com.dnovaes.csgolive.matches.summary.ui.model.isProcessingLoadSummaryData
import com.dnovaes.csgolive.matches.summary.ui.model.isProcessingLoadSummaryDataFromPage
import com.dnovaes.csgolive.matches.summary.ui.model.isStartingLoadSummaryData
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MatchesSummaryFragment : BaseFragment<FragmentMatchesBinding>() {

    private val viewModel: MatchesViewModel by activityViewModels()

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

    private val matchObserver: Observer<UIViewState<Matches>> = Observer { modelState ->
        when {
            modelState.isStartingLoadSummaryData() -> viewModel.loadInitialSummaryData()
            modelState.isProcessingLoadSummaryData() ||
            modelState.isProcessingLoadSummaryDataFromPage() -> {
                if (!binding.summarySwipeRefreshLayout.isRefreshing) {
                    showLoadingSpinner()
                }
            }
            modelState.isDoneLoadingSummaryData() ||
            modelState.isDoneLoadingSummaryDataFromPage() -> {
                binding.summarySwipeRefreshLayout.isRefreshing = false
                hideLoadingSpinner()
                modelState.error?.let {
                    showFailureSnackBar(it)
                }
                modelState.result?.let { matches ->
                    updatesRecyclerView(matches.data)
                }
            }
        }
    }

    override fun onDestroyView() {
        viewModel.matchesLiveData.removeObserver(matchObserver)
        super.onDestroyView()
    }

    private fun bindElements() {
        viewModel.matchesLiveData.observe(viewLifecycleOwner, matchObserver)
        binding.summarySwipeRefreshLayout.setOnRefreshListener {
            viewModel.refreshSummaryMatches()
        }
        bindRecyclerView()
    }

    private fun bindRecyclerView() {
        val matchesAdapter = MatchesAdapter(emptyList()) { matchId, teamIds ->
            val action = MatchesSummaryFragmentDirections.actionSummaryFragmentToMatchDetailFragment(matchId, teamIds[0], teamIds[1])
            findNavController().navigate(action)
        }
        binding.summaryRecyclerView.let { recyclerView ->
            recyclerView.adapter = matchesAdapter
            recyclerView.layoutManager = LinearLayoutManager(this.context)
        }
        binding.summaryRecyclerView.addOnScrollListener(InfiniteScrollListener {
            viewModel.loadsMoreSummaryMatchesFromPage()
        })
    }

    private fun updatesRecyclerView(matches: List<MatchResponse>) {
        (binding.summaryRecyclerView.adapter as? MatchesAdapter)?.update(matches)
    }
}
