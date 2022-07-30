package cmsr.ipsacademy.net.activities.Class

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import cmsr.ipsacademy.net.R
import java.util.*


class ClassAttendanceFragment : Fragment(R.layout.fragment_class_attendance) {

    private var actionSpinner: Spinner? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_class_attendance, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        actionSpinner = requireView().findViewById(R.id.spinner)
        val actions = resources.getStringArray(R.array.actionofspinner)

        val adapter = ArrayAdapter(
            requireActivity(),
            android.R.layout.simple_spinner_item, actions
        )
        actionSpinner!!.adapter = adapter
        actionSpinner!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View,
                position: Int,
                id: Long
            ) {
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        val datePicker: Button = requireView().findViewById(R.id.datePickerButton)
        datePicker.setOnClickListener {
            val dpd = DatePickerDialog(
                requireContext(), DatePickerDialog.OnDateSetListener { view, year, month, day ->
                    datePicker.setText("" + day + "/" + month + "/" + year)
                }, year, month, day
            )
            dpd.show()
        }
        val datePicker2: Button = requireView().findViewById(R.id.datePickerButton2)
        datePicker2.setOnClickListener {
            val dpd = DatePickerDialog(
                requireContext(), DatePickerDialog.OnDateSetListener { view, year, month, day ->
                    datePicker.setText("" + day + "/" + month + "/" + year)
                }, year, month, day
            )
            dpd.show()
        }

    }

}