package com.example.util.simpletimetracker.domain.interactor

import com.example.util.simpletimetracker.domain.mapper.StatisticsMapper
import com.example.util.simpletimetracker.domain.model.Record
import com.example.util.simpletimetracker.domain.model.RecordTypeCategory
import com.example.util.simpletimetracker.domain.model.Statistics
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class StatisticsCategoryInteractor @Inject constructor(
    private val recordInteractor: RecordInteractor,
    private val recordTypeCategoryInteractor: RecordTypeCategoryInteractor,
    private val statisticsMapper: StatisticsMapper
) {

    suspend fun getAll(): List<Statistics> = withContext(Dispatchers.IO) {
        val allRecords = recordInteractor.getAll()

        getCategoryRecords(allRecords)
            .map { (categoryId, records) ->
                Statistics(
                    id = categoryId,
                    duration = records.let(statisticsMapper::mapToDuration)
                )
            }
    }

    suspend fun getFromRange(
        start: Long,
        end: Long
    ): List<Statistics> = withContext(Dispatchers.IO) {
        val allRecords = recordInteractor.getFromRange(start, end)

        getCategoryRecords(allRecords)
            .map { (categoryId, records) ->
                Statistics(
                    id = categoryId,
                    duration = statisticsMapper.mapToDurationFromRange(records, start, end)
                )
            }
    }

    private suspend fun getCategoryRecords(allRecords: List<Record>): Map<Long, List<Record>> {
        val recordTypeCategories = recordTypeCategoryInteractor.getAll()
            .groupBy(RecordTypeCategory::categoryId)
            .mapValues { it.value.map(RecordTypeCategory::recordTypeId) }

        return recordTypeCategories
            .mapValues { (_, typeIds) -> allRecords.filter { it.typeId in typeIds } }
            .filterValues(List<Record>::isNotEmpty)
    }
}