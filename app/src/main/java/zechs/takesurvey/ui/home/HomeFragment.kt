package zechs.takesurvey.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import zechs.takesurvey.R
import zechs.takesurvey.databinding.FragmentHomeBinding
import zechs.takesurvey.utils.ext.navigateSafe

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
                findNavController().navigateSafe(R.id.action_homeFragment_to_attemptFragment)
            }
            btnResult.setOnClickListener {
                findNavController().navigateSafe(R.id.action_homeFragment_to_resultFragment)
            }
        }

    }


}
