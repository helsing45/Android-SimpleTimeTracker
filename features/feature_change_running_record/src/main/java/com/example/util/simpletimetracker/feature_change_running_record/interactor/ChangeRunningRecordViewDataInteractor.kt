package com.example.util.simpletimetracker.feature_change_running_record.interactor

import com.example.util.simpletimetracker.core.interactor.GetCurrentRecordsDurationInteractor
import com.example.util.simpletimetracker.domain.extension.orZero
import com.example.util.simpletimetracker.domain.interactor.PrefsInteractor
import com.example.util.simpletimetracker.domain.interactor.RecordTagInteractor
import com.example.util.simpletimetracker.domain.interactor.RecordTypeInteractor
import com.example.util.simpletimetracker.domain.model.RunningRecord
import com.example.util.simpletimetracker.feature_change_running_record.mapper.ChangeRunningRecordViewDataMapper
import com.example.util.simpletimetracker.feature_change_running_record.viewData.ChangeRunningRecordViewData
import javax.inject.Inject

class ChangeRunningRecordViewDataInteractor @Inject constructor(
    private val prefsInteractor: PrefsInteractor,
    private val recordTypeInteractor: RecordTypeInteractor,
    private val recordTagInteractor: RecordTagInteractor,
    private val changeRunningRecordViewDataMapper: ChangeRunningRecordViewDataMapper,
    private val getCurrentRecordsDurationInteractor: GetCurrentRecordsDurationInteractor,
) {

    suspend fun getPreviewViewData(record: RunningRecord): ChangeRunningRecordViewData {
        val type = recordTypeInteractor.get(record.id)
        val tags = recordTagInteractor.getAll().filter { it.id in record.tagIds }
        val isDarkTheme = prefsInteractor.getDarkMode()
        val useMilitaryTime = prefsInteractor.getUseMilitaryTimeFormat()
        val showSeconds = prefsInteractor.getShowSeconds()

        val dailyCurrent = if (type?.dailyGoalTime.orZero() > 0L) {
            getCurrentRecordsDurationInteractor.getDailyCurrent(record)
        } else {
            0L
        }
        val weeklyCurrent = if (type?.weeklyGoalTime.orZero() > 0L) {
            getCurrentRecordsDurationInteractor.getWeeklyCurrent(record)
        } else {
            0L
        }
        val monthlyCurrent = if (type?.monthlyGoalTime.orZero() > 0L) {
            getCurrentRecordsDurationInteractor.getMonthlyCurrent(record)
        } else {
            0L
        }

        return changeRunningRecordViewDataMapper.map(
            runningRecord = record,
            dailyCurrent = dailyCurrent,
            weeklyCurrent = weeklyCurrent,
            monthlyCurrent = monthlyCurrent,
            recordType = type,
            recordTags = tags,
            isDarkTheme = isDarkTheme,
            useMilitaryTime = useMilitaryTime,
            showSeconds = showSeconds,
        )
    }
}