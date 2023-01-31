package zechs.takesurvey.ui.attempt

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import zechs.takesurvey.databinding.FragmentAttemptBinding

class AttemptFragment : Fragment() {

    companion object {
        const val TAG = "AttemptFragment"
    }

    private var _binding: FragmentAttemptBinding? = null
    private val binding get() = _binding!!

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
    }

}
