package com.geekhub_android_2019.cherkasyguide.ui.routeslist

import android.view.View
import android.widget.ImageView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyAttribute.*
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.bumptech.glide.Glide
import com.geekhub_android_2019.cherkasyguide.R
import com.geekhub_android_2019.cherkasyguide.common.BaseEpoxyHolder
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView

@EpoxyModelClass(layout = R.layout.fragment_routes_group_separator)
abstract class RouteHeaderModel: EpoxyModelWithHolder<RouteHeaderModel.VH>() {

    @EpoxyAttribute
    lateinit var name: String

    @EpoxyAttribute(Option.DoNotHash)
    lateinit var listener: View.OnClickListener

    override fun bind(holder: VH) {
        holder.groupName.text = name

        holder.showMapBtn.setOnClickListener(listener)
    }

    class VH: BaseEpoxyHolder() {
        val groupName by bind<MaterialTextView>(R.id.groupName)
        val showMapBtn by bind<MaterialButton>(R.id.showRouteMap)
    }
}

@EpoxyModelClass(layout = R.layout.fragment_routes_place_card)
abstract class PlaceCardModel: EpoxyModelWithHolder<PlaceCardModel.VH>() {

    @EpoxyAttribute
    lateinit var imageUrl: String

    @EpoxyAttribute
    lateinit var title: String

    @EpoxyAttribute(Option.DoNotHash)
    lateinit var listener: View.OnClickListener

    override fun bind(holder: VH) {
        holder.placeTitle.text = title

        Glide.with(holder.view)
            .load(imageUrl)
            .placeholder(R.drawable.download)
            .into(holder.placeThumb)

        holder.view.setOnClickListener(listener)
    }

    class VH: BaseEpoxyHolder() {
        val placeTitle by bind<MaterialTextView>(R.id.placeTitle)
        val placeThumb by bind<ImageView>(R.id.placeThumb)
    }
}

@EpoxyModelClass(layout = R.layout.fragment_routes_create_new)
abstract class CreateEditRouteModel: EpoxyModelWithHolder<CreateEditRouteModel.VH>() {

    @EpoxyAttribute
    var titleId: Int? = null

    @EpoxyAttribute(Option.DoNotHash)
    lateinit var listener: View.OnClickListener

    override fun bind(holder: VH) {
        holder.button.setText(titleId!!)
        holder.button.setOnClickListener(listener)
    }

    class VH: BaseEpoxyHolder() {
        val button by bind<MaterialButton>(R.id.createRoute)
    }
}
