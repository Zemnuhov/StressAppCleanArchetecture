package com.neurotech.stressapp.di

import com.neurotech.stressapp.notification.NotificationBuilderApp
import com.neurotech.stressapp.notification.NotificationReceiver
import com.neurotech.stressapp.service.AppService
import com.neurotech.stressapp.service.DataFlowAnalyzer
import com.neurotech.stressapp.ui.MainActivity
import com.neurotech.stressapp.ui.analitycs.AnalyticsFragment
import com.neurotech.stressapp.ui.markupupdate.DayMarkupUpdateFragment
import com.neurotech.stressapp.ui.main.Graph.PhaseGraphFragment
import com.neurotech.stressapp.ui.main.Graph.TonicGraphFragment
import com.neurotech.stressapp.ui.main.MainFragment
import com.neurotech.stressapp.ui.main.MainHost.MainHostFragment
import com.neurotech.stressapp.ui.main.PhaseItem.PhaseItemFragment
import com.neurotech.stressapp.ui.main.StatisticItem.StatisticItemFragment
import com.neurotech.stressapp.ui.main.TonicItem.TonicItemFragment
import com.neurotech.stressapp.ui.markup.MarkupFragment
import com.neurotech.stressapp.ui.relax.RelaxFragment
import com.neurotech.stressapp.ui.search.SearchFragment
import com.neurotech.stressapp.ui.setting.DayMarkup.DayMarkupFragment
import com.neurotech.stressapp.ui.setting.Source.SourceFragment
import com.neurotech.stressapp.ui.StartFragment
import com.neurotech.stressapp.ui.statistic.StatisticFragment
import com.neurotech.stressapp.ui.useraccount.UserAccountFragment
import dagger.Component
import javax.inject.Scope
import javax.inject.Singleton


@Singleton
@Component(
    modules = [
        AppModule::class,
        ConnectionUseCaseModule::class,
        GsrDataUseCaseModule::class,
        MarkupDataUseCaseModule::class,
        PhaseDataUseCaseModule::class,
        RepositoryModule::class,
        ResultDataUseCaseModule::class,
        SettingsModule::class,
        TonicDataUseCaseModule::class,
        ServiceModule::class,
        RecodingInDeviceUseCaseModule::class,
        UserUseCaseModule::class
    ]
)
interface AppComponent {
    fun inject(fragment: MainHostFragment)
    fun inject(fragment: SearchFragment)
    fun inject(fragment: StartFragment)
    fun inject(fragment: DayMarkupFragment)
    fun inject(fragment: DayMarkupUpdateFragment)
    fun inject(fragment: SourceFragment)
    fun inject(fragment: PhaseGraphFragment)
    fun inject(fragment: PhaseItemFragment)
    fun inject(fragment: StatisticItemFragment)
    fun inject(fragment: TonicItemFragment)
    fun inject(service: AppService)
    fun inject(dataFlowAnalyzer: DataFlowAnalyzer)
    fun inject(notificationBuilderApp: NotificationBuilderApp)
    fun inject(notificationReceiver: NotificationReceiver)
    fun inject(fragment: MarkupFragment)
    fun inject(fragment: StatisticFragment)
    fun inject(fragment: TonicGraphFragment)
    fun inject(analyticsFragment: AnalyticsFragment)
    fun inject(relaxFragment: RelaxFragment)
    fun inject(mainFragment: MainFragment)
    fun inject(userAccountFragment: UserAccountFragment)
    fun inject(mainActivity: MainActivity)
}