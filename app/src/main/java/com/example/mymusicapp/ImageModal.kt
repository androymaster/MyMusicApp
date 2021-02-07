package com.example.mymusicapp

import java.io.Serializable

class ImageModal : Serializable {

    var image:Int? = null

    constructor(image: Int){
        this.image = image
    }
}