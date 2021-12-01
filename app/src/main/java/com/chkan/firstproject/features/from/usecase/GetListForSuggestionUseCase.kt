package com.chkan.firstproject.features.from.usecase

import com.chkan.firstproject.data.cash.CashDataSource
import com.chkan.firstproject.data.datatype.Result
import com.chkan.firstproject.data.datatype.ResultType
import com.chkan.firstproject.data.network.NetworkDataSource
import javax.inject.Inject

class GetListForSuggestionUseCase @Inject constructor(private val networkDataSource : NetworkDataSource) {

    suspend fun getListForSuggestionUseCase(query: String) : Result <MutableList<String>> {

        val listAsModel = networkDataSource.getListForSuggestion(query)

        return if (listAsModel.resultType== ResultType.SUCCESS){
            val listPlaces = listAsModel.data?.listPlaces
            val set = mutableSetOf<String>()
            if (listPlaces != null) {
                CashDataSource.places = listPlaces
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