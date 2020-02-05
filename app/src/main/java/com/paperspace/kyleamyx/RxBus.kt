package com.paperspace.kyleamyx

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject


/**
 * Object used to communicate actions between controllers. Currently used to update List/Favorites tabs when
 * adding/removing a `Favorite` item.
 */
object RxBus {

    private val publisher = PublishSubject.create<Any>()

    fun publish(event: Any) {
        publisher.onNext(event)
    }

    fun <T> listen(eventType: Class<T>): Observable<T> = publisher.ofType(eventType)

}


class RxFavoriteEvent{
    data class AddFavorite(val add: Boolean)
    data class RemoveFavorite(val remove: Boolean)
}

class RxSettingsEvent{
    data class TimeSettingChange(val time: String)
}

