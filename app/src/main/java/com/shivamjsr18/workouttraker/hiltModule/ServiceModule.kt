package com.shivamjsr18.workouttraker.hiltModule

import com.shivamjsr18.workouttraker.services.AccountService
import com.shivamjsr18.workouttraker.services.StorageService
import com.shivamjsr18.workouttraker.services.implimentation.AccountServiceImpl
import com.shivamjsr18.workouttraker.services.implimentation.StorageServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ServiceModule {
    @Binds
    abstract fun provideAccountService(impl: AccountServiceImpl): AccountService

    @Binds
    abstract fun providesStorageService(impl: StorageServiceImpl): StorageService
}