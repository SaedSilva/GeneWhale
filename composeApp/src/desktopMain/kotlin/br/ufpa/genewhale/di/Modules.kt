package br.ufpa.genewhale.di

import br.ufpa.genewhale.global.Global
import br.ufpa.genewhale.services.DockerService
import br.ufpa.genewhale.services.PanarooService
import br.ufpa.genewhale.services.WebService
import br.ufpa.genewhale.services.WebServiceJavaImpl
import br.ufpa.genewhale.ui.PanarooViewModel
import br.ufpa.genewhale.ui.viewmodels.HomeViewModel
import br.ufpa.genewhale.ui.viewmodels.ProjectViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

private val appModule = module {
    single { Global(listOf(get<PanarooService>()), get()) }
}
private val servicesModule = module {
    single<DockerService> { get<PanarooService>() }
    single<WebService> { WebServiceJavaImpl() }
}

private val viewModelsModule = module {
    viewModelOf(::ProjectViewModel)
    viewModelOf(::HomeViewModel)
}

private val panarooModule = module {
    single { PanarooService() }
    viewModel { PanarooViewModel(get(), get()) }
}

val modules = listOf(
    appModule,
    servicesModule,
    viewModelsModule,
    panarooModule
)