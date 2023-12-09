package ru.chay.githubclient.ui.userslist

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.launch
import ru.chay.githubclient.databinding.FragmentUsersListBinding
import ru.chay.githubclient.ui.util.afterTextChanged
import ru.chay.githubclient.ui.util.toGone
import ru.chay.githubclient.ui.util.toVisible

class UsersListFragment : Fragment() {

    private var _binding: FragmentUsersListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: UsersListViewModel by viewModels { UsersListViewModel.Factory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUsersListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    processState(state)
                }
            }
        }

        with(binding.rvUsersList) {
            adapter = UserListAdapter()
            addItemDecoration(UsersListItemDecorator(8))
        }
        binding.searchText.afterTextChanged(viewModel :: onNewQuery)
    }

    private fun processState(state: UsersListUiState) {
        Log.d("mainFragment", state.toString())
        when(state) {
            is UsersListUiState.Error -> {
                binding.progressSearching.toGone()
            }
            UsersListUiState.Searching -> {
                binding.rvUsersList.toGone()
                binding.progressSearching.toVisible()
            }
            is UsersListUiState.Success -> {
                binding.progressSearching.toGone()
                (binding.rvUsersList.adapter as UserListAdapter).bindUsers(state.users)
                binding.rvUsersList.toVisible()
            }
        }
    }
}