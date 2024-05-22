package android.kurs.foodrecipes.presentation.sign_in

import android.kurs.foodrecipes.databinding.FragmentSignInBinding
 import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInFragment : Fragment() {
    private lateinit var binding: FragmentSignInBinding
    private lateinit var mAuth: FirebaseAuth
     private lateinit var biometricPrompt: BiometricPrompt

    private val biometricPromptInfo = BiometricPrompt.PromptInfo.Builder()
        .setTitle("Используйте биометрический сканер для входа")
         .setNegativeButtonText("ОТМЕНА")
        .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAuth = FirebaseAuth.getInstance()
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignInBinding.inflate(inflater)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.signIn.setOnClickListener {
            loginUser()
        }
        binding.signUp.setOnClickListener {
            findNavController().navigate(SignInFragmentDirections.toSignUpFragment())
        }
        val user: FirebaseUser? = mAuth.currentUser
        user.let {
            binding.imgFinger.isVisible = it != null
        }
        if (binding.imgFinger.isVisible){
            checkBiometrik()
        }

        binding.forgot.setOnClickListener {
           findNavController().navigate(SignInFragmentDirections.toForgotFragment())
        }

    }

    private fun checkBiometrik() {
        if (checkBiometricSupport()) {
            biometricPrompt = BiometricPrompt(
                this,
                ContextCompat.getMainExecutor(requireContext()),
                authenticationCallback
            )
            biometricPrompt.authenticate(biometricPromptInfo)
        } else {
            binding.imgFinger.isVisible = false
            Toast.makeText(
                requireContext(),
                "Biometric authentication is not available",
                Toast.LENGTH_SHORT
            ).show()
        }
        binding.imgFinger.setOnClickListener {
            biometricPrompt.authenticate(biometricPromptInfo)
        }

    }

    private fun loginUser() {
        val email = binding.username.text.toString()
        val password = binding.password.text.toString()

        if (TextUtils.isEmpty(email)) {
            binding.username.setError("Email cannot be empty")
            binding.username.requestFocus()
        } else if (TextUtils.isEmpty(password)) {
            binding.password.setError("Password cannot be empty")
            binding.password.requestFocus()
        } else {
            mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        Toast.makeText(
                            requireContext(),
                            "User logged in successfully",
                            Toast.LENGTH_SHORT
                        ).show()
                       findNavController().navigate(SignInFragmentDirections.toHomeFragment())
                    } else {
                        Toast.makeText(requireContext(), "sign in failed", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
        }
    }

    private fun checkBiometricSupport(): Boolean {
        val biometricManager = androidx.biometric.BiometricManager.from(requireContext())
        return biometricManager.canAuthenticate() == androidx.biometric.BiometricManager.BIOMETRIC_SUCCESS
    }

    private val authenticationCallback = object : BiometricPrompt.AuthenticationCallback() {
        override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
            super.onAuthenticationError(errorCode, errString)
            Toast.makeText(requireContext(), "Authentication error: $errString", Toast.LENGTH_SHORT)
                .show()
        }

        override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
            super.onAuthenticationSucceeded(result)
            Toast.makeText(requireContext(), "Authentication succeeded", Toast.LENGTH_SHORT).show()
            findNavController().navigate(SignInFragmentDirections.toHomeFragment())
        }

        override fun onAuthenticationFailed() {
            super.onAuthenticationFailed()
            Toast.makeText(requireContext(), "Authentication failed", Toast.LENGTH_SHORT).show()
        }
    }

}