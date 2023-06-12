package com.example.kutoko.ui.beranda

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kutoko.adapter.adapterNearbyStore.LoadingStateAdapter
import com.example.kutoko.adapter.adapterNearbyStore.NearbyStoreAdapter
import com.example.kutoko.adapter.adapterRecomendationStore.RecomendationAdapter
import com.example.kutoko.clientApi.ApiConfig
import com.example.kutoko.databinding.FragmentBerandaBinding
import com.example.kutoko.ui.userLocation.LocationList
import com.example.kutoko.util.LocationManager
import com.example.kutoko.util.TokenManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BerandaFragment : Fragment() {

    private var _binding: FragmentBerandaBinding? = null
    private lateinit var nearbyRecylerView : RecyclerView
    private lateinit var recomendRecylerView : RecyclerView
    private val binding get() = _binding!!
    private val pageViewModel : PageViewModel by viewModels {
        ViewModelFactory(requireActivity())
    }

    private val recomendationPageViewModel : RecomendationPageViewModel by viewModels {
        ViewModelFactoryRecomendation(requireActivity())
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val berandaViewModel =
            ViewModelProvider(this)[BerandaViewModel::class.java]
        _binding = FragmentBerandaBinding.inflate(inflater, container, false)
        binding.tvAlamatSekarang.text = LocationManager.addressLocation
        //recylerview

        recomendRecylerView = binding.rvRekomendasi
        recomendRecylerView.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL, false)
        layoutManager.reverseLayout = false
        recomendRecylerView.layoutManager = layoutManager
        recomendRecylerView.post {
            recomendRecylerView.scrollToPosition(0)
        }
        setUserRecomendationWithDelay()

        nearbyRecylerView = binding.rvUmkmDisekitar
        nearbyRecylerView.layoutManager = GridLayoutManager(context,2)
        setUserStoreWithDelay()

        binding.btGantiLokasi.setOnClickListener {
            startActivity(Intent(requireActivity(),LocationList::class.java))
            requireActivity().finish()
        }




        return binding.root
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun setUserStoreWithDelay() {
        val delayDuration = 1000L

        lifecycleScope.launch {
            delay(delayDuration)
            setUserStore()
            try {
                ApiConfig.getApiService().getStore(TokenManager.token,LocationManager.lat,LocationManager.long,1,1)

            }catch (e: Exception){
                withContext(Dispatchers.Main) {
                    Toast.makeText(requireActivity(), "An error occurred: ${e.message} : ", Toast.LENGTH_LONG).show()
                }
            }
        }

    }

    private fun setUserRecomendationWithDelay() {
        val delayDuration = 500L



        lifecycleScope.launch {
            delay(delayDuration)
            setUserRecomendation()
            try {
                ApiConfig.getApiService().getRecommendation(TokenManager.token,LocationManager.lat,LocationManager.long,1,1)

            }catch (e: Exception){
                withContext(Dispatchers.Main) {
                    Toast.makeText(requireActivity(), "An error occurred: ${e.message} : ", Toast.LENGTH_LONG).show()
                }
            }
        }

    }

    private fun setUserRecomendation() {
        val adapter = RecomendationAdapter()
        binding.rvRekomendasi.adapter = adapter.withLoadStateFooter(
            footer = LoadingStateAdapter {
                adapter.retry()
            }
        )

        recomendationPageViewModel.recomendStore.observe(viewLifecycleOwner) {
            adapter.submitData(lifecycle,it)
        }

    }

    private fun setUserStore() {
        val adapter = NearbyStoreAdapter()
        binding.rvUmkmDisekitar.adapter = adapter.withLoadStateFooter(
            footer = LoadingStateAdapter {
                adapter.retry()
            }
        )

        pageViewModel.store.observe(viewLifecycleOwner){
            adapter.submitData(lifecycle,it)
        }

    }
}