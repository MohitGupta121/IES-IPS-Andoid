package cmsr.ipsacademy.net.activities.StudentResultFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import cmsr.ipsacademy.net.R

class StudentResultFragment : Fragment(R.layout.fragment_student_result) {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val actions = resources.getStringArray(R.array.selectSection)
        var spinner = requireView().findViewById<Spinner>(R.id.studentResultSection_selectSection)
        val aa = ArrayAdapter(requireActivity(), android.R.layout.simple_spinner_item, actions)
        spinner?.adapter = aa
        spinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        var btnSubmit = requireView().findViewById<Button>(R.id.submit)
        btnSubmit.setOnClickListener {
            Toast.makeText(requireContext(), "processing", Toast.LENGTH_LONG).show()
        }

    }
}