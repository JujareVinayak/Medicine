/* 
* Lowe's Companies Inc., Android Application
* Copyright (C)  Lowe's Companies Inc.
*
* The Lowe's Application is the private property of
* Lowe's Companies Inc. Any distribution of this software
* is unlawful and prohibited.
*/
package com.vinayak.medicine.presentation.ui.fragment

import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.vinayak.medicine.R
import com.vinayak.medicine.databinding.FragmentMedicineDetailsBinding

/**
 *
 */
class MedicineDetailsFragment : Fragment(R.layout.fragment_medicine_details) {

    private var _binding: FragmentMedicineDetailsBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMedicineDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            MedicineDetailsFragmentArgs.fromBundle(it).medicine?.let {
                binding.apply {
                    val name = SpannableString("Name: ${it.name}")
                    name.setSpan(
                        ForegroundColorSpan(binding.root.context.getColor(R.color.teal_200)),
                        INT_SIX,
                        name.length,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                    val dose = SpannableString("Dose: ${it.dose}")
                    dose.setSpan(
                        ForegroundColorSpan(binding.root.context.getColor(R.color.teal_200)),
                        INT_SIX,
                        dose.length,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                    val strength = SpannableString("Strength: ${it.strength}")
                    strength.setSpan(
                        ForegroundColorSpan(binding.root.context.getColor(R.color.teal_200)),
                        INT_TEN,
                        strength.length,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                    this.disease.text = it.disease
                    this.name.text = name
                    this.dose.text = dose
                    this.strength.text = strength
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val INT_SIX = 6
        private const val INT_TEN = 10
    }
}