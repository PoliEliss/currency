package com.rorono.a22recycler

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toolbar
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.rorono.a22recycler.databinding.FragmentCurrencyBinding
import com.rorono.a22recycler.databinding.FragmentCurrencyTransferBinding
import android.widget.Toast.makeText as makeText1


class CurrencyTransferFragment : Fragment(R.layout.fragment_currency_transfer) {

private lateinit var binding: FragmentCurrencyTransferBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       binding = FragmentCurrencyTransferBinding.inflate(layoutInflater)
       return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




    }
}




