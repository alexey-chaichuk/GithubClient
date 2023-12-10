package ru.chay.githubclient.ui.userdetails

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import kotlinx.coroutines.launch
import ru.chay.githubclient.databinding.FragmentUserDetailsBinding
import ru.chay.githubclient.ui.util.showSystemMessage
import ru.chay.githubclient.ui.util.toGone
import ru.chay.githubclient.ui.util.toVisible

class UserDetailsFragment : Fragment() {
    private var _binding: FragmentUserDetailsBinding? = null
    private val binding get() = _binding!!
    val args: UserDetailsFragmentArgs by navArgs()
    private val viewModel: UserDetailsViewModel by viewModels { UserDetailsViewModel.Factory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getUserDetails(args.username)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    processState(state)
                }
            }
        }

    }

    private fun processState(state: UserDetailsUiState) {
        Log.d("detailFragment", state.toString())
        when(state) {
            is UserDetailsUiState.Error -> {
                binding.progressLoading.toGone()
                state.exception.localizedMessage?.let { showSystemMessage(it) }
            }
            UserDetailsUiState.Loading -> {
                binding.progressLoading.toVisible()
            }
            is UserDetailsUiState.Success -> {
                with(binding) {
                    progressLoading.toGone()
                    tvUsername.text = state.user.name
                    tvFullname.text = state.user.fullName
                    tvFollowers.text = state.user.followers.toString()
                }
            }
        }
    }
}