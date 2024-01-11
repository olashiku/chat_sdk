package com.olashiku.chatsdk.views.base

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.olashiku.chatsdk.extensions.addBundle
import com.olashiku.chatsdk.storage.PaperPrefs
import com.olashiku.chatsdk.views.loader.Loader
import org.koin.java.KoinJavaComponent.inject

interface NavigationActions {
    fun openFragment(action: Int, showBottomNavigation: Boolean = false)
    fun popFragment()
    fun openDialog(action: Int)
    fun openFragmentWithData(action: Int, vararg value: Pair<Any, Any>)
}

open class BaseFragment: Fragment(), NavigationActions {

    val paperPrefs:PaperPrefs by inject(PaperPrefs::class.java)

    var loader: Loader? = null
    override fun onAttach(context: Context) {
        super.onAttach(context)
        loader = Loader(requireContext())
    }

    override fun onDestroy() {
        super.onDestroy()
        loader = null
    }

    fun showLoader(){
        loader?.showLoader()
    }

     fun hideLoader(){
         loader?.hideLoader()
     }

    override fun openFragment(action: Int, showBottomNavigation: Boolean) {
        findNavController().navigate(action)
    }

    override fun popFragment() {
        findNavController().popBackStack()
    }

    override fun openDialog(action: Int) {
        findNavController().navigate(action)
    }

    override fun openFragmentWithData(action: Int, vararg value: Pair<Any, Any>) {
        findNavController().navigate(action, addBundle(*value))

    }


}