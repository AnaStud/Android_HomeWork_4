package ru.anasoft.android_homework_4

import android.os.Bundle
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import ru.anasoft.android_homework_4.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.switchGetNotifications.setOnCheckedChangeListener { _, isChecked ->
            binding.checkboxAboutAutorization.isChecked = isChecked
            binding.checkboxAboutAutorization.isEnabled = isChecked
            binding.checkboxAboutNews.isChecked = isChecked
            binding.checkboxAboutNews.isEnabled = isChecked
            buttonSaveCondition()
        }

        val score = Random.nextInt(101)
        binding.progress.progress = score
        binding.textViewProgress.text = "$score/100"

        binding.editTextName.doOnTextChanged { _, _, _, _ -> buttonSaveCondition() }
        binding.editTextPhone.doOnTextChanged { _, _, _, _ -> buttonSaveCondition() }
        binding.radioGroupGender.setOnCheckedChangeListener { _: RadioGroup, _: Int -> buttonSaveCondition() }
        binding.checkboxAboutAutorization.addOnCheckedStateChangedListener { _, _ -> buttonSaveCondition() }
        binding.checkboxAboutNews.addOnCheckedStateChangedListener { _, _ -> buttonSaveCondition() }

        binding.buttonSave.setOnClickListener {
            Toast.makeText(this, getString(R.string.messageSaved), Toast.LENGTH_SHORT).show()
        }
    }

    fun buttonSaveCondition() {
        val nameLength = binding.editTextName.text?.length ?:0
        val phoneLength = binding.editTextPhone.text?.length ?:0
        val checkedRadioButtonId = binding.radioGroupGender.checkedRadioButtonId
        val switchIsChecked = binding.switchGetNotifications.isChecked

        if (nameLength in 1..40
            && phoneLength > 0
            && checkedRadioButtonId > -1
            && (!switchIsChecked
                    || (switchIsChecked
                        && (binding.checkboxAboutAutorization.isChecked
                            || binding.checkboxAboutNews.isChecked)))
            ) {

            binding.buttonSave.isEnabled = true
        } else {
            binding.buttonSave.isEnabled = false
        }
    }
}