package com.example.kutoko.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.kutoko.MainActivity
import com.example.kutoko.R
import com.example.kutoko.data.User
import com.example.kutoko.data.UserPreference
import com.example.kutoko.databinding.FragmentProfileBinding
import com.example.kutoko.ui.auth.LoginActivity
import com.example.kutoko.ui.tokosaya.beranda.BerandaTokoSayaViewModel
import com.example.kutoko.ui.userLocation.LocationList
import com.example.kutoko.util.TokenManager

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null

    private lateinit var mUserPreference: UserPreference
    private lateinit var user : User

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val profileViewModel =
            ViewModelProvider(this)[ProfileViewModel::class.java]

        val berandaTokoSayaViewModel = ViewModelProvider(this)[BerandaTokoSayaViewModel::class.java]

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        mUserPreference = UserPreference(requireContext())
        user = mUserPreference.getUser()

        binding.tvProfileUser.text = user.name
        Glide.with(requireActivity()).load(user.avatar).placeholder(R.drawable.ic_baseline_image_24).into(binding.ivProfileUser)


        binding.logout.setOnClickListener{
            val builder = AlertDialog.Builder(requireActivity())
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

        binding.btOpenMyAddress.setOnClickListener {
            val moveIntent = Intent(requireActivity(),LocationList::class.java)
            startActivity(moveIntent)
            requireActivity().finish()
        }

        binding.btOpenMyStore.setOnClickListener {
            profileViewModel.checkMyStore(TokenManager.token)
            profileViewModel.myStoreResponse.observe(viewLifecycleOwner){
                if (it.data.isNotEmpty()){
                    val intent = Intent(requireActivity(),MainActivity::class.java)
                    intent.putExtra(MainActivity.CHANGE_NAV,true)
                    requireActivity().finish()
                    startActivity(intent)
                }else{
                    val builder = AlertDialog.Builder(requireActivity())
                    builder.setTitle("Kutoko : Toko Saya")
                    builder.setMessage("Maaf, Tapi Kamu Belum Mendaftarkan Toko Anda di Website Kami")
                    builder.setPositiveButton("OK") { _,_ ->
                    }
                    builder.create().show()
                }
            }

            profileViewModel.errorResponse.observe(viewLifecycleOwner){
                if (it){
                    Toast.makeText(requireActivity(),"Token Expired Please Login Again",Toast.LENGTH_SHORT)
                }

            }

        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}