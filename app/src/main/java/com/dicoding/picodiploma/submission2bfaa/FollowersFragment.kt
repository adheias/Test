package com.dicoding.picodiploma.submission2bfaa

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.picodiploma.submission2bfaa.databinding.FragmentFollowersBinding

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
    private lateinit var adapter: ListUserAdapter
    private lateinit var followersViewModel: FollowersViewModel
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
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
        adapter = ListUserAdapter()
        adapter.notifyDataSetChanged()
        binding.rvFollowers.adapter = adapter
    }

    private fun setFollowers() {
        if (arguments != null) {
            val username = arguments?.getString(ARG_USERNAME)
            followersViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(FollowersViewModel::class.java)
            showLoading(true)
            if (username != null)
                followersViewModel.setFollowers(username)
        }

        followersViewModel.getFollowers().observe(viewLifecycleOwner, { listFollowers ->
            if (listFollowers != null) {
                adapter.setData(listFollowers)
                showLoading(false)
            }
        })
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}