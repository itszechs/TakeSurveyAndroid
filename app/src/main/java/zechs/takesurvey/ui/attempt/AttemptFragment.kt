package zechs.takesurvey.ui.attempt

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionManager
import com.google.android.material.radiobutton.MaterialRadioButton
import com.google.android.material.transition.MaterialSharedAxis
import dagger.hilt.android.AndroidEntryPoint
import zechs.takesurvey.R
import zechs.takesurvey.data.models.PollResponse
import zechs.takesurvey.databinding.FragmentAttemptBinding
import zechs.takesurvey.utils.ext.navigateSafe
import zechs.takesurvey.utils.showSnackBar

@AndroidEntryPoint
class AttemptFragment : Fragment() {

    companion object {
        const val TAG = "AttemptFragment"
    }

    private var _binding: FragmentAttemptBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<AttemptFragmentArgs>()
    private val viewModel by lazy {
        ViewModelProvider(this)[AttemptViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAttemptBinding.inflate(
            inflater, container, /* attachToParent */ false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentAttemptBinding.bind(view)

        val pollId = args.pollId
        Log.d(TAG, "onViewCreated: PollId($pollId)")

        setupSurveyObserver()
        setupSubmitButton()
        setupAttemptObserver()
        viewModel.fetchSurvey(pollId)
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
            progressBar.visibility = if (loading) View.VISIBLE else View.GONE
            tvTitle.visibility = if (loading) View.GONE else View.VISIBLE
            radioButtonScrollView.visibility = if (loading) View.GONE else View.VISIBLE
            btnSubmit.visibility = if (loading) View.GONE else View.VISIBLE
        }
    }

    private fun setupSurveyUi(survey: PollResponse) {
        binding.apply {
            tvTitle.text = survey.title
            binding.radioGroup.removeAllViews()
            survey.options.forEach { option ->
                MaterialRadioButton(requireContext())
                    .apply { text = option.title }
                    .also { binding.radioGroup.addView(it) }
            }
        }
    }

    private fun setupSubmitButton() {
        binding.btnSubmit.setOnClickListener {
            val selectedOption = binding.radioGroup.checkedRadioButtonId
            val checkedRadioButton = binding.radioGroup.findViewById<RadioButton?>(selectedOption)
            checkedRadioButton?.let {
                val option = checkedRadioButton.text.toString()
                viewModel.attemptSurvey(
                    pollId = args.pollId,
                    pollOption = option
                )
            } ?: run {
                showSnackBar(
                    root = binding.root,
                    message = getString(R.string.please_select_option)
                )
            }
        }
    }

    private fun setupAttemptObserver() {
        viewModel.attemptState.observe(viewLifecycleOwner) { attemptState ->
            Log.d(TAG, "attemptState($attemptState)")
            when (attemptState) {
                AttemptSurveyUiState.Attempting -> {
                    isLoading(true)
                }
                is AttemptSurveyUiState.AttemptedStatus -> {
                    showSnackBar(
                        binding.root,
                        message = attemptState.message
                    )
                    isLoading(false)
                    val action = AttemptFragmentDirections.actionAttemptFragmentToResultFragment(
                        pollId = args.pollId
                    )
                    findNavController().navigateSafe(action)
                }
                is AttemptSurveyUiState.Error -> {
                    showSnackBar(
                        binding.root,
                        message = attemptState.message
                    )
                    isLoading(false)
                }
            }

        }
    }


}
