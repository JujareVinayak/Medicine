package com.vinayak.medicine.presentation.ui.fragment

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.icu.util.Calendar
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.vinayak.medicine.R
import com.vinayak.medicine.data.model.items.DiseaseItem
import com.vinayak.medicine.data.model.items.MedicineItem
import com.vinayak.medicine.databinding.FragmentProblemsListBinding
import com.vinayak.medicine.presentation.ui.adapter.ProblemsAdapter
import com.vinayak.medicine.presentation.viewmodel.ProblemsViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 *
 */
@Suppress("DEPRECATION")
@AndroidEntryPoint
class ProblemsListFragment : Fragment(R.layout.fragment_problems_list) {

    private val problemsViewModel by viewModels<ProblemsViewModel>()
    private var problemsAdapter: ProblemsAdapter? = null
    private var userName: String? = ""
    private val problemsList = mutableListOf<Any>()
    private val problemItems = mutableListOf<DiseaseItem>()
    private var _binding: FragmentProblemsListBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProblemsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intentFilter = IntentFilter()
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION)
        requireActivity().registerReceiver(networkReceiver, intentFilter)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            userName = ProblemsListFragmentArgs.fromBundle(it).username
        }
        problemsAdapter = ProblemsAdapter() {
            navigateToMedicineDetailsScreen(it)
        }
        binding.apply {
            greetMessage.text = getGreetMessage(userName)
            problemsList.adapter = problemsAdapter
        }
        if (isInternetConnected()) {
            initProblemsObserver()
            problemsViewModel.getProblemsList()
        } else {
            initOfflineData()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initProblemsObserver() {
        problemsViewModel.problems.observe(viewLifecycleOwner) {
            problemsList.clear()
            it?.let { problems ->
                problems.diseases.forEach { diseaseItem ->
                    problemsList.add(diseaseItem)
                    problemsViewModel.insertDisease(diseaseItem)
                    val medicines = problems.medicines.filter { medicineItem ->
                        medicineItem.disease.equals(
                            diseaseItem.disease,
                            true
                        )
                    }
                    problemsList.addAll(medicines)
                    problemsViewModel.insertMedicines(medicines)
                }
            } ?: run {
                initOfflineData()
            }
            problemsAdapter?.apply {
                submitList(problemsList)
                notifyDataSetChanged()
            }
        }
    }

    private fun initOfflineData() {
        problemsViewModel.getOfflineDiseasesList().observe(viewLifecycleOwner) {
            problemsList.clear()
            problemItems.clear()
            it?.let {
                problemItems.addAll(it)
                problemsViewModel.getOfflineMedicinesList()
            }
        }
        problemsViewModel.getOfflineMedicinesList().observe(viewLifecycleOwner) {
            problemItems.forEach { diseaseItem ->
                problemsList.add(diseaseItem)
                val medicineItems = it.filter { medicineItem ->
                    medicineItem.disease.equals(
                        diseaseItem.disease,
                        true
                    )
                }
                problemsList.addAll(medicineItems)
            }
            problemsAdapter?.apply {
                submitList(problemsList)
                notifyDataSetChanged()
            }
        }
    }

    private fun getGreetMessage(userName: String?): String {
        var greetMessage = ""
        val hrs = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        if (hrs in 5..11)
            greetMessage = "Good Morning"
        else if (hrs in 12..17)
            greetMessage = "Good Afternoon"
        else if (hrs in 18..23)
            greetMessage = "Good Evening"
        return "$greetMessage $userName. Here is your medicine list."
    }

    private fun isInternetConnected(): Boolean {
        val connectivityManager =
            requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

    //Broadcast Receiver for the internet.
    private val networkReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val action = intent!!.action
            when (action) {
                ConnectivityManager.CONNECTIVITY_ACTION -> {
                    val connManager =
                        context!!.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                    val netInfos = connManager.allNetworkInfo
                    for (netInfo in netInfos) {
                        when (netInfo.type) {
                            ConnectivityManager.TYPE_MOBILE -> {
                                if (netInfo.state == NetworkInfo.State.CONNECTED)
                                    problemsViewModel.getProblemsList()
                            }
                            ConnectivityManager.TYPE_MOBILE_HIPRI -> {
                                if (netInfo.state == NetworkInfo.State.CONNECTED)
                                    problemsViewModel.getProblemsList()
                            }
                            ConnectivityManager.TYPE_WIFI -> {
                                if (netInfo.state == NetworkInfo.State.CONNECTED)
                                    problemsViewModel.getProblemsList()
                            }

                        }
                    }

                }
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        requireActivity().unregisterReceiver(networkReceiver)
    }

    private fun navigateToMedicineDetailsScreen(medicineItem: MedicineItem) {
        findNavController().navigate(
            ProblemsListFragmentDirections.actionProblemsListFragmentToMedicineDetailsFragment(
                medicineItem
            )
        )
    }
}