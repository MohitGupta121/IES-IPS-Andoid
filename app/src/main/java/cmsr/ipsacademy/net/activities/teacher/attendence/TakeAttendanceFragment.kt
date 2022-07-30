package cmsr.ipsacademy.net.activities.teacher.attendence

import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import cmsr.ipsacademy.net.activities.teacher.attendence.adapters.TakeAttendanceStudentsListAdapter
import cmsr.ipsacademy.net.api.ApiSet
import cmsr.ipsacademy.net.api.controller
import cmsr.ipsacademy.net.databinding.FragmentTakeAttendanceBinding
import cmsr.ipsacademy.net.helpers.AppConstants
import cmsr.ipsacademy.net.helpers.SharedPreferencesHelper
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import kotlin.collections.ArrayList

class TakeAttendanceFragment : Fragment() {

    private lateinit var clg_sub_code: String
    private lateinit var batch_id: String
    private lateinit var semester: String
    private lateinit var binding: FragmentTakeAttendanceBinding
    private lateinit var myAdapter: TakeAttendanceStudentsListAdapter
    private lateinit var sharedPreferencesHelper: SharedPreferencesHelper
    private lateinit var faculty_computer_code: String
    private lateinit var date: String
    private lateinit var lecture_type: String
    private lateinit var time_slot_id: String
    private lateinit var topic_id: String
    private lateinit var lab_group: String
    private val studentList: ArrayList<String> = ArrayList()
    private val presentStudentList: ArrayList<String> = ArrayList()
    private val absentStudentList: ArrayList<String> = ArrayList()

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        myAdapter = TakeAttendanceStudentsListAdapter(requireContext())

        sharedPreferencesHelper = SharedPreferencesHelper(requireContext())
        sharedPreferencesHelper.getValueString(AppConstants.computer_code)
            ?.let { faculty_computer_code = it }

        val arguments = this.arguments
        if (arguments != null) {
            clg_sub_code = arguments.getString("clg_sub_code").toString()
            batch_id = arguments.getString("batch_id").toString()
            semester = arguments.getString("semester").toString()
        }

