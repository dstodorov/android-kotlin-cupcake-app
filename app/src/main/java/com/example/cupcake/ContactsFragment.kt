package com.example.cupcake

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.cupcake.databinding.FragmentContactsBinding
import com.example.cupcake.model.Client
import com.example.cupcake.model.OrderViewModel

class ContactsFragment : Fragment() {

    private var binding: FragmentContactsBinding? = null
    private val sharedViewModel: OrderViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val fragmentBinding = FragmentContactsBinding.inflate(inflater, container, false)
        binding = fragmentBinding

        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = sharedViewModel
            contactsFragment = this@ContactsFragment
        }
    }

    fun goToNextScreen() {
        var emptyField: Boolean = false
        if (binding?.firstName?.text.isNullOrEmpty()) {
            emptyField = true
            binding?.firstName?.error = getString(R.string.empty_field)
        }
        if (binding?.lastName?.text.isNullOrEmpty()) {
            emptyField = true
            binding?.lastName?.error = getString(R.string.empty_field)
        }
        if (binding?.email?.text.isNullOrEmpty()) {
            emptyField = true
            binding?.email?.error = getString(R.string.empty_field)
        }
        if (binding?.phoneNumber?.text.isNullOrEmpty()) {
            emptyField = true
            binding?.phoneNumber?.error = getString(R.string.empty_field)
        }

        if (!emptyField) {
            val client = Client(
                binding?.firstName?.text.toString(),
                binding?.lastName?.text.toString(),
                binding?.email?.text.toString(),
                binding?.phoneNumber?.text.toString()
            )
            sharedViewModel.setClient(client)
            findNavController().navigate(R.id.action_contactsFragment_to_summaryFragment)
        }

    }

    /**
     * This fragment lifecycle method is called when the view hierarchy associated with the fragment
     * is being removed. As a result, clear out the binding object.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    fun cancelOrder() {
        sharedViewModel.resetOrder()
        findNavController().navigate(R.id.action_contactsFragment_to_startFragment)
    }
}