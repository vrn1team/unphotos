package com.beloushkin.unphotos.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.beloushkin.unphotos.R
import com.beloushkin.unphotos.extensions.loadNetworkImage
import com.beloushkin.unphotos.model.Photo
import com.beloushkin.unphotos.util.getProgressDrawable
import kotlinx.android.synthetic.main.item_photo.view.*

class PhotoListAdapter(
    private val photoList: ArrayList<Photo>
): RecyclerView.Adapter<PhotoListAdapter.PhotoViewHolder>() {

    fun updatePhotosList(newList: List<Photo>) {
        photoList.clear()
        photoList.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_photo,parent, false)
        return PhotoViewHolder(view)
    }

    override fun getItemCount() = photoList.size

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val currPhoto = photoList[position]
        holder.view.photoDescription.text = currPhoto.description
        holder.view.photoAuthor.text = currPhoto.user?.username
        holder.view.photoImage.loadNetworkImage(currPhoto.url?.regular, getProgressDrawable(holder.view.context))
        holder.view.photoLayout.setOnClickListener {
            val action = ListFragmentDirections.actionDetail(currPhoto)
            Navigation.findNavController(holder.view).navigate(action)
        }
    }

    class PhotoViewHolder(var view: View): RecyclerView.ViewHolder(view)
}