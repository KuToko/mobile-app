package com.example.kutoko.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.kutoko.data.UserPreference
import com.example.kutoko.databinding.FragmentProfileBinding
import com.example.kutoko.ui.auth.LoginActivity
import com.example.kutoko.util.TokenManager

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null

    private lateinit var mUserPreference: UserPreference

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val profileViewModel =
            ViewModelProvider(this).get(ProfileViewModel::class.java)

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        mUserPreference = UserPreference(requireContext())

        binding.logout.setOnClickListener{
            mUserPreference.deleteData()
            TokenManager.token = ""
            val moveIntent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(moveIntent)
            requireActivity().finish()
        }


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}