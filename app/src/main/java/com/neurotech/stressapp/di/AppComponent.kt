package com.neurotech.stressapp.di

import com.neurotech.stressapp.notification.NotificationBuilderApp
import com.neurotech.stressapp.notification.NotificationReceiver
import com.neurotech.stressapp.service.AppService
import com.neurotech.stressapp.service.DataFlowAnalyzer
import com.neurotech.stressapp.ui.Analitycs.AnalyticsFragment
import com.neurotech.stressapp.ui.Main.MainHost.MainHostFragment
import com.neurotech.stressapp.ui.Search.SearchFragment
import com.neurotech.stressapp.ui.StartFragment
import com.neurotech.stressapp.ui.Main.Graph.PhaseGraphFragment
import com.neurotech.stressapp.ui.Main.PhaseItem.PhaseItemFragment
import com.neurotech.stressapp.ui.Main.StatisticItem.StatisticItemFragment
import com.neurotech.stressapp.ui.Main.TonicItem.TonicItemFragment
import com.neurotech.stressapp.ui.Setting.DayMarkup.DayMarkupFragment
import com.neurotech.stressapp.ui.DayMarkupUpdate.DayMarkupUpdateFragment
import com.neurotech.stressapp.ui.Main.Graph.TonicGraphFragment
import com.neurotech.stressapp.ui.Main.MainFragment
import com.neurotech.stressapp.ui.Markup.MarkupFragment
import com.neurotech.stressapp.ui.RelaxFragment.RelaxFragment
import com.neurotech.stressapp.ui.Setting.Source.SourceFragment
import com.neurotech.stressapp.ui.Statistic.StatisticFragment
import dagger.Component
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
        RecodingInDeviceUseCaseModule::class
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

}