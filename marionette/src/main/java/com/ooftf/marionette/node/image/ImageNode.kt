package com.ooftf.marionette.node.image

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.ooftf.marionette.node.BaseNode
import com.ooftf.marionette.node.NodeContext
import org.json.JSONObject

class ImageNode(context: NodeContext) : BaseNode<ImageView>(context) {
    override fun createView(): ImageView {
        return ImageView(context.context)
    }

    override fun handleAttrs(view: ImageView, layout: JSONObject) {
        super.handleAttrs(view, layout)
        view.adjustViewBounds = true
        handleAttr(layout,"url"){
            Glide.with(view).load(it).into(view)
        }
        handleAttr(layout,"scaleType"){
            when(it){
                "CENTER","center"->view.scaleType = ImageView.ScaleType.CENTER
                "CENTER_CROP","center_crop"->view.scaleType = ImageView.ScaleType.CENTER_CROP
                "FIT_XY","fit_xy"->view.scaleType = ImageView.ScaleType.FIT_XY
                "CENTER_INSIDE","center_inside"->view.scaleType = ImageView.ScaleType.CENTER_INSIDE
            }

        }
    }


}