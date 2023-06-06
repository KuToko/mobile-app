package com.example.kutoko.ui.beranda

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kutoko.adapter.LoadingStateAdapter
import com.example.kutoko.adapter.NearbyStoreAdapter
import com.example.kutoko.databinding.FragmentBerandaBinding

class BerandaFragment : Fragment() {

    private var _binding: FragmentBerandaBinding? = null
    private lateinit var recylerView : RecyclerView
    private val binding get() = _binding!!
    private val pageViewModel : PageViewModel by viewModels {
        ViewModelFactory(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val berandaViewModel =
            ViewModelProvider(this)[BerandaViewModel::class.java]
        _binding = FragmentBerandaBinding.inflate(inflater, container, false)

        //recylerview
        recylerView = binding.rvUmkmDisekitar
        recylerView.layoutManager = GridLayoutManager(context,2)
        setUserStore()
        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setUserStore() {
        val adapter = NearbyStoreAdapter()
        binding.rvUmkmDisekitar.adapter = adapter.withLoadStateFooter(
            footer = LoadingStateAdapter {
                adapter.retry()
            }
        )

        pageViewModel.store.observe(viewLifecycleOwner){
            if (it != null) {
                Log.d("Beranda Fragment", "Data received")
                adapter.submitData(lifecycle,it)
                Log.d("Beranda Fragment", "Data submitted to adapter")
            }
        }
    }
}