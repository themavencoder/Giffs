package com.example.breezil.giffs.utils

import android.os.Environment
import com.example.breezil.giffs.BuildConfig

class Constant{


    companion object {
        @JvmField val GIF = "single_gif"
        @JvmField val STORAGE_PERMISSION_CODE = 99

        @JvmField val ZERO = 0
        @JvmField val ONE = 1
        @JvmField val TWO = 2
        @JvmField val FIVE_HUNDRED = 500
        @JvmField val ONE_THOUSAND = 2000
        @JvmField val TWO_THOUSAND = 1000
        @JvmField val FOUR = 4
        @JvmField val FIVE = 5
        @JvmField val TEN = 10
        @JvmField val ONE_HUNDRED = 100


        @JvmField val ROOT_DIR = Environment.getExternalStorageDirectory().path
        @JvmField val GIFFS_FOLDER = "$ROOT_DIR/Pictures/GIFFS"

    }
}
