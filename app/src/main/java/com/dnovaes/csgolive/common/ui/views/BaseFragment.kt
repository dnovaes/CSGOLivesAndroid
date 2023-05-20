package com.dnovaes.csgolive.common.ui.views

import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.dnovaes.csgolive.R
import com.dnovaes.csgolive.matches.common.ui.model.UIError
import com.google.android.material.snackbar.Snackbar

open class BaseFragment<T: ViewBinding>: Fragment() {

    protected var _binding: T? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    protected val binding get() = _binding!!

    private var spinner: ProgressBar? = null
    protected var messagePresenterIsBusy = false

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

    protected fun showFailureSnackBar(error: UIError, onDismiss: (() -> Unit)?= null) {
        val rootView: View = binding.root
        val snackBar = Snackbar.make(rootView, rootView.context.getString(error.stringRes), Snackbar.LENGTH_LONG)
        snackBar.setBackgroundTint(rootView.context.getColor(R.color.failure_snackbar_color))
        snackBar.setTextColor(Color.WHITE)
        snackBar.addCallback(object : Snackbar.Callback() {
            override fun onShown(snackbar: Snackbar) {
                //do Nothing
            }

            override fun onDismissed(snackbar: Snackbar, event: Int) {
                messagePresenterIsBusy = false
                onDismiss?.invoke()
            }
        })
        messagePresenterIsBusy = true
        snackBar.show()
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

    protected fun goBack() {
        if (!messagePresenterIsBusy) {
            findNavController().popBackStack()
        }
    }
}