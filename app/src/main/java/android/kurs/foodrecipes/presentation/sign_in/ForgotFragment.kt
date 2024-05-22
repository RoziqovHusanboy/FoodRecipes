package android.kurs.foodrecipes.presentation.sign_in

 import android.kurs.foodrecipes.databinding.FragmentLoginResetBinding
 import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForgotFragment:Fragment() {
    private lateinit var binding: FragmentLoginResetBinding
    private lateinit var mAuth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAuth = FirebaseAuth.getInstance()
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginResetBinding.inflate(inflater)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.cancel.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.reset.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            mAuth.sendPasswordResetEmail(email).addOnCompleteListener {
                if (it.isSuccessful){
                    Toast.makeText(requireContext(), "Check Your Email", Toast.LENGTH_SHORT).show();
                    findNavController().popBackStack()
                }else{
                    Toast.makeText(requireContext(), it.exception?.message, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}