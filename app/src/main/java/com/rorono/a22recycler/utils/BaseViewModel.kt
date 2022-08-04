package com.rorono.a22recycler.utils

import android.app.Application
import androidx.lifecycle.*

abstract class BaseViewModel<T>(
    protected val state: MutableLiveData<T> = MutableLiveData(), application: Application
) : AndroidViewModel(application) {
    fun getStateLiveData(): LiveData<T> = state
    fun observeState(owner: LifecycleOwner, onChanged: (newState: T) -> Unit) {
        state.observe(owner, Observer { onChanged(it!!) })
    }
}