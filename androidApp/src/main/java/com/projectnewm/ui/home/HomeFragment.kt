package com.projectnewm.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import by.kirich1409.viewbindingdelegate.viewBinding
import com.projectnewm.HomeActivity
import com.projectnewm.R
import com.projectnewm.databinding.FragmentHomeBinding
import com.projectnewm.ui.genre.GenreFragment
import com.projectnewm.util.bindTabs
import dagger.hilt.android.AndroidEntryPoint

val pageTitles by lazy {
    arrayOf(
        "Explore",
        "Ambient",
        "Hip-Hop",
        "Alternative",
        "Classical"
    )
}

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private val binding: FragmentHomeBinding by viewBinding(FragmentHomeBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.pager.apply {
            adapter = GenrePagerAdapter(childFragmentManager, viewLifecycleOwner.lifecycle)
            bindTabs((activity as HomeActivity).binding.tabs) { tab, position ->
                tab.text = pageTitles[position]
            }
        }
    }
}

class GenrePagerAdapter(fm: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fm, lifecycle) {

    override fun getItemCount() = pageTitles.size

    override fun createFragment(position: Int): Fragment {
        return GenreFragment.newInstance(pageTitles[position])
    }
}