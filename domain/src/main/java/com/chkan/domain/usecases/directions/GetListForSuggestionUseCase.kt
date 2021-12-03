package com.chkan.domain.usecases.directions

import com.chkan.data.sources.cash.CashDataSource
import com.chkan.base.utils.ResultType
import com.chkan.base.utils.Result
import com.chkan.data.sources.network.NetworkDataSource
import javax.inject.Inject

class GetListForSuggestionUseCase @Inject constructor(private val networkDataSource : NetworkDataSource, private val cashDataSource : CashDataSource) {

    suspend fun getListForSuggestionUseCase(query: String) : Result<MutableList<String>> {

        val listAsModel = networkDataSource.getListForSuggestion(query)

        return if (listAsModel.resultType== ResultType.SUCCESS){
            val listPlaces = listAsModel.data?.listPlaces
            val set = mutableSetOf<String>()
            if (listPlaces != null) {
                cashDataSource.places = listPlaces
                for (item in listPlaces) {
                    set.add(item.name)
                }
            }
            Result.success(set.toMutableList())
        } else {
            Result.error(listAsModel.error)
        }

    }
}