package it.uniparthenope.parthenopeddit.android.ui.chat

import android.database.Observable
import android.text.PrecomputedText

interface ObservableUseCase<T, P : PrecomputedText.Params> {
    fun getObservable(params: P? = null): Observable<T>
}