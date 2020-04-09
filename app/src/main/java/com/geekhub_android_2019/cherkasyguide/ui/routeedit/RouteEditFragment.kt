package com.geekhub_android_2019.cherkasyguide.ui.routeedit

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.geekhub_android_2019.cherkasyguide.R
import com.geekhub_android_2019.cherkasyguide.common.Limits
import com.geekhub_android_2019.cherkasyguide.common.verticalGridCarousel
import com.geekhub_android_2019.cherkasyguide.models.Place
import com.geekhub_android_2019.cherkasyguide.models.UserRoute
import com.geekhub_android_2019.cherkasyguide.ui.models.*
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_routeedit.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class RouteEditFragment : Fragment(R.layout.fragment_routeedit) {

    private val vm by sharedViewModel<RouteEditViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)

        vm.state.observe(viewLifecycleOwner) {
            routeEditSpinner.visibility = View.GONE

            it?.let { (places, userRoute) ->
                assembleViewModel(places, userRoute)
            }
        }

        vm.warn.observe(viewLifecycleOwner) {
            val msg = when (it) {
                Messages.ROUTE_TO_LONG -> resources.getQuantityString(
                    R.plurals.to_long_route,
                    Limits.MAX_PLACES,
                    Limits.MAX_PLACES
                )
            }

            Snackbar.make(requireView(), msg, Snackbar.LENGTH_LONG)
                .setAction(android.R.string.ok) {}
                .show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.fragment_routeedit_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            R.id.clearRoute -> {
                vm.clearUserRoute()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    private fun assembleViewModel(places: List<Place>, userRoute: UserRoute?) {
        val selected = userRoute?.places?.toSet() ?: setOf()

        placesList.withModels {
            verticalGridCarousel {
                id("user-route-places")

                places.map { place ->
                    PlaceItemModel_()
                        .id("place-item", place.id)
                        .title(place.name!!)
                        .imageUrl(place.photoSmallUrl!!)
                        .checked(selected.contains(place))
                        .listener { _ -> vm.toggleCheck(place) }
                }.also { models(it) }
            }
        }
    }
}
