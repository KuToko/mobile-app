package com.example.kutoko.ui.tokosaya.order

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kutoko.R

class OrderanTokoSaya : Fragment() {

    companion object {
        fun newInstance() = OrderanTokoSaya()
    }

    private lateinit var viewModel: OrderanTokoSayaViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_orderan_toko_saya, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(OrderanTokoSayaViewModel::class.java)
        // TODO: Use the ViewModel
    }

}