package edu.kamshanski.daggerstudy.ui.base

import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import edu.kamshanski.daggerstudy.ViewModelFactory
import javax.inject.Inject

abstract class BaseFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @MainThread
    inline fun <reified VM : ViewModel> Fragment.vm(): Lazy<VM> = viewModels { viewModelFactory }
}