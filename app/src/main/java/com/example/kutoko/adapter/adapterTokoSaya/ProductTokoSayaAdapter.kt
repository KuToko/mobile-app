package com.example.kutoko.adapter.adapterTokoSaya

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kutoko.R
import com.example.kutoko.data.apiResponse.DataItem
import com.example.kutoko.data.apiResponse.ProductItem
import com.example.kutoko.databinding.MyProductItemBinding
import com.example.kutoko.ui.tokosaya.delete.DeleteProductActivity
import com.example.kutoko.ui.tokosaya.update.UpdateProductActivity

class ProductTokoSayaAdapter(private val listProduct: List<DataItem>) : RecyclerView.Adapter<ProductTokoSayaAdapter.ViewHolder>()
{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = MyProductItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val productItem = listProduct[position]
        Glide.with(holder.itemView)
            .load(productItem.productImage)
            .placeholder(R.drawable.ic_baseline_image_24)
            .into(holder.binding.ivProductImg)


        val bisnisId = productItem.businessId
        val name = productItem.name
        val price = productItem.price
        val image = productItem.productImage
        val desc = productItem.description
        val id = productItem.id

        holder.binding.tvNamaProduct.text = productItem.name
        holder.binding.tvHargaProduct.text = "Rp. ${productItem.price}"
        holder.binding.btEdit.setOnClickListener {
            val intent = Intent(holder.binding.root.context,UpdateProductActivity::class.java)
            if(bisnisId != null && name != null && price != null && desc != null && id != null){
                val product = ProductItem(bisnisId,id,name,price.toString(),desc,image)
                intent.putExtra(UpdateProductActivity.PRODUCT_ITEM,product)
                holder.binding.root.context.startActivity(intent)
            }
        }

        holder.binding.btDelete.setOnClickListener {
            val intent = Intent(holder.binding.root.context,DeleteProductActivity::class.java)
            intent.putExtra(DeleteProductActivity.PRODUCT_ID,id)
            if(bisnisId != null && name != null && price != null && desc != null && id != null){
                val product = ProductItem(bisnisId,id,name,price.toString(),desc,image)
                intent.putExtra(DeleteProductActivity.PRODUCT_ITEM,product)
                holder.binding.root.context.startActivity(intent)
            }

        }

    }

    override fun getItemCount(): Int = listProduct.size


    class ViewHolder(var binding: MyProductItemBinding) : RecyclerView.ViewHolder(binding.root)
}