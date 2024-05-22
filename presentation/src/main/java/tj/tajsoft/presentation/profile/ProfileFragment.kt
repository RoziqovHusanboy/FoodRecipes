package android.kurs.foodrecipes.presintation.profile

import android.content.Intent
import android.graphics.BitmapFactory
import android.kurs.foodrecipes.R
import android.kurs.foodrecipes.databinding.FragmentProfileBinding
import tj.tajsoft.domain.repo.ProfileDialogCallBack
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi
@AndroidEntryPoint
class ProfileFragment : Fragment(), OnClickListener, ProfileDialogCallBack {

    private val PICK_IMAGE_REQUEST = 1
    private lateinit var binding: FragmentProfileBinding
    private lateinit var mAuth: FirebaseAuth
    private val viewModel by viewModels<ProfileViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

    @OptIn(DelicateCoroutinesApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        UI()
        subScribeToLiveData()
    }

    private fun UI() {
        val user = mAuth.currentUser
        user?.let {
            binding.nameGmail.text = it.email.toString()
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

    private fun subScribeToLiveData() = with(binding) {
        viewModel.profileImage.observe(viewLifecycleOwner){
            val storedBitmap =  it
            if (storedBitmap != null) {
                binding.imagePerson.setImageBitmap(storedBitmap)
            } else {
                binding.cardImage.setOnClickListener {
                    openGallery()
                }
            }
        }


        viewModel.phoneNumber.observe(viewLifecycleOwner){
            numberPhone.text = it
        }
        viewModel.fullName.observe(viewLifecycleOwner){
            nameFio.text = it
        }
        viewModel.location.observe(viewLifecycleOwner){
            nameLocation.text  =it
        }
        viewModel.birthDay.observe(viewLifecycleOwner){
            nameBirth.text = it
        }
    }

    private fun openDialogEdit(title: String, profileDialogCallBack: ProfileDialogCallBack) {
        val dialog = BottomSheetDialog(requireContext())
        dialog.setContentView(R.layout.dialog_bottom)
        val textViewName = dialog.findViewById<TextView>(R.id.textViewName)
        val edittext = dialog.findViewById<EditText>(R.id.edittext)
        val btnConfirm = dialog.findViewById<Button>(R.id.btnConfirm)
        val str = String.format("Сменить %s ?", title)
        textViewName?.setText(str)
        var text = String()
        edittext?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
              text = s.toString()
            }
        })
        btnConfirm?.setOnClickListener {
            profileDialogCallBack.saveToLocalStore(title,text)
            dialog.dismiss()
        }
        dialog.show()
    }
        private fun openGallery() {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intent.type = "image/*"
            startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && data != null && data.data != null) {
            val selectedImageUri: Uri = data.data!!
            binding.imagePerson.setImageURI(selectedImageUri)
            val bitmap = BitmapFactory.decodeStream(
                requireContext().contentResolver.openInputStream(selectedImageUri)
            )
            viewModel.saveImageToSharedPreferences(bitmap)
            Toast.makeText(context, "Image selected", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Failed to select image", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.imageViewEditPhone -> openDialogEdit("Phone Number", this)
            R.id.imageViewEditFIO -> openDialogEdit("Full Name", this)
            R.id.imageViewEditBirth -> openDialogEdit("Birth of Date", this)
            R.id.imageViewLocation -> openDialogEdit("Location", this)
        }
    }

    override fun saveToLocalStore(title: String, store: String) {
        when (title) {
            "Phone Number" -> viewModel.savePhoneNumber(store)
            "Full Name" -> viewModel.saveFullName(store)
            "Birth of Date" -> viewModel.saveBirthDay(store)
            "Location" -> viewModel.saveLocation(store)
        }
    }
}