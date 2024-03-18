package android.kurs.foodrecipes.presintation.logSign_up

import android.kurs.foodrecipes.databinding.FragmentLoginUpBinding
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : Fragment() {

    private lateinit var binding: FragmentLoginUpBinding
    private lateinit var mAuth: FirebaseAuth
   lateinit var  email:String
    lateinit var password :String
    lateinit var confirmPassword  :String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAuth = FirebaseAuth.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginUpBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.signUp.setOnClickListener {
            CreateUser()
        }
    }

    private fun CreateUser() {
          email = binding.emailEditText.text.toString()
        password = binding.passwordEditText.text.toString()
        confirmPassword = binding.confirmPasswordEditText.text.toString()
        if (TextUtils.isEmpty(email)) {
            binding.emailEditText.setError("Email cannot be empty")
            binding.emailEditText.requestFocus()
        } else if (TextUtils.isEmpty(password)) {
            binding.passwordEditText.setError("Password cannot be empty")
            binding.passwordEditText.requestFocus()
        } else if (TextUtils.isEmpty(confirmPassword)) {
            binding.confirmPasswordEditText.setError("Password cannot be empty")
            binding.confirmPasswordEditText.requestFocus()
        }else if (confirmPassword != password){
            binding.confirmPasswordEditText.setError("Confirm password not equals to password")
            binding.confirmPasswordEditText.requestFocus()
        }else{
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener{
                if (it.isSuccessful){
                    Toast.makeText(requireContext(), "User registered successfully", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(SignUpFragmentDirections.toLogInFragment())
                }else{
                    Toast.makeText(requireContext(), "Registration Error", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    private fun sendEmailVerification(){
        val user: FirebaseUser? =mAuth.currentUser
        user.let {
            it?.sendEmailVerification()?.addOnCompleteListener {
                if (it.isSuccessful){
                    Toast.makeText(requireContext(), "email sent to $email", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

}