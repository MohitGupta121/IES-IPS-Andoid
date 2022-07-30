package cmsr.ipsacademy.net.activities.teacher.attendence

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import cmsr.ipsacademy.net.activities.teacher.attendence.adapters.AttendancePanelViewAdapter
import cmsr.ipsacademy.net.api.ApiSet
import cmsr.ipsacademy.net.api.controller
import cmsr.ipsacademy.net.databinding.FragmentFacultyAssignedSubjectsBinding
import cmsr.ipsacademy.net.helpers.AppConstants
import cmsr.ipsacademy.net.helpers.SharedPreferencesHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FacultyAssignedSubjectsFragment : Fragment() {

    private lateinit var binding: FragmentFacultyAssignedSubjectsBinding
    private lateinit var sharedPreferencesHelper: SharedPreferencesHelper
    private lateinit var myAdapter: AttendancePanelViewAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPreferencesHelper = SharedPreferencesHelper(requireContext())

        sharedPreferencesHelper.getValueString(AppConstants.computer_code)
            ?.let { getSubjectsDetails(it) }

        setupRecyclerview()
    }

    private fun getSubjectsDetails(computer_code: String) {

        lifecycleScope.launch(Dispatchers.IO) {
            val res = controller.getInstance().create(ApiSet::class.java)
                .getFacultySubjectsDetails(computer_code).execute()

            if (res.body() != null) {
                Log.d(
                    "test",
                    "Faculty Subjects-" + res.body()
                        .toString() + "\n" + res.body()!!.size.toString() + "\n" + res.body()!![0].department.toString()
                )
                withContext(Dispatchers.Main) {
                    myAdapter = AttendancePanelViewAdapter(requireContext())
                    myAdapter.setData(res.body()!!)
                    myAdapter.notifyDataSetChanged()
                    binding.recyclerViewAttendancePanel.adapter = myAdapter
                }

            }
        }

    }

    private fun setupRecyclerview() {
        binding.recyclerViewAttendancePanel.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val fragmentBinding =
            FragmentFacultyAssignedSubjectsBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }
}