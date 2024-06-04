// AddressAdapter.kt
package com.android.ex11_googlemap

import android.location.Address
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.hanple.databinding.RecyclerviewItemBinding

class AddressAdapter(
    private val addressList: List<Address>,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<AddressAdapter.AddressViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(address: Address)
        fun onAddressClick(address: Address) // 텍스트뷰 클릭 인터페이스 추가
    }

    inner class AddressViewHolder(private val binding: RecyclerviewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(address: Address) {
            binding.tvItemAddress.text = address.getAddressLine(0)
            binding.root.setOnClickListener {
                listener.onItemClick(address)
            }
            binding.tvItemAddress.setOnClickListener { // 텍스트뷰 클릭 리스너 설정
                listener.onAddressClick(address)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressViewHolder {
        val binding = RecyclerviewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AddressViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AddressViewHolder, position: Int) {
        holder.bind(addressList[position])
    }

    override fun getItemCount(): Int = addressList.size
}
