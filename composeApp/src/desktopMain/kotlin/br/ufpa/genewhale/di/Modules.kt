package br.ufpa.genewhale.di

import br.ufpa.genewhale.global.Global
import br.ufpa.genewhale.panaroo.services.PanarooService
import br.ufpa.genewhale.global.WebService
import br.ufpa.genewhale.global.WebServiceJavaImpl
import br.ufpa.genewhale.panaroo.ui.PanarooViewModel
import br.ufpa.genewhale.ui.navigation.PanarooScope
import br.ufpa.genewhale.home.HomeViewModel
import br.ufpa.genewhale.ui.viewmodels.ProjectViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

private val appModule = module {
    single { Global(get(), get()) }
}

private val servicesModule = module {
    single<WebService> { WebServiceJavaImpl() }
}

private val viewModelsModule = module {
    viewModelOf(::ProjectViewModel)
    viewModelOf(::HomeViewModel)
}

private val panarooModule = module {
    single<PanarooService> { PanarooService() }
    scope <PanarooScope>{ scoped { PanarooViewModel(get(), get()) } }
}

val modules = listOf(
    appModule,
    servicesModule,
    viewModelsModule,
    panarooModule
)