package com.example.kutoko.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
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
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Logout")
            builder.setMessage("Are you sure you want to logout?")
            builder.setPositiveButton("OK") { _, _ ->
                mUserPreference.deleteData()
                TokenManager.token = ""
                val moveIntent = Intent(requireContext(), LoginActivity::class.java)
                startActivity(moveIntent)
                requireActivity().finishAffinity() // Close all activities
            }
            builder.setNegativeButton("Cancel", null)
            val dialog = builder.create()
            dialog.show()
        }


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}