package com.example.breezil.giffs.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import androidx.core.content.FileProvider
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.example.breezil.giffs.R
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class GifUtils (context: Context){



    private fun galleryAddPic(context: Context, imagePath: String) {
        val mediaScanIntent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
        val f = File(imagePath)
        val contentUri = Uri.fromFile(f)
        mediaScanIntent.data = contentUri
        context.sendBroadcast(mediaScanIntent)
    }

    fun startDownloading(context: Context, gifDrawable: GifDrawable): String? {
        var savedImagePath: String? = null
        // Create the new file in the external storage
        val timeStamp = SimpleDateFormat(
            context.getString(R.string.date_format),
            Locale.getDefault()
        ).format(Date())
        val gifFileName = context.getString(R.string.app_name) + timeStamp + context.getString(R.string.gif)
        val storageDir = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString() + context.getString(
                R.string._slash_giffs
            )
        )
        var success = true
        if (!storageDir.exists()) {
            success = storageDir.mkdirs()
        }



        // Save the new Bitmap
        if (success) {

            val imageFile = File(storageDir, gifFileName)
            savedImagePath = imageFile.absolutePath
            try {
                val fileOut = FileOutputStream(imageFile)
                val byteBuffer = gifDrawable.buffer
                val arr = ByteArray(byteBuffer.remaining())
                byteBuffer.rewind()
                byteBuffer.get(arr)
                fileOut.write(arr)
                fileOut.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }


            // Add the image to the system gallery
            galleryAddPic(context, savedImagePath)

        }

        return savedImagePath
    }

    fun getLocalDrawableUri(context: Context, gifDrawable: GifDrawable): Uri {
        var bmpUri: Uri? = null
        val file = File(
            context.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
            (context.getString(R.string.share_gif_) +
                    System.currentTimeMillis() + context.getString(R.string.gif)))
        val out: FileOutputStream?
        try {
            out = FileOutputStream(file)
            val byteBuffer = gifDrawable.buffer
            val arr = ByteArray(byteBuffer.remaining())
            byteBuffer.rewind()
            byteBuffer.get(arr)
            out.write(arr)
            try {
                out.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            bmpUri = FileProvider.getUriForFile(context, context.applicationContext .packageName + ".provider", file)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return bmpUri!!
    }

}


