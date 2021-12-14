package com.rongcheng.hi.ui.app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.rongcheng.hi.ui.app.databinding.FragmentFirstBinding
import com.rongcheng.hi.ui.tab.bottom.HiTabBottomInfo

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val homeInfo =
            HiTabBottomInfo(
                name = "首页",
                iconFont = "fonts/iconfont.ttf",
                defaultIconName = getString(R.string.if_home),
                selectedIconName = null,
                defaultColor = "#ff656667",
                tintColor = "#ffd44949"
            )
//        binding.buttonFirst.setOnClickListener {
//            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
//        }
        binding.hiTabBottom.setHiTabInfo(homeInfo)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}