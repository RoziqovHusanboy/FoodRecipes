package android.kurs.foodrecipes.presentation.sign_up

import android.kurs.foodrecipes.databinding.FragmentSignUpBinding
 import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : Fragment() {

    private lateinit var binding: FragmentSignUpBinding
    private lateinit var mAuth: FirebaseAuth
   lateinit var  username:String
    lateinit var email :String
    lateinit var password  :String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAuth = FirebaseAuth.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignUpBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.register.setOnClickListener {
            CreateUser()
        }
        binding.signIn.setOnClickListener{
            findNavController().navigate(SignUpFragmentDirections.toSignInFragment())
        }
    }

    private fun CreateUser() {
        username = binding.username.text.toString()
        email = binding.email.text.toString()
        password = binding.password.text.toString()
        if (TextUtils.isEmpty(username)) {
            binding.username.setError("Username cannot be empty")
            binding.username.requestFocus()
        } else if (TextUtils.isEmpty(email)) {
            binding.email.setError("Email cannot be empty")
            binding.email.requestFocus()
        } else if (TextUtils.isEmpty(password)){
            binding.password.setError("Password cannot be empty")
            binding.password.requestFocus()
        }else{
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener{
                if (it.isSuccessful){
                    Toast.makeText(requireContext(), "User registered successfully", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(SignUpFragmentDirections.toSignInFragment())
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