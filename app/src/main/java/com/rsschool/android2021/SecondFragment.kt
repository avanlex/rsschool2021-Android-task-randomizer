package com.rsschool.android2021

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import java.util.concurrent.ThreadLocalRandom

class SecondFragment : Fragment() {

    private var backButton: Button? = null
    private var result: TextView? = null
    private var listener : MainOnClickListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = activity as MainOnClickListener

    }

    override fun onDetach() {
        super.onDetach()
        listener = null
//        callback.remove()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        result = view.findViewById(R.id.result)
        backButton = view.findViewById(R.id.back)

        val min = requireArguments().getInt(MIN_VALUE_KEY) ?: 0
        val max = requireArguments().getInt(MAX_VALUE_KEY) ?: 0

        val previousNumber = generate(min, max)
        result?.text = previousNumber.toString()

        backButton?.setOnClickListener {
            // implement back211
            parentFragmentManager.popBackStack()
        }

        backButton?.setOnClickListener {
            listener?.sendResult(previousNumber)
        }

        requireActivity()
            .onBackPressedDispatcher
            .addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    listener?.sendResult(previousNumber)
                }
            })
    }

    private fun generate(min: Int, max: Int) = (min..max).random()

    companion object {

        @JvmStatic
        fun newInstance(min: Int, max: Int): SecondFragment {
            val fragment = SecondFragment()
            val args = Bundle()

            // adding arguments
            args.putInt(MIN_VALUE_KEY, min)
            args.putInt(MAX_VALUE_KEY, max)
            fragment.arguments = args

            return fragment
        }

        private const val MIN_VALUE_KEY = "MIN_VALUE"
        private const val MAX_VALUE_KEY = "MAX_VALUE"
    }
}

