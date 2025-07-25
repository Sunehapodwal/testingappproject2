package com.example.testingappproject

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testingappproject.RecyclerAdapter.OnClick
import com.example.testingappproject.databinding.FragmentHomefragmentBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import kotlin.printStackTrace

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Homefragment.newInstance] factory method to
 * create an instance of thdais fragment.
 */
class Homefragment : Fragment(), OnClick {
    lateinit var binding: FragmentHomefragmentBinding
    private lateinit var auth: FirebaseAuth
    lateinit var homescren: homescren
    var list = ArrayList<RecyclerDataClass>()
    private lateinit var recyclerView: RecyclerView
    lateinit var recyclerAdapter: RecyclerAdapter
    private var db = Firebase.firestore
    private val collection=db.collection("elements")
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homescren=activity as homescren
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentHomefragmentBinding.inflate(layoutInflater,container,false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()
        binding.recycler.layoutManager= LinearLayoutManager(homescren)
        recyclerView=view.findViewById(R.id.recycler)
        recyclerAdapter= RecyclerAdapter(list, this)
        recyclerAdapter.notifyDataSetChanged()
        binding.flotbtn.setOnClickListener {
            showAddDialog()
        }
    }

    private fun showAddDialog() {
        val dialog = Dialog(homescren)
        dialog.setContentView(R.layout.dialogopen)
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        val nameEditText = dialog.findViewById<EditText>(R.id.name)
        val descriptionEditText = dialog.findViewById<EditText>(R.id.description)
        val saveButton = dialog.findViewById<Button>(R.id.savebtn)

        saveButton.setOnClickListener {
            val name = nameEditText.text.toString()
            val description = descriptionEditText.text.toString()

            if (name.isEmpty() || description.isEmpty()) {
                Toast.makeText(homescren, "All fields are required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val data = hashMapOf("name" to name, "description" to description)


            db.collection("dialogue")
                .add(data)
                .addOnSuccessListener {
                    Toast.makeText(homescren, "Saved successfully!", Toast.LENGTH_SHORT).show()
                  //  dialog.dismiss()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(homescren, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }
            recyclerAdapter.notifyDataSetChanged()
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun showDeleteDialog() {
        AlertDialog.Builder(homescren)
            .setTitle("Delete")
            .setMessage("Are you sure you want to delete this item?")
            .setPositiveButton("Yes") { _, _ ->
                Toast.makeText(homescren, "Item deleted", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
            .setNeutralButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .setCancelable(true)
            .show()
         fun showUpdateDialog(position: Int) {
            val dialog = Dialog(homescren)
            dialog.setContentView(R.layout.dialogopen)
            dialog.window?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )

            val nameEditText = dialog.findViewById<EditText>(R.id.name)
            val descriptionEditText = dialog.findViewById<EditText>(R.id.description)
            val saveButton = dialog.findViewById<Button>(R.id.editBtn)

            val item = list[position]
            nameEditText.setText(item.name)
            descriptionEditText.setText(item.description)

            saveButton.setOnClickListener {
                val newName = nameEditText.text.toString()
                val newDescription = descriptionEditText.text.toString()

                if (newName.isEmpty() || newDescription.isEmpty()) {
                    Toast.makeText(homescren, "All fields are required", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                val docId = item.id
                if (docId != null) {
                    val updatedData = mapOf(
                        "name" to newName,
                        "description" to newDescription
                    )

                    db.collection("dialogue").document(docId)
                        .update(updatedData)
                        .addOnSuccessListener {
                            Toast.makeText(homescren, "Updated successfully!", Toast.LENGTH_SHORT).show()
                            // Update local list and notify adapter
                            list[position].name = newName
                            list[position].description = newDescription
                            recyclerAdapter.notifyItemChanged(position)
                            dialog.dismiss()
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(homescren, "Update failed: ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                } else {
                    Toast.makeText(homescren, "Error: Document ID missing", Toast.LENGTH_SHORT).show()
                }
            }

            dialog.show()
        }
    }

    override fun save(position: Int) {
        TODO("Not yet implemented")
    }

    override fun update(position: Int) {
        TODO("Not yet implemented")
    }

    override fun delete(position: Int) {
        TODO("Not yet implemented")
    }

    override fun onItemClick(position: Int) {
        TODO("Not yet implemented")
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Homefragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Homefragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}