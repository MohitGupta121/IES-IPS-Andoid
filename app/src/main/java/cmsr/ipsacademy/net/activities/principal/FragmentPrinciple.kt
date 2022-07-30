package cmsr.ipsacademy.net.activities.principal

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import cmsr.ipsacademy.net.R
import com.google.android.material.navigation.NavigationView


class FragmentPrinciple : Fragment(R.layout.fragment_fargment_principal_ui) {

    var duty = arrayOf("Counselling", "Set Category Criteria", "view Students")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val navigationView: NavigationView = requireView().findViewById(R.id.navigation_menu)

        val spinner =
            navigationView.menu.findItem(R.id.navigation_drawer_item3).actionView as Spinner
        val aa = ArrayAdapter(requireActivity(),android.R.layout.simple_spinner_item,duty)
        spinner.adapter =aa
        spinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View,
                position: Int,
                id: Long
            ) {
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

      return super.onCreateView(inflater, container, savedInstanceState)
    }

}