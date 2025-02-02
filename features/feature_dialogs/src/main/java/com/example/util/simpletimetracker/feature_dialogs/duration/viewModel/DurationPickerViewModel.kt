package com.example.util.simpletimetracker.feature_dialogs.duration.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.util.simpletimetracker.core.extension.set
import com.example.util.simpletimetracker.domain.extension.orZero
import com.example.util.simpletimetracker.feature_dialogs.duration.customView.DurationView
import com.example.util.simpletimetracker.feature_dialogs.duration.extra.DurationPickerExtra
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class DurationPickerViewModel @Inject constructor() : ViewModel() {

    lateinit var extra: DurationPickerExtra

    val durationViewData: LiveData<DurationView.ViewData> by lazy {
        MutableLiveData<DurationView.ViewData>().let { initial ->
            viewModelScope.launch {
                reformattedDuration = reformatDuration(extra.duration)
                initial.value = loadDurationViewData()
            }
            initial
        }
    }

    private var reformattedDuration: Long = 0

    fun onNumberPressed(number: Int) {
        if (reformattedDuration <= 999_99_99) {
            reformattedDuration = reformattedDuration * 10 + number
            updateDurationViewData()
        }
    }

    fun onNumberDelete() {
        reformattedDuration /= 10
        updateDurationViewData()
    }

    private fun updateDurationViewData() {
        val data = loadDurationViewData()
        durationViewData.set(data)
    }

    private fun loadDurationViewData(): DurationView.ViewData {
        return mapToViewData(reformattedDuration)
    }

    private fun mapToViewData(durationString: Long): DurationView.ViewData {
        val hours = durationString / 10000
        val minutes = (durationString / 100) % 100
        val seconds = durationString % 100

        return DurationView.ViewData(hours, minutes, seconds)
    }

    private fun reformatDuration(duration: Long): Long {
        fun format(value: Long): String = value.toString().padStart(2, '0')

        val hr = duration
            .let(TimeUnit.SECONDS::toHours)
        val min = (duration - TimeUnit.HOURS.toSeconds(hr))
            .let(TimeUnit.SECONDS::toMinutes)
        val sec = (duration - TimeUnit.HOURS.toSeconds(hr) - TimeUnit.MINUTES.toSeconds(min))
            .let(TimeUnit.SECONDS::toSeconds)

        return (format(hr) + format(min) + format(sec)).toLongOrNull().orZero()
    }
}
