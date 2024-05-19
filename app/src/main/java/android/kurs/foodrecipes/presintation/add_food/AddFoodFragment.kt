package android.kurs.foodrecipes.presintation.add_food

import android.app.Activity
import android.content.Intent
import android.kurs.foodrecipes.R
import android.kurs.foodrecipes.data.local.model.FoodAddModel
import android.kurs.foodrecipes.databinding.FragmentAddFoodBinding
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddFoodFragment:Fragment() {
private lateinit var binding:FragmentAddFoodBinding
private val viewModel:AddFoodViewModel by viewModels ()
    private val PICK_IMAGE_REQUEST = 1
    private lateinit var uri:Uri
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddFoodBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        UI()
    }

    private fun UI() {
    binding.chooseImage.setOnClickListener {
        openGalary()
    }
        showButton()
    }

    private fun openGalary() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            val selectedImageUri: Uri? = data?.data
            if (selectedImageUri != null) {
                binding.chooseImage.setImageURI(selectedImageUri)
                updateButtonColor(uri = selectedImageUri)
             }
        }
    }
    private fun showButton() {
        edittextWhatcher(binding.edittextName)
        edittextWhatcher(binding.edittextCategory)
        edittextWhatcher(binding.edittextDesc)
    }

    private fun updateButtonColor(uri: Uri?=null) {
        Log.d("TAG", "updateButtonColor:$uri ")
        val isEditTextEmpty = binding.edittextName.text.isNullOrEmpty() ||
                binding.edittextCategory.text.isNullOrEmpty() ||
                binding.edittextDesc.text.isNullOrEmpty()

        val buttonColorRes = if (isEditTextEmpty) R.color.button_back else R.color.green
        binding.save.setBackgroundColor(requireContext().getColor(buttonColorRes))

        if (!isEditTextEmpty) {
            val sendNameText =  binding.edittextName.text.toString()
             val sendCategoryText = binding.edittextCategory.text.toString()
             val sendDescText = binding.edittextDesc.text.toString()

             binding.save.setOnClickListener {
                 viewModel.saveFood(
                     FoodAddModel(
                         name = sendNameText,
                         category = sendCategoryText,
                         desc = sendDescText,
                         url =uri!!
                     )
                 )
                 Log.d("TAG", "updateButtonColor: $sendNameText, $sendCategoryText,$sendDescText,$uri")
                 binding.edittextCategory.text.clear()
                 binding.edittextDesc.text.clear()
                 binding.edittextName.text.clear()
                  findNavController().popBackStack()
             }

        }
    }
    private fun edittextWhatcher(text: EditText) {

        text.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (text.text.toString().trim().isNullOrEmpty()) {
                    text.setBackgroundResource(R.drawable.fragment_add_foods_background_is_empty)
                    updateButtonColor()
                } else {
                    text.setBackgroundResource(R.drawable.fragment_add_food_edittext_background)
                    updateButtonColor()
                }

            }
        })
    }
}