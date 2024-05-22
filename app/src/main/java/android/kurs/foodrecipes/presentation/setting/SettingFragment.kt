package android.kurs.foodrecipes.presentation.setting

import android.content.Intent
import android.kurs.foodrecipes.databinding.FragmentSettingBinding

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class SettingFragment : Fragment() {
    private lateinit var binding: FragmentSettingBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.profile.setOnClickListener {
            findNavController().navigate(SettingFragmentDirections.actionSettingFragmentToProfileFragment())
        }
        UI()
    }

    private fun UI() {

        binding.share.setOnClickListener {
            val appPackageName = requireContext().packageName
            val sendIntent = Intent()
            sendIntent.action = Intent.ACTION_SEND
            sendIntent.putExtra(
                Intent.EXTRA_TEXT,
                "Check out the App at: https://play.google.com/store/apps/details?id=$appPackageName" // after adding app to play store, will change link
            )
            sendIntent.type = "text/plain"
            requireContext().startActivity(sendIntent)
        }

        binding.question.setOnClickListener {
            Intent(Intent.ACTION_VIEW, Uri.parse("https://telegram.me/itrun_kids_tj")).apply {
                startActivity(this)
            }
        }

        binding.site.setOnClickListener {
            Intent(Intent.ACTION_VIEW, Uri.parse("http://itrun.tj/")).apply {
                startActivity(this)
            }
        }

        binding.logout.setOnClickListener {
            requireActivity().finish()
        }
    }
}