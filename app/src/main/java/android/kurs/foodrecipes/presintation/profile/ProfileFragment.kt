package android.kurs.foodrecipes.presintation.profile

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.kurs.foodrecipes.R
import android.kurs.foodrecipes.databinding.FragmentProfileBinding
import android.kurs.foodrecipes.presintation.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.auth.FirebaseAuth
import java.io.ByteArrayOutputStream

class ProfileFragment : Fragment(),OnClickListener {

    private lateinit var sharedPreferences: SharedPreferences
    private val PICK_IMAGE_REQUEST = 1
    private lateinit var binding: FragmentProfileBinding
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences = SharedPreferences(requireContext(), "imageview")
        mAuth = FirebaseAuth.getInstance()
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val user = mAuth.currentUser
        user?.let {
            binding.nameGmail.text = it.email.toString()
        }
        val storedBitmap = loadImageFromSharedPreferences()

        if (storedBitmap != null) {
            binding.imagePerson.setImageBitmap(storedBitmap)
        } else {
            binding.cardImage.setOnClickListener {
                openGallery()
            }
        }
        binding.imageTitle.setOnClickListener {
            openGallery()
        }

        binding.iconBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.imageViewEditPhone.setOnClickListener(this)
        binding.imageViewEditBirth.setOnClickListener(this)
        binding.imageViewEditFIO.setOnClickListener(this)


    }

    private fun openDialogEdit(title: String,editedText:String) {
        var  newEditedText = editedText
        val dialog = BottomSheetDialog(requireContext())
        dialog.setContentView(R.layout.dialog_bottom)
        val textViewName = dialog.findViewById<TextView>(R.id.textViewName)
        val edittext = dialog.findViewById<EditText>(R.id.edittext)
        val str = String.format("Сменить %s ?", title)
        textViewName?.setText(str)

        edittext?.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                newEditedText = s.toString()
            }
        })
        dialog.show()
    }

    private fun openGallery() {
        try {
            val intent = Intent(MediaStore.ACTION_PICK_IMAGES)
            intent.type = "image/*"
            startActivityForResult(intent, PICK_IMAGE_REQUEST)

        } catch (e: Exception) {
            Toast.makeText(
                requireContext(),
                "selected large image should be not over 5mb",
                Toast.LENGTH_SHORT
            ).show()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        try {
            val selectedImageUri: Uri = data?.data!!
            binding.imagePerson.setImageURI(selectedImageUri)
            val bitmap = BitmapFactory.decodeStream(
                requireContext().contentResolver.openInputStream(selectedImageUri!!)
            )
            saveImageToSharedPreferences(bitmap)
            Toast.makeText(
                context,
                "Image selected",
                Toast.LENGTH_SHORT
            ).show()

        } catch (e: Exception) {
            android.util.Log.d("TAG", "onActivityResult: $e")
        }
    }

    private fun saveImageToSharedPreferences(bitmap: Bitmap) {
        val imageString = encodeImage(bitmap)
        sharedPreferences.save("image", imageString.toUri())
    }

    private fun encodeImage(bitmap: Bitmap): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }

    private fun loadImageFromSharedPreferences(): Bitmap? {
        val imageString = sharedPreferences.get("image")
        return if (imageString != null && imageString.isNotEmpty()) {
            decodeImage(imageString)
        } else {
            null
        }
    }

    private fun decodeImage(imageString: String): Bitmap {
        val byteArray = Base64.decode(imageString, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.imageViewEditPhone->{
                val string = String()
                openDialogEdit("номер",string)
                val sharedPhone = SharedPreferencesProfile(requireContext(),"phone")
                sharedPhone.save("phone",string)
            }
            R.id.imageViewEditFIO->{
                val string = String()
                openDialogEdit("ФИО",string)
                val sharedPhone = SharedPreferencesProfile(requireContext(),"fio")
                sharedPhone.save("fio",string)
            }
            R.id.imageViewEditBirth ->{
                val string = String()
                openDialogEdit("дата рождения",string)
                val sharedPhone = SharedPreferencesProfile(requireContext(),"birth")
                sharedPhone.save("birth",string)
            }
        }
    }
}