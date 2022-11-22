package com.siuuu.mercatec.ui.values

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Environment
import android.util.Base64
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

class ImageEncodeAndDecode {
    companion object{
        fun encode(image: File): String {
            val image = BitmapFactory.decodeFile(image.absolutePath)

            // Encode image to base64 string
            val baos = ByteArrayOutputStream()
            image.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            var imageBytes = baos.toByteArray()
            val imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT)
            return imageString
        }

        fun decode(imageString: String, directoryToSave: File) : File {

            // Decode base64 string to image
            val imageBytes = Base64.decode(imageString, Base64.DEFAULT)
            val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)

            //imageview.setImageBitmap(decodedImage)
            return bitmapToFile(imageBytes,directoryToSave)!!
        }

        fun bitmapToFile(bitmap: ByteArray, directoryToSave: File): File? {
            //create a file to write bitmap data
            var file: File? = null
            return try {
                //file = File(Environment.getExternalStorageDirectory().toString()+"/Android/data/com.siuuu.mercatec/files/Pictures" + File.separator + fileNameToSave)
                file = File.createTempFile("mercatec_img_",".jpg",directoryToSave)
                file.createNewFile()

                //write the bytes in file
                val fos = FileOutputStream(file)
                fos.write(bitmap)
                fos.flush()
                fos.close()
                file
            } catch (e: Exception) {
                e.printStackTrace()
                file // it will return null
            }
        }
    }




}