package android.kurs.foodrecipes.presintation.login

import android.annotation.SuppressLint
import android.content.Intent
import android.hardware.biometrics.BiometricManager
import android.kurs.foodrecipes.databinding.FragmentLoginInBinding
import android.os.Bundle
import android.provider.Settings
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
import java.util.concurrent.Executor

@AndroidEntryPoint
class LogInFragment:Fragment() {
    private lateinit var binding: FragmentLoginInBinding
    private lateinit var mAuth: FirebaseAuth
    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAuth = FirebaseAuth.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginInBinding.inflate(inflater)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.signIn.setOnClickListener {
            loginUser()
        }
        binding.createAccount.setOnClickListener {
            findNavController().navigate( LogInFragmentDirections.actionLogInFragmentToSignUpFragment())
        }
        val user:FirebaseUser? = mAuth.currentUser
        user.let {
            binding.imgFinger.isVisible = it != null
        }

        loginWithFinger()

        binding.forgotTextview.setOnClickListener {
            findNavController().navigate(LogInFragmentDirections.actionLogInFragmentToForgotFragment())
        }

    }

    private fun loginUser() {
        val email = binding.emailEditText.text.toString()
        val password = binding.passwordEditText.text.toString()

        if (TextUtils.isEmpty(email)) {
            binding.emailEditText.setError("Email cannot be empty")
            binding.emailEditText.requestFocus()
        } else if (TextUtils.isEmpty(password)) {
            binding.passwordEditText.setError("Password cannot be empty")
            binding.passwordEditText.requestFocus()
        }else{
            mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        Toast.makeText(
                            requireContext(),
                            "User logged in successfully",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                        findNavController().navigate(LogInFragmentDirections.actionLogInFragmentToHomeFragment2())
                    }else{
                        Toast.makeText(requireContext(), "sign in failed", Toast.LENGTH_SHORT)
                            .show()
                }
            }
        }
    }
    fun loginWithFinger(){
        executor = ContextCompat.getMainExecutor(requireContext())
        biometricPrompt = BiometricPrompt(requireActivity(),executor,
            @SuppressLint("NewApi")
            object:BiometricPrompt.AuthenticationCallback(){

                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    Toast.makeText(requireContext(), "Authentication error: $errString", Toast.LENGTH_SHORT).show()
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    Toast.makeText(requireContext(), "Authentication succeeded", Toast.LENGTH_SHORT).show()
                 findNavController().navigate(LogInFragmentDirections.actionLogInFragmentToHomeFragment2())
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    Toast.makeText(requireContext(), "Authentication failed", Toast.LENGTH_SHORT).show()
                }
            })

        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Sample Title")
            .setSubtitle("Sample Subtitle")
            .setNegativeButtonText("sample setNegativeButtonText")
            .build()

        binding.imgFinger.setOnClickListener {
            biometricPrompt.authenticate(promptInfo)
        }
    }

//    @SuppressLint("WrongConstant")
//    private fun checkDeviceHasBiometric() {
//        val biometricManager = androidx.biometric.BiometricManager.from(requireContext())
//        when (biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.DEVICE_CREDENTIAL)) {
//            BiometricManager.BIOMETRIC_SUCCESS -> {
//                binding.textFinger.text = "App can authenticate using biometric"
//                binding.loginBTN.isEnabled = true
//            }
//
//            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
//                binding.textFinger.text = "Biometric features are currently unavailable"
//                binding.loginBTN.isEnabled = false
//            }
//
//            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
//                val enrollIntent = Intent(Settings.ACTION_BIOMETRIC_ENROLL).apply {
//                    putExtra(
//                        Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED,
//                        BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.DEVICE_CREDENTIAL
//                    )
//                }
//                binding.loginBTN.isEnabled = false
//                startActivityForResult(enrollIntent,100)
//            }
//
//
//        }
//
//    }
}