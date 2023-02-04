package zechs.takesurvey.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import zechs.takesurvey.R
import zechs.takesurvey.databinding.FragmentHomeBinding
import zechs.takesurvey.utils.ext.addInput
import zechs.takesurvey.utils.ext.inputText
import zechs.takesurvey.utils.ext.navigateSafe
import zechs.takesurvey.utils.extractIdFromUrl

class HomeFragment : Fragment() {

    companion object {
        const val TAG = "HomeFragment"
    }

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(
            inflater, container, /* attachToParent */ false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)

        binding.apply {
            btnCreate.setOnClickListener {
                findNavController().navigateSafe(R.id.action_homeFragment_to_createFragment)
            }
            btnAttempt.setOnClickListener {
                setupSurveyButton(NavigateTo.Attempt)
            }
            btnResult.setOnClickListener {
                setupSurveyButton(NavigateTo.Result)
            }
        }

    }

    private fun setupSurveyButton(
        navigateTo: NavigateTo
    ) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.enter_survey_link)
            .setPositiveButton(getString(R.string.submit)) { dialog, _ ->
                Log.d(
                    TAG, "setupAttemptButton: Input(" + dialog.inputText(
                        textInputLayout = R.id.dialog_text_input_layout
                    ) + ")"
                )
                extractIdFromUrl(
                    dialog.inputText(textInputLayout = R.id.dialog_text_input_layout)
                )?.let { pollId ->
                    val action = when (navigateTo) {
                        NavigateTo.Attempt -> {
                            HomeFragmentDirections.actionHomeFragmentToAttemptFragment(pollId)
                        }
                        NavigateTo.Result -> {
                            HomeFragmentDirections.actionHomeFragmentToResultFragment(pollId)
                        }
                    }
                    findNavController().navigateSafe(action)
                } ?: run {
                    Snackbar.make(
                        binding.root,
                        getString(R.string.invalid_link),
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }.setNegativeButton(getString(R.string.cancel), null)
            .addInput(hint = R.string.enter_survey_link)
            .show()
    }

    sealed interface NavigateTo {
        object Attempt : NavigateTo
        object Result : NavigateTo
    }

}
