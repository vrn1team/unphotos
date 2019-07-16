package com.beloushkin.unphotos.view


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.beloushkin.unphotos.R
import com.beloushkin.unphotos.extensions.loadNetworkImage
import com.beloushkin.unphotos.model.Photo
import com.beloushkin.unphotos.model.PhotoUrl
import com.beloushkin.unphotos.model.User
import com.beloushkin.unphotos.util.getProgressDrawable
import kotlinx.android.synthetic.main.fragment_photo.*
import kotlinx.android.synthetic.main.item_photo.*

class PhotoFragment : Fragment() {

    var photo: Photo? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_photo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {

            photo = PhotoFragmentArgs.fromBundle(it).photo
            context?.let { cont ->
                loadFullImage(photo, cont)
                tvAuthor.text = photo?.user?.username
                tvDescription.text = photo?.description
                photo?.user?.let { user ->
                    loadUserImage(user, cont)
                }
            }

        }
    }

    private fun loadFullImage(photo: Photo?, context: Context) {
        photo?.let {
            fullImage.loadNetworkImage(photo.url?.regular, getProgressDrawable(context))
        }

    }

    private fun loadUserImage(user: User?, context: Context) {
        user?.let {
            userAvatar.loadNetworkImage(it.profileImage?.small, getProgressDrawable(context))
        }

    }


}
