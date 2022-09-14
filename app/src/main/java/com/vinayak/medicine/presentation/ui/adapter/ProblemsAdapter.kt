package com.vinayak.medicine.presentation.ui.adapter

import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vinayak.medicine.R
import com.vinayak.medicine.data.model.items.MedicineItem
import com.vinayak.medicine.data.model.items.DiseaseItem
import com.vinayak.medicine.databinding.ViewMedicineItemBinding
import com.vinayak.medicine.databinding.ViewProblemItemBinding


/**
 *
 */
class ProblemsAdapter(val listener: (medicineItems: MedicineItem) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var list: List<Any> = mutableListOf()

    fun submitList(list: List<Any>) {
        this.list = list
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            PROBLEM_VIEW_TYPE -> {
                val binding = ViewProblemItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return ProblemViewHolder(binding)
            }
            MEDICINE_VIEW_TYPE -> {
                val binding = ViewMedicineItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return MedicineViewHolder(binding)
            }
            else -> {
                return null!!
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ProblemViewHolder -> {
                holder.bind(list[position] as DiseaseItem)
            }
            is MedicineViewHolder -> {
                holder.bind(list[position] as MedicineItem)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (list[position] is DiseaseItem) {
            PROBLEM_VIEW_TYPE
        } else {
            MEDICINE_VIEW_TYPE
        }
    }

    inner class ProblemViewHolder(private val binding: ViewProblemItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(diseaseItem: DiseaseItem) {
            binding.apply {
                sectionHeaderText.text = diseaseItem.disease
                sectionHeaderCount.text = diseaseItem.medicineCount.toString()
            }
        }
    }

    inner class MedicineViewHolder(private val binding: ViewMedicineItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(medicineItem: MedicineItem) {
            binding.apply {
                root.setOnClickListener {
                    listener(medicineItem)
                }
                val name = SpannableString("Name: ${medicineItem.name}")
                name.setSpan(
                    ForegroundColorSpan(binding.root.context.getColor(R.color.teal_200)),
                    INT_SIX,
                    name.length,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                val dose = SpannableString("Dose: ${medicineItem.dose}")
                dose.setSpan(
                    ForegroundColorSpan(binding.root.context.getColor(R.color.teal_200)),
                    INT_SIX,
                    dose.length,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                val strength = SpannableString("Strength: ${medicineItem.strength}")
                strength.setSpan(
                    ForegroundColorSpan(binding.root.context.getColor(R.color.teal_200)),
                    INT_TEN,
                    strength.length,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                this.name.text = name
                this.dose.text = dose
                this.strength.text = strength
            }
        }
    }

    companion object {
        private const val PROBLEM_VIEW_TYPE = 1
        private const val MEDICINE_VIEW_TYPE = 2
        private const val INT_SIX = 6
        private const val INT_TEN = 10
    }

    override fun getItemCount() = list.size
}