package cmsr.ipsacademy.net.activities.teacher.attendence.adapters

import android.content.Context
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import cmsr.ipsacademy.net.R
import cmsr.ipsacademy.net.activities.teacher.attendence.models.subjects.FacultySubjectDetailsModelItem
import cmsr.ipsacademy.net.activities.teacher.attendence.TakeAttendanceFragment
import kotlinx.android.synthetic.main.attendance_panel_table_list.view.*

class AttendancePanelViewAdapter(val context: Context) :
    RecyclerView.Adapter<AttendancePanelViewAdapter.RowViewHolder>() {


    lateinit var myList: List<FacultySubjectDetailsModelItem>


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.attendance_panel_table_list, parent, false)
        return RowViewHolder(itemView)
    }

    private fun setHeaderBg(view: View) {
        view.setBackgroundResource(R.drawable.table_header_cell_bg)
    }

    private fun setContentBg(view: View) {
        view.setBackgroundResource(R.drawable.table_content_cell_bg)
    }

    override fun onBindViewHolder(holder: RowViewHolder, position: Int) {
        val rowPos = holder.adapterPosition

        val modal = myList[position]

        holder.itemView.apply {
            setContentBg(txtSNo)
            setContentBg(txtBatch)
            setContentBg(txtDepartment)
            setContentBg(txtSubject)
            setContentBg(txtSubjectCode)
            setContentBg(btnAction)

            txtSNo.text = (holder.adapterPosition + 1).toString()
            txtBatch.text = modal.batch
            txtDepartment.text = modal.department
            txtSubject.text = modal.subject_name
            txtSubjectCode.text = modal.university_sub_code

            take_attendance_particular_subject_button.setOnClickListener {
                val fragment: TakeAttendanceFragment = TakeAttendanceFragment.newInstance()
                val activity = it.context as AppCompatActivity
                val bundle = Bundle()
                bundle.putString("clg_sub_code", modal.clg_sub_code)
                bundle.putString("batch_id", modal.batch_id)
                bundle.putString("semester", modal.semester)
                fragment.arguments = bundle
                activity.supportFragmentManager.beginTransaction()
                    .replace(R.id.frame_layout_container, fragment).addToBackStack(null).commit()
            }

        }
    }

    fun setData(newList: List<FacultySubjectDetailsModelItem>) {
        myList = newList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {

        return myList.size // one more to add header row
    }

    inner class RowViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}