        getSelectTopics()
        getLectureType()
        getTimeSlots()
        setStudentGroup()
        selectToadyDate()
        getAllStudents()
        setupStudentsDetailsRecyclerView()
        submitAttendance()


    }

    private fun submitAttendance() {

        var latest_record_id: String = "7854"

        binding.takeAttendanceSubmitButton.setOnClickListener {

//            Log.e("present", presentStudentList.size.toString())
//
//            println(studentList.minus(presentStudentList.toSet()))
//
            absentStudentList.addAll(studentList.minus(presentStudentList.toSet()))
//            println("Absent:   " + absentStudentList.toSet())

            lifecycleScope.launch(Dispatchers.IO) {
                val res = controller.getInstance().create(ApiSet::class.java)
                    .submitAttendance(
                        batch_id,
                        faculty_computer_code,
                        "2022-07-29",
                        lecture_type,
                        time_slot_id,
                        topic_id,
                        "AB",
                        "192.APP.IP"
                    ).execute()

                if (res.isSuccessful) {

                    Log.d(
                        "SUBMIT",
                        res.body()
                            .toString()
                    )

                    withContext(Dispatchers.Main) {
                        latest_record_id = res.body()!!.latest_id
                        Toast.makeText(
                            requireContext(),
                            "Attendance Submit + ${latest_record_id}",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                }

                Log.e("students", "present:" + presentStudentList.size + "absent: " + absentStudentList.size)

                for (i in 0 until presentStudentList.size) {
                    val comp = presentStudentList[i]
                    Log.e("present", comp + latest_record_id)
                    controller.getInstance().create(ApiSet::class.java)
                        .presentStudent(comp, latest_record_id, "1").execute()
                }

                for (i in 0 until absentStudentList.size) {
                    val comp = absentStudentList[i]
                    Log.e("absent", comp + latest_record_id)
                    controller.getInstance().create(ApiSet::class.java)
                        .presentStudent(comp, latest_record_id, "0").execute()
                }

            }

        }


    }

    private fun getAllStudents() {
        lifecycleScope.launch(Dispatchers.IO) {
            val res = controller.getInstance().create(ApiSet::class.java)
                .getStudentsByBatchId(batch_id, semester).execute()

            if (res.body() != null) {
                Log.d(
                    "SelectTopics",
                    "Topics-" + res.body()
                        .toString() + "\n"
                )

                withContext(Dispatchers.Main) {
                    myAdapter.setData(res.body()!!)
                    myAdapter.notifyDataSetChanged()
                    binding.takeAttendanceStudentsNameRecyclerview.adapter = myAdapter

                    for (i in 0 until res.body()!!.size) {
                        studentList.add(res.body()!![i].computer_code)
                    }
                }
            }
        }
    }

    private fun setupStudentsDetailsRecyclerView() {
        binding.takeAttendanceStudentsNameRecyclerview.layoutManager =
            LinearLayoutManager(requireContext())
        myAdapter.setOnClickListener(object :
            TakeAttendanceStudentsListAdapter.onItemClickListener {
            override fun onItemClick(position: Int) {
                presentStudentList.add(studentList[position])
            }

        })
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun selectToadyDate() {
        binding.takeAttendanceSelectDateSpinner.setOnClickListener {

            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val dpd = DatePickerDialog(
                requireContext(),
                { _, year, monthOfYear, dayOfMonth ->
                    binding.takeAttendanceSelectDateSpinner.text =
                        ("$year - $monthOfYear - $dayOfMonth")
                    date = binding.takeAttendanceSelectDateSpinner.text as String
                },
                year,
                month,
                day
            )
            dpd.show()
        }

    }

    private fun setStudentGroup() {

        val groupItems: ArrayList<String> = ArrayList()
        groupItems.add("Both A & B")
        groupItems.add("A")
        groupItems.add("B")

        val adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, groupItems)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.takeAttendanceSelectGroupSpinner.adapter = adapter

    }

    private fun getTimeSlots() {

        lifecycleScope.launch(Dispatchers.IO) {
            val res = controller.getInstance().create(ApiSet::class.java)
                .getTimeSlots().execute()

            if (res.body() != null) {
                Log.d(
                    "SelectTopics",
                    "Topics-" + res.body()
                        .toString() + "\n"
                )

                withContext(Dispatchers.Main) {

                    val lectureItems: ArrayList<String> = ArrayList()

                    for (i in 0 until res.body()!!.size) {
                        lectureItems.add(res.body()!![i].start_time + " - " + res.body()!![i].end_time)
                        setTimeSlotSpinner(lectureItems)
                    }

                }
            }
        }

    }

    private fun setTimeSlotSpinner(timeItem: java.util.ArrayList<String>) {
        val adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, timeItem)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.takeAttendanceSelectTimeSlotsSpinner.adapter = adapter

        binding.takeAttendanceSelectTimeSlotsSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {
                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    time_slot_id = (position + 1).toString()
                }
            }

    }

    private fun getLectureType() {

        lifecycleScope.launch(Dispatchers.IO) {
            val res = controller.getInstance().create(ApiSet::class.java)
                .getLectureTypes().execute()

            if (res.body() != null) {
                Log.d(
                    "SelectTopics",
                    "Topics-" + res.body()
                        .toString() + "\n" + res.body()!![0].lecture_type
                )

                withContext(Dispatchers.Main) {

                    val lectureItems: ArrayList<String> = ArrayList()
                    for (i in 0 until res.body()!!.size) {
                        lectureItems.add(res.body()!![i].lecture_type)
                        setLectureTypeSpinner(lectureItems)
                    }

                }
            }
        }

    }

    private fun setLectureTypeSpinner(lectureItems: ArrayList<String>) {
        val adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, lectureItems)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.takeAttendanceSelectLectureTypeSpinner.adapter = adapter

        binding.takeAttendanceSelectLectureTypeSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {
                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    lecture_type = (position + 1).toString()
                }
            }
    }

    private fun getSelectTopics() {

        lifecycleScope.launch(Dispatchers.IO) {
            val res = controller.getInstance().create(ApiSet::class.java)
                .getTopicFromBatchId(clg_sub_code).execute()

            if (res.body() != null) {
                Log.d(
                    "SelectTopics",
                    "Topics-" + res.body()
                        .toString() + "\n"
                )

                withContext(Dispatchers.Main) {

                    val topicsName: ArrayList<String> = ArrayList()
                    val topicsID: ArrayList<String> = ArrayList()

                    for (i in 0 until res.body()!!.size) {
                        topicsName.add(res.body()!![i].topic_name)
                        topicsID.add(res.body()!![i].topic_id)
                        setTopicSpinner(topicsName, topicsID)
                    }
                }

            } else {
                val topicsItems: ArrayList<String> = ArrayList()
                val topicsID: ArrayList<String> = ArrayList()
                topicsItems.add(" ")
                topicsID.add(" ")
                setTopicSpinner(topicsItems, topicsID)
            }
        }

    }

    private fun setTopicSpinner(topicItem: ArrayList<String>, topicsID: ArrayList<String>) {

        val adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, topicItem)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.takeAttendanceSubjectTopicSpinner.adapter = adapter

        binding.takeAttendanceSubjectTopicSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {
                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    topic_id = topicsID[position]
                }
            }

    }

    companion object {
        fun newInstance() = TakeAttendanceFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val fragmentBinding = FragmentTakeAttendanceBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

}