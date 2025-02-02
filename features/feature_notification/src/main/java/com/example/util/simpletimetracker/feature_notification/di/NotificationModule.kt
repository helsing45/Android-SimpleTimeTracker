package com.example.util.simpletimetracker.feature_notification.di

import com.example.util.simpletimetracker.domain.interactor.NotificationGoalTimeInteractor
import com.example.util.simpletimetracker.domain.interactor.NotificationInactivityInteractor
import com.example.util.simpletimetracker.domain.interactor.NotificationTypeInteractor
import com.example.util.simpletimetracker.feature_notification.goalTime.interactor.NotificationGoalTimeInteractorImpl
import com.example.util.simpletimetracker.feature_notification.inactivity.interactor.NotificationInactivityInteractorImpl
import com.example.util.simpletimetracker.feature_notification.recordType.interactor.NotificationTypeInteractorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface NotificationModule {

    @Binds
    fun NotificationTypeInteractorImpl.bindNotificationTypeInteractor(): NotificationTypeInteractor

    @Binds
    fun NotificationInactivityInteractorImpl.bindNotificationInactivityInteractor(): NotificationInactivityInteractor

    @Binds
    fun NotificationGoalTimeInteractorImpl.bindNotificationGoalTimeInteractor(): NotificationGoalTimeInteractor
}