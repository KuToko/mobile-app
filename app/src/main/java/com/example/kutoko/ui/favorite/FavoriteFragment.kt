package com.example.kutoko.ui.favorite

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kutoko.adapter.adapterFavorite.FavoriteAdapter
import com.example.kutoko.data.Favorite
import com.example.kutoko.data.database.favoriteDatabase.ListFavoriteItem
import com.example.kutoko.databinding.FragmentFavoriteBinding
import com.example.kutoko.ui.favorite.viewmodel.FavoriteViewModelFactory
import com.example.kutoko.ui.favorite.viewmodel.MainFavoriteViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class FavoriteFragment : Fragment() {

    private lateinit var mainFavoriteViewModel: MainFavoriteViewModel

    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    private var _binding : FragmentFavoriteBinding? = null
    private val binding get() = _binding!!


    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding =  FragmentFavoriteBinding.inflate(inflater, container, false)

        val manager = LinearLayoutManager(requireActivity())
        binding.rvFavorite.layoutManager = manager
        binding.rvFavorite.setHasFixedSize(true)

        val activity = requireActivity()

        mainFavoriteViewModel = obtainMainViewModel(requireActivity() as AppCompatActivity)

        mainFavoriteViewModel.getAllFavorite().observe(requireActivity()) {
            setUserFavorite(it)
        }

        coroutineScope.launch {
            showLoading(false)
            delay(2000)
            mainFavoriteViewModel.getAllFavorite().observe(activity) { favorites ->
                setUserFavorite(favorites)
            }
        }

        return binding.root
    }

    @SuppressLint("SetTextI18n")
    private fun setUserFavorite(listFavorite: List<ListFavoriteItem>) {
        val favoriteStore = ArrayList<Favorite>()

        for (i in listFavorite.indices) {
            val idStore = listFavorite[i].id
            val storeName = listFavorite[i].name
            val storeAvatar = listFavorite[i].avatar
            val storeUpVotes = listFavorite[i].upvotes
            val kategori = listFavorite[i].categories

            if (idStore != null && storeName != null && storeAvatar != null && storeUpVotes!= null) {
                val favorite = Favorite(idStore,storeName,storeUpVotes,storeAvatar,kategori)
                favoriteStore.add(favorite)
            }
        }

        if (favoriteStore.isNotEmpty()) {
            val adapter = FavoriteAdapter(favoriteStore)
            binding.rvFavorite.adapter = adapter
        }else{
            binding.tvFavorite.text = "Belum ada Store Favorite Yang Di Tambahkan"
        }
    }

    private fun obtainMainViewModel(activity: AppCompatActivity): MainFavoriteViewModel {
        val factory = FavoriteViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[MainFavoriteViewModel::class.java]
    }

    private fun showLoading(isLoading: Boolean){
        if (isLoading){
            binding.progressBar.visibility = View.VISIBLE
        }else{
            binding.progressBar.visibility = View.GONE
        }
    }

}