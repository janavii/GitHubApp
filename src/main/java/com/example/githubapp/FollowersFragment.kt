package com.example.githubapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubapp.databinding.FragmentFollowersBinding


class FollowersFragment : Fragment() {
    companion object {
        private val ARG_USERNAME = "username"
        fun newInstance(username: String?): FollowersFragment {
            val fragment = FollowersFragment()
            val bundle = Bundle()
            bundle.putString(ARG_USERNAME, username)
            fragment.arguments = bundle
            return fragment
        }
    }
    private var _binding: FragmentFollowersBinding? = null
    private lateinit var adapter: UserAdapter
    private lateinit var followersViewModel: FollowersViewModel
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFollowersBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvFollowers.setHasFixedSize(true)
        showRecyclerView()
        setFollowers()
    }

    private fun showRecyclerView() {
        binding.rvFollowers.layoutManager = LinearLayoutManager(activity)
        adapter = UserAdapter()
        adapter.notifyDataSetChanged()
        binding.rvFollowers.adapter = adapter
    }

    private fun setFollowers() {
        if (arguments != null) {
            val username = arguments?.getString(ARG_USERNAME)
            followersViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(FollowersViewModel::class.java)

            if (username != null)
                followersViewModel.setFollowers(username)
        }

        followersViewModel.getFollowers().observe(viewLifecycleOwner, { listFollowers ->
            if (listFollowers != null) {
                adapter.setData(listFollowers)
            }
        })
    }

}
