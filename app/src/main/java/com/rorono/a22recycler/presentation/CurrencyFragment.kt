package com.rorono.a22recycler.presentation

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.View.ALPHA
import android.view.View.TRANSLATION_X
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.transition.Slide
import androidx.transition.TransitionManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.datepicker.MaterialDatePicker
import com.rorono.a22recycler.*
import com.rorono.a22recycler.adapter.CurrencyAdapter
import com.rorono.a22recycler.adapter.OnItemClickListener
import com.rorono.a22recycler.databinding.FragmentCurrencyBinding
import com.rorono.a22recycler.models.Currency
import com.rorono.a22recycler.settings.Settings
import com.rorono.a22recycler.utils.FullNameCurrency
import com.rorono.a22recycler.utils.Rounding
import com.rorono.a22recycler.viewmodel.CurrencyViewModel
import java.util.*
import javax.inject.Inject
import kotlin.concurrent.fixedRateTimer


class CurrencyFragment :
    BaseViewBindingFragment<FragmentCurrencyBinding>(FragmentCurrencyBinding::inflate) {

    @Inject
    lateinit var factory: MainViewModelFactory
    private lateinit var viewModel: CurrencyViewModel
    private var adapter = CurrencyAdapter()
    private var behavior: BottomSheetBehavior<*>? = null

    override fun onAttach(context: Context) {
        (context.applicationContext as MyApplication).appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this, factory)[CurrencyViewModel::class.java]
        val date: EditText = binding.etDate
        date.hint = viewModel.getDate()
        viewModel.getCurrencyDao()
        viewModel.getSaveCurrencyDao()

        behavior = BottomSheetBehavior.from(binding.include.bottomSheet)
        binding.swipeRefreshLayout.setOnRefreshListener {
            getData(NetManager(requireContext()).isOnline(), date = date.hint.toString())
            binding.swipeRefreshLayout.isRefreshing = false
        }
        viewModel.date.observe(requireActivity()) {
            viewModel.getCurrency(it)
            date.hint = it
        }

        val (year, month, day) = createCalendar()
        binding.ivCalendar.setOnClickListener {
            val datePickerDialog = DatePickerDialog(
                view.context,
                { view, year, month, dayOfMonth ->
                    val month = (month + 1)

                    var day: String = dayOfMonth.toString()
                    var montSmallTen: String = month.toString()

                    if (month < 10) {
                        montSmallTen = "0$month"
                    }
                    if (dayOfMonth < 10) {
                        day = "0$dayOfMonth"
                    }
                    date.hint = "$year-$montSmallTen-$day"
                    viewModel.getCurrency(date = date.hint.toString())
                    viewModel.date.value = date.hint.toString()
                },
                year,
                month,
                day
            )
            val today = MaterialDatePicker.todayInUtcMilliseconds()
            datePickerDialog.datePicker.maxDate = today
            datePickerDialog.show()
        }

        binding.apply {
            recyclerView.layoutManager = GridLayoutManager(view.context, 3)
            recyclerView.adapter = adapter
        }

        viewModel.messageError.observe(viewLifecycleOwner) { error ->
            getToastError(error)
        }

        viewModel.listCurrency.observe(viewLifecycleOwner) { response ->
            adapter.submitList(response)
            searchCurrency(response)
        }
        adapter.setOnListener(object : OnItemClickListener {
            override fun onItemClick(currency: Currency, position: Int) {
                initializationBottomSheetBehavior(currency = currency)
                val roundedCurrency = Rounding.getTwoNumbersAfterDecimalPoint(currency.value)
                getCurrencyConvertorInRubles(roundedCurrency)
                getTransferRubles(roundedCurrency)
                val listFavorite = mutableListOf<Currency>()
                viewModel.saveCurrencyDatabase.observe(viewLifecycleOwner) { list ->
                    listFavorite.addAll(list)
                }
                val res = if (listFavorite.filter { it.fullName == currency.fullName }
                        .isNotEmpty()) R.drawable.ic_favorite else R.drawable.ic_favorite_border
                binding.include.ivChoseCurrency.setImageResource(res)

                binding.include.ivChoseCurrency.setOnClickListener {
                    val searchCurrency =
                        listFavorite.filter { cur -> cur.fullName == currency.fullName }
                            .isNotEmpty()
                    if (!searchCurrency) {
                        listFavorite.add(currency)
                        viewModel.setSaveCurrencyDao(listFavorite)
                        binding.include.ivChoseCurrency.setImageResource(R.drawable.ic_favorite)
                    } else {
                        listFavorite.remove(currency)
                        viewModel.deleteSaveCurrency(currency)
                        binding.include.ivChoseCurrency.setImageResource(R.drawable.ic_favorite_border)
                    }
                }

            }
        })

        binding.ivSearch.setOnClickListener {
            createAnimationOpenSearch()
        }
        binding.ivCancelSearch.setOnClickListener {
            cancelSearch()
        }
        binding.search.setOnCloseListener(object : SearchView.OnCloseListener {
            override fun onClose(): Boolean {
                binding.search.visibility = View.INVISIBLE
                return true
            }
        })
    }

    private fun getTransferRubles(roundedCurrency: Double) {
        binding.include.etTransferRubles.addTextChangedListener {
            if (binding.include.etTransferRubles.text.toString() != "" && binding.include.etTransferRubles.hasFocus()) {
                if (binding.include.etTransferRubles.text.toString() == ".") {
                    binding.include.etTransferRubles.setText("0.")
                    binding.include.etTransferRubles.setSelection(binding.include.etTransferRubles.length())
                }
                val enteredValue = binding.include.etTransferRubles.text.toString().toDouble()
                try {
                    binding.include.etCurrencyConvertor.setText(
                        viewModel.converterToCurrency(
                            roundedCurrency,
                            enteredValue
                        ).toString()
                    )
                } catch (e: Exception) {
                    Toast.makeText(
                        requireActivity(),
                        getString(R.string.entered_value_greater_zero),
                        Toast.LENGTH_LONG
                    ).show()
                }

            } else if (binding.include.etTransferRubles.text.isNullOrBlank()) {
                binding.include.etCurrencyConvertor.text?.clear()

            }
        }
    }

    private fun getCurrencyConvertorInRubles(roundedCurrency: Double) {
        binding.include.etCurrencyConvertor.addTextChangedListener {
            if (binding.include.etCurrencyConvertor.text.toString() != "" && binding.include.etCurrencyConvertor.hasFocus()) {
                if (binding.include.etCurrencyConvertor.text.toString() == ".") {
                    binding.include.etCurrencyConvertor.setText("0")
                }
                val enteredValue =
                    binding.include.etCurrencyConvertor.text.toString().toDouble()
                try {
                    binding.include.etTransferRubles.setText(
                        viewModel.transferToRubles(
                            roundedCurrency,
                            enteredValue = enteredValue
                        ).toString()
                    )
                } catch (e: Exception) {
                    Toast.makeText(
                        requireActivity(),
                        getString(R.string.entered_value_greater_zero),
                        Toast.LENGTH_LONG
                    ).show()
                }
            } else if (binding.include.etCurrencyConvertor.text.isNullOrBlank()) {
                binding.include.etTransferRubles.text?.clear()
                hideKeyboard()
            }
        }
    }


    private fun initializationBottomSheetBehavior(currency: Currency) {
        behavior!!.state = BottomSheetBehavior.STATE_EXPANDED
        if (Settings.loadLanguage(requireContext()) == 2) {
            if (FullNameCurrency.fullNameCurrency.isNotEmpty()) {
                val currencyHasMapFullName = FullNameCurrency.fullNameCurrency
                currency.fullName = currencyHasMapFullName.getValue(currency.charCode)
                binding.include.tvFullNameCurrency.text = currency.fullName
            }
        } else {
            binding.include.tvFullNameCurrency.text = currency.fullName
        }
        with(binding.include) {
            tvFullNameCurrency.text = currency.fullName
            textInputLayoutCurrencyConvertor.hint = currency.charCode
            charCodeTitle.text = currency.charCode
            etCurrencyConvertor.hint = getString(R.string._0)
            (Rounding.getTwoNumbersAfterDecimalPoint(currency.value).toString() + " ???").also {
                tvRate.text = it
            }
        }

    }

    private fun searchCurrency(response: List<Currency>) {
        binding.search.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.search.clearFocus()
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val n = mutableListOf<Currency>()
                binding.search.clearFocus()
                if (newText!!.isNotEmpty()) {
                    for (i in response) {
                        if (i.charCode.contains(newText.uppercase())) {
                            n.add(i)
                            adapter.submitList(n)
                        }
                    }
                } else {
                    adapter.submitList(response)
                }
                return false
            }
        })
    }

    override fun onResume() {
        super.onResume()
        getData(NetManager(context = requireContext()).isOnline(), viewModel.date.value!!)
    }

    fun Fragment.hideKeyboard() {
        view?.let { activity?.hideKeyboard(it) }
    }

    fun Activity.hideKeyboard() {
        hideKeyboard(currentFocus ?: View(this))
    }

    fun Context.hideKeyboard(view: View) {
        val inputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun createAnimationOpenSearch() {

        binding.ivCancelSearch.visibility = View.VISIBLE
        binding.search.visibility = View.VISIBLE
        val objectAnimator1 = ObjectAnimator.ofFloat(binding.search, TRANSLATION_X, 500f, 60f)
        val objectAnimator2 = ObjectAnimator.ofFloat(binding.search, ALPHA, 0f, 1f)
        val objectAnimator3 =
            ObjectAnimator.ofFloat(binding.ivCancelSearch, TRANSLATION_X, 400f, 20f)
        val objectAnimator4 = ObjectAnimator.ofFloat(binding.ivCancelSearch, ALPHA, 0f, 1f)
        val objectAnimator5 = ObjectAnimator.ofFloat(
            binding.tvTitleToolbar,
            TRANSLATION_X,
            binding.tvTitleToolbar.x,
            10f
        )
        val objectAnimator6 = ObjectAnimator.ofFloat(binding.tvTitleToolbar, ALPHA, 1f, 0f)
        val objectAnimator7 =
            ObjectAnimator.ofFloat(binding.ivSearch, TRANSLATION_X, binding.ivSearch.x, -100f)
        val objectAnimator8 = ObjectAnimator.ofFloat(binding.ivSearch, ALPHA, 1f, 0f)

        val animatorSet = AnimatorSet()
        animatorSet.playTogether(
            objectAnimator1,
            objectAnimator2,
            objectAnimator3,
            objectAnimator4,
            objectAnimator5,
            objectAnimator6,
            objectAnimator7,
            objectAnimator8
        )
        animatorSet.duration = 1000
        animatorSet.start()
        binding.tvTitleToolbar.visibility = View.GONE
        binding.ivSearch.visibility = View.GONE
    }

    private fun cancelSearch() {
        val slide = Slide()
        slide.slideEdge = Gravity.END
        TransitionManager.beginDelayedTransition(binding.contentLayout, slide)
        binding.tvTitleToolbar.visibility = View.VISIBLE
        binding.ivCancelSearch.visibility = View.GONE
        binding.ivSearch.visibility = View.VISIBLE
        binding.search.visibility = View.GONE
        val objectAnimator = ObjectAnimator.ofFloat(binding.search, "translationX", 510f)
        val objectAnimator5 = ObjectAnimator.ofFloat(
            binding.tvTitleToolbar, TRANSLATION_X, binding.tvTitleToolbar.x, 4f
        )
        val objectAnimator6 = ObjectAnimator.ofFloat(binding.tvTitleToolbar, ALPHA, 0f, 1f)
        val objectAnimator7 =
            ObjectAnimator.ofFloat(binding.ivSearch, TRANSLATION_X, binding.ivSearch.x, 20f)
        val objectAnimator8 = ObjectAnimator.ofFloat(binding.ivSearch, ALPHA, 0f, 1f)
        val animatorSet = AnimatorSet()
        animatorSet.playTogether(
            objectAnimator,
            objectAnimator5,
            objectAnimator6,
            objectAnimator7,
            objectAnimator8
        )
        animatorSet.duration = 1000
        animatorSet.start()
    }

    private fun getData(networkConnection: Boolean, date: String) {
        if (networkConnection) {
            binding.tvAttention.visibility = View.GONE
            viewModel.getCurrency(date)

        } else {
            viewModel.getCurrencyDao()
            binding.tvAttention.visibility = View.VISIBLE
            viewModel.currencyDatabase.observe(viewLifecycleOwner) { list ->
                val textAttention =
                    getString(R.string.attention_error_get_data) + " ${viewModel.date.value}"
                binding.tvAttention.text = textAttention
                adapter.submitList(list)

            }
        }
    }

    private fun getToastError(error: String) {
        Toast.makeText(requireContext(), error, Toast.LENGTH_LONG).show()
    }
}

private fun createCalendar(): Triple<Int, Int, Int> {
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)
    return Triple(year, month, day)
}















