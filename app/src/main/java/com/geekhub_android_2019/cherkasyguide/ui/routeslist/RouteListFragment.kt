package com.geekhub_android_2019.cherkasyguide.ui.routeslist

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.airbnb.epoxy.*
import com.geekhub_android_2019.cherkasyguide.R
import kotlinx.android.synthetic.main.fragment_routes_list.*

class RouteListFragment : Fragment(R.layout.fragment_routes_list) {

    private val vm by activityViewModels<RoutesViewModel>()
    private val controller: NavController
        get() = findNavController()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vm.routes.observe(viewLifecycleOwner, Observer {
            it?.let(::assembleView)
        })
    }

    private fun assembleView(state: ViewState) {
        routeList.withModels {
            state.routes.forEach { route ->
                routeHeader {
                    id("route-header", route.id)
                    name(route.name!!)
                    listener { _ -> vm.viewRouteMap(controller, route.places) }
                }

                carousel {
                    id("place-thumbs", route.id)

                    route.places.map { place ->
                        PlaceCardModel_()
                            .id("place-thumb", route.id, place.id)
                            .title(place.name!!)
                            .imageUrl(place.photoSmallUrl!!)
                            .listener { _ -> vm.viewPlace(controller, place) }
                    }.also { models(it) }
                }
            }

            if (state.userRoute != null && state.userRoute.places.isNotEmpty()) {
                routeHeader {
                    id("user-route-header", state.userRoute.id)
                    name(getString(R.string.user_route))
                    listener { _ -> vm.viewRouteMap(controller, state.userRoute.places) }
                }

                carousel {
                    id("place-thumbs-user", state.userRoute.id)

                    state.userRoute.places.map { place ->
                        PlaceCardModel_()
                            .id("user-place-thumb", state.userRoute.id, place.id)
                            .title(place.name!!)
                            .imageUrl(place.photoSmallUrl!!)
                            .listener { _ -> vm.viewPlace(controller, place) }
                    }.also { models(it) }
                }
            } else
                createRoute {
                    id("create-new-route")
                    listener { _ -> vm.createNewRoute(controller) }
                }
        }

    }
}
