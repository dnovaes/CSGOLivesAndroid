package com.dnovaes.csgolive.common.ui.views

import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

open class BaseFragment<T: ViewBinding>: Fragment() {

    protected var _binding: T? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    protected val binding get() = _binding!!

    private var spinner: ProgressBar? = null

    override fun onDestroyView() {
        (binding.root as? ViewGroup)?.removeView(spinner)
        spinner = null
        _binding = null
        super.onDestroyView()
    }

    protected fun showLoadingSpinner() {
        val spinner = spinner ?: createsSpinner()
        spinner.visibility = View.VISIBLE
    }

    protected fun hideLoadingSpinner() {
        spinner?.visibility = View.INVISIBLE
    }

    private fun createsSpinner(): ProgressBar {
        val loadingSpinner = ProgressBar(context, null, android.R.attr.progressBarStyleLarge)
        loadingSpinner.isIndeterminate = true
        loadingSpinner.visibility = View.VISIBLE
        val params = ConstraintLayout.LayoutParams(
            ConstraintLayout.LayoutParams.WRAP_CONTENT,
            ConstraintLayout.LayoutParams.WRAP_CONTENT,
        )
        params.topToTop = ConstraintLayout.LayoutParams.PARENT_ID
        params.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID
        params.startToStart = ConstraintLayout.LayoutParams.PARENT_ID
        params.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
        spinner = loadingSpinner
        (binding.root as? ViewGroup)?.addView(spinner, params)
        return loadingSpinner
    }

}