package zechs.takesurvey.ui.result

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.TransitionManager
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.transition.MaterialSharedAxis
import dagger.hilt.android.AndroidEntryPoint
import zechs.takesurvey.data.models.PollResponse
import zechs.takesurvey.databinding.FragmentResultBinding
import zechs.takesurvey.ui.attempt.FetchSurveyUiState
import zechs.takesurvey.ui.result.adapter.ItemOption
import zechs.takesurvey.ui.result.adapter.OptionsAdapter
import zechs.takesurvey.utils.showSnackBar

@AndroidEntryPoint
class ResultFragment : Fragment() {

    companion object {
        const val TAG = "ResultFragment"
    }

    private var _binding: FragmentResultBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<ResultFragmentArgs>()
    private val viewModel by lazy {
        ViewModelProvider(this)[ResultsViewModel::class.java]
    }

    private val optionAdapter by lazy {
        OptionsAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResultBinding.inflate(
            inflater, container, /* attachToParent */ false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentResultBinding.bind(view)

        val pollId = args.pollId
        Log.d(TAG, "onViewCreated: PollId($pollId)")

        viewModel.fetchSurvey(pollId)
        setupRecyclerView()
        setupSurveyObserver()
    }

    private fun setupSurveyObserver() {
        viewModel.fetchState.observe(viewLifecycleOwner) { surveyState ->
            Log.d(TAG, "surveyState($surveyState)")
            when (surveyState) {
                FetchSurveyUiState.Fetching -> {
                    isLoading(true)
                }
                is FetchSurveyUiState.Error -> {
                    isLoading(false)
                    showSnackBar(
                        root = binding.root,
                        message = surveyState.message,
                        duration = Snackbar.LENGTH_INDEFINITE
                    )
                }
                is FetchSurveyUiState.FetchedSurvey -> {
                    setupSurveyUi(surveyState.survey)
                    isLoading(false)
                }
            }
        }
    }

    private fun isLoading(loading: Boolean) {
        TransitionManager.beginDelayedTransition(
            binding.root,
            MaterialSharedAxis(MaterialSharedAxis.Y, !loading)
        )
        binding.apply {
            if (loading) {
                this.loading.visibility = View.VISIBLE
                rvList.visibility = View.GONE
                appBar.visibility = View.GONE
            } else {
                this.loading.visibility = View.GONE
                rvList.visibility = View.VISIBLE
                appBar.visibility = View.VISIBLE
            }
        }
    }

    private fun setupSurveyUi(survey: PollResponse) {
        binding.apply {
            tvTitle.text = survey.title
            val options = mutableListOf<ItemOption>()
            val totalVotes = survey.options.sumOf { it.vote }
            survey.options.forEach { option ->
                val percentage = (option.vote * 100) / totalVotes
                options.add(
                    ItemOption(
                        title = option.title,
                        percentage = percentage,
                        vote = option.vote
                    )
                )
            }
            optionAdapter.submitList(options)
        }
    }

    private fun setupRecyclerView() {
        val linearLayoutManager = LinearLayoutManager(
            /* context */ context,
            /* orientation */ RecyclerView.VERTICAL,
            /* reverseLayout */ false
        )
        binding.rvList.apply {
            adapter = optionAdapter
            layoutManager = linearLayoutManager
        }
    }

}