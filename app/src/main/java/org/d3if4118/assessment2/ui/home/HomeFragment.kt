package org.d3if4118.assessment2.ui.home

import android.annotation.SuppressLint
import android.app.Application
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.item_rv_suhu.view.*
import org.d3if4118.assessment2.R
import org.d3if4118.assessment2.database.DatabaseSuhu
import org.d3if4118.assessment2.database.Suhu
import org.d3if4118.assessment2.database.SuhuDao
import org.d3if4118.assessment2.databinding.DialogEditDataBinding
import org.d3if4118.assessment2.databinding.FragmentCalculateBinding
import org.d3if4118.assessment2.utils.AdapterUtil

class HomeFragment : Fragment() {

    private var _binding: FragmentCalculateBinding? = null

    private val binding get() = _binding!!

    // memanggil diaryViewModel
    lateinit var application: Application
    lateinit var dataSource: SuhuDao
    lateinit var viewModelFactory: HomeDataViewModelFactory
    lateinit var homeProsesViewModel: HomeProsesViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCalculateBinding.inflate(layoutInflater, container, false)

        application = requireNotNull(this.activity).application
        dataSource = DatabaseSuhu.getInstance(application).suhuBadanDao
        viewModelFactory = HomeDataViewModelFactory(dataSource, application)
        homeProsesViewModel =
            ViewModelProviders.of(this, viewModelFactory).get(HomeProsesViewModel::class.java)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        simpanOnClick()

        homeProsesViewModel.listSuhu.observe(viewLifecycleOwner, Observer {
            binding.rvHistory.adapter =
                AdapterUtil(R.layout.item_rv_suhu, it, { position, itemView, item ->
                    itemView.tvSuhu.text = StringBuilder(item.jenisSuhu).append(" : ${item.suhu}")
                    itemView.btnEdit.setOnClickListener {
                        showDialog(item)
                    }
                    itemView.btnDelete.setOnClickListener {
                        deleteData(item)
                    }
                }, { _, item ->

                })

        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun simpanOnClick() {


        binding.btFahrenheit.setOnClickListener {
            val text = binding.etCelcius.text?.toString()
            if (text != "") {
                val suhuFahrenheit = 9.0 / 5.0 * text!!.toDouble() + 32
                homeProsesViewModel.onClickSimpanData(
                    suhuFahrenheit,
                    "Fahrenheit"
                )
                binding.inputFiled.editText?.setText("")
                Toast.makeText(requireContext(), "Berhasil Masukan Data", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Gagal Masukan Data", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btKelvin.setOnClickListener {
            val text = binding.etCelcius.text?.toString()
            if (text != "") {
                val suhuKelvin = text!!.toDouble() + 273
                homeProsesViewModel.onClickSimpanData(
                    suhuKelvin,
                    "Kelvin"
                )
                binding.inputFiled.editText?.setText("")
                Toast.makeText(requireContext(), "Berhasil Masukan Data", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Gagal Masukan Data", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btReamur.setOnClickListener {
            val text = binding.etCelcius.text?.toString()
            if (text != "") {
                val suhuReamur = 4.0 / 5.0 * text.toString().toDouble()
                homeProsesViewModel.onClickSimpanData(
                    suhuReamur,
                    "Reamur"
                )
                Toast.makeText(requireContext(), "Berhasil Masukan Data", Toast.LENGTH_SHORT).show()

            } else {
                Toast.makeText(requireContext(), "Gagal Masukan Data", Toast.LENGTH_SHORT).show()
            }
        }


    }

    @SuppressLint("SetTextI18n")
    private fun showDialog(suhu: Suhu) {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_edit_data)
        val layoutInflater = LayoutInflater.from(requireContext())

        //Binding Here
        val binding = DialogEditDataBinding.inflate(layoutInflater)
        dialog.setContentView(binding.root)
        dialog.setCancelable(true)
        dialog.show()

        binding.tvSuhuStatus.text = "Suhu : ${suhu.jenisSuhu}"
        binding.etCelcius.setText(suhu.suhu.toString())

        binding.btUbah.setOnClickListener {
            if (binding.etCelcius.text.toString() != "") {
                homeProsesViewModel.onClickUpdate(
                    Suhu(
                        suhu.id,
                        binding.etCelcius.text.toString().toDouble(),
                        suhu.jenisSuhu
                    )
                )
                Toast.makeText(requireContext(), "Berhasil Ubah Data", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            } else {
                Toast.makeText(requireContext(), "Isi Data", Toast.LENGTH_SHORT).show()
            }

        }

        binding.btBatal.setOnClickListener {
            dialog.dismiss()
        }
    }

    private fun deleteData(suhu: Suhu) {
        val builder = AlertDialog.Builder(requireContext())
            .setMessage("Apakah Anda Yakin Ingin Hapus Data ?")
            .setPositiveButton("Hapus") { _, _ ->
                homeProsesViewModel.onClickHapus(suhu.id)
                Toast.makeText(requireContext(), "Kata Berhasil Dihapus", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton(R.string.batal) { dialog, _ ->
                dialog.cancel()
            }
        builder.show()
    }
}