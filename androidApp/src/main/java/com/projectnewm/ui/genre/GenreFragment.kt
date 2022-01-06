package com.projectnewm.ui.genre

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewpager2.adapter.FragmentStateAdapter
import by.kirich1409.viewbindingdelegate.viewBinding
import com.projectnewm.R
import com.projectnewm.databinding.FragmentBannerPageBinding
import com.projectnewm.databinding.FragmentGenreBinding
import com.projectnewm.util.bindTabs
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GenreFragment : Fragment(R.layout.fragment_genre) {

    companion object {
        const val ARG_GENRE = "ARG_GENRE"

        fun newInstance(genre: String): GenreFragment {
            return GenreFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_GENRE, genre)
                }
            }
        }
    }

    private val viewModel: GenreViewModel by viewModels()

    private val binding: FragmentGenreBinding by viewBinding(FragmentGenreBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.bannerPager.apply {
            adapter = BannerAdapter(this@GenreFragment)
            bindTabs(binding.bannerTabs)
        }
    }

    class BannerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
        override fun getItemCount(): Int = 6

        override fun createFragment(position: Int): Fragment {
            return BannerPageFragment()
        }
    }

    class BannerPageFragment : Fragment(R.layout.fragment_banner_page) {

        private val binding: FragmentBannerPageBinding by viewBinding(FragmentBannerPageBinding::bind)

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        }
    }
}