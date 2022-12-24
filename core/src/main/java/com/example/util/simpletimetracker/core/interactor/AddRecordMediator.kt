package com.example.util.simpletimetracker.core.interactor

import com.example.util.simpletimetracker.domain.interactor.RecordInteractor
import com.example.util.simpletimetracker.domain.model.Record
import com.example.util.simpletimetracker.domain.model.WidgetType
import javax.inject.Inject

class AddRecordMediator @Inject constructor(
    private val recordInteractor: RecordInteractor,
    private val widgetInteractor: WidgetInteractor,
    private val notificationGoalTimeInteractor: NotificationGoalTimeInteractor,
) {

    suspend fun add(
        record: Record,
    ) {
        recordInteractor.add(record)
        notificationGoalTimeInteractor.checkAndReschedule(record.typeId)
        widgetInteractor.updateWidgets(listOf(WidgetType.STATISTICS_CHART))
    }
}