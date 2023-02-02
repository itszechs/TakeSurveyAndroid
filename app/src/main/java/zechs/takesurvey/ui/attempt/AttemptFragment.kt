package zechs.takesurvey.ui.attempt

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionManager
import com.google.android.material.radiobutton.MaterialRadioButton
import com.google.android.material.transition.MaterialSharedAxis
import zechs.takesurvey.data.models.PollResponse
import zechs.takesurvey.databinding.FragmentAttemptBinding
import zechs.takesurvey.utils.showSnackBar


class AttemptFragment : Fragment() {

    companion object {
        const val TAG = "AttemptFragment"
    }

    private var _binding: FragmentAttemptBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<AttemptFragmentArgs>()
    private val viewModel by activityViewModels<AttemptViewModel>()

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
            MaterialSharedAxis(MaterialSharedAxis.Y, true)
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
            val checkedRadioButton = binding.radioGroup.findViewById<RadioButton>(selectedOption)
            val option = checkedRadioButton.text.toString()
            Log.d(TAG, "Selected option: ${option}")
        }
    }

}
