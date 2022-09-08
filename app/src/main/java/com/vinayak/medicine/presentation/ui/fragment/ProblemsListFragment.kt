/* 
* Lowe's Companies Inc., Android Application
* Copyright (C)  Lowe's Companies Inc.
*
* The Lowe's Application is the private property of
* Lowe's Companies Inc. Any distribution of this software
* is unlawful and prohibited.
*/
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
import com.vinayak.medicine.data.model.items.MedicineItem
import com.vinayak.medicine.data.model.items.ProblemItem
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
    val problemsList = mutableListOf<Any>()
    val problemItems = mutableListOf<ProblemItem>()
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
        initProblemsObserver()
        if (isInternetConnected()) {
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
                problems.problems.forEach { problem ->
                    val problemItem = ProblemItem(
                        problem.disease,
                        problem.medicines.size.toString()
                    )
                    problemsList.add(problemItem)
                    problemsViewModel.insertProblem(problemItem)
                    problem.medicines.forEach { medicine ->
                        val medicineItem = MedicineItem(
                            medicine.name,
                            medicine.dose,
                            medicine.strength,
                            problem.disease
                        )
                        problemsList.add(medicineItem)
                        problemsViewModel.insertMedicine(medicineItem)
                    }
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
        problemsViewModel.getOfflineProblemsList().observe(viewLifecycleOwner) {
            it?.let {
                problemItems.addAll(it)
                problemsViewModel.getOfflineMedicinesList()
            }
        }
        problemsViewModel.getOfflineMedicinesList().observe(viewLifecycleOwner) {
            problemItems.forEach { problemItem ->
                problemsList.add(problemItem)
                it.forEach { medicineItem ->
                    if (problemItem.disease.equals(medicineItem.disease, true)) {
                        problemsList.add(medicineItem)
                    }
                }
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