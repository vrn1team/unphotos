package com.beloushkin.unphotos.view


import android.Manifest
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.FileProvider

import com.beloushkin.unphotos.R
import com.beloushkin.unphotos.extensions.loadNetworkImage
import com.beloushkin.unphotos.extensions.saveNetworkImageToFileAsync
import com.beloushkin.unphotos.model.Photo
import com.beloushkin.unphotos.model.User
import com.beloushkin.unphotos.util.getProgressDrawable
import kotlinx.android.synthetic.main.fragment_photo.*
import permissions.dispatcher.NeedsPermission
import permissions.dispatcher.RuntimePermissions
import java.io.File

@RuntimePermissions
class PhotoFragment : Fragment(),View.OnClickListener {

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
        fabShare.setOnClickListener(this)
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


    @NeedsPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    override fun onClick(v: View?) {
        if (v?.id == fabShare.id) {

            context?.let {
                // getExternalFilesDir() + "/Pictures" should match the declaration in fileprovider.xml paths
                val file = File(it.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                    "share_image_" + System.currentTimeMillis() + ".png")

                fullImage.saveNetworkImageToFileAsync(photo?.url?.regular,file)

                // wrap File object into a content provider. NOTE: authority here should match authority in manifest declaration
                val bmpUri = FileProvider.getUriForFile(it,
                    "com.beloushkin.unphotos.fileprovider", file)
                val intent = Intent().apply {
                    this.action = Intent.ACTION_SEND
                    this.putExtra(Intent.EXTRA_STREAM, bmpUri)
                    this.type = "image/jpeg"
                }
                startActivity(Intent.createChooser(intent, resources.getText(R.string.send_to)))
            }
        }
    }
}
