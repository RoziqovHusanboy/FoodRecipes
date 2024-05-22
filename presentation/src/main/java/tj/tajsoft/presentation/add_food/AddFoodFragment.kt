package android.kurs.foodrecipes.presintation.add_food

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.kurs.foodrecipes.R
import tj.tajsoft.data.local.model.FoodAddModel
import android.kurs.foodrecipes.databinding.FragmentAddFoodBinding
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import java.io.ByteArrayOutputStream

@AndroidEntryPoint
class AddFoodFragment : Fragment() {
    private lateinit var binding: FragmentAddFoodBinding
    private val viewModel: AddFoodViewModel by viewModels()
    private val PICK_IMAGE_REQUEST = 1

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
        if (requestCode == PICK_IMAGE_REQUEST && data != null && data.data != null) {
            val selectedImageUri: Uri = data.data!!
            binding.chooseImage.setImageURI(selectedImageUri)
            val bitmap = BitmapFactory.decodeStream(
                requireContext().contentResolver.openInputStream(selectedImageUri)
            )
            updateButtonColor(bitmap)
            Toast.makeText(context, "Image selected", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Failed to select image", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showButton() {
        edittextWhatcher(binding.edittextName)
        edittextWhatcher(binding.edittextCategory)
        edittextWhatcher(binding.edittextDesc)
    }

    private fun updateButtonColor(bitmap: Bitmap? =null) {
        Log.d("TAG", "updateButtonColor:$bitmap ")
        val imageString = encodeImage(bitmap!!)
        val isEditTextEmpty = binding.edittextName.text.isNullOrEmpty() ||
                binding.edittextCategory.text.isNullOrEmpty() ||
                binding.edittextDesc.text.isNullOrEmpty()

        val buttonColorRes = if (isEditTextEmpty) R.color.button_back else R.color.green
        binding.save.setBackgroundColor(requireContext().getColor(buttonColorRes))

        if (!isEditTextEmpty) {
            val sendNameText = binding.edittextName.text.toString()
            val sendCategoryText = binding.edittextCategory.text.toString()
            val sendDescText = binding.edittextDesc.text.toString()

            binding.save.setOnClickListener {
                viewModel.saveNewFood(
                     requireContext(),
                        FoodAddModel(
                            name = sendNameText,
                            category = sendCategoryText,
                            desc = sendDescText,
                            url = imageString.toUri().toString()
                        )
                )
                Log.d(
                    "TAG",
                    "updateButtonColor: $sendNameText, $sendCategoryText,$sendDescText,$bitmap"
                )
                findNavController().popBackStack()
            }

        }
    }
    private fun encodeImage(bitmap: Bitmap): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
